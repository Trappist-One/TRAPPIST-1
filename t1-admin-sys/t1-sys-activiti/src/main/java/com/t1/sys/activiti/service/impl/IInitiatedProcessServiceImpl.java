package com.t1.sys.activiti.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.sys.activiti.diagramgenerate.constant.ProcessChartConstants;
import com.t1.sys.activiti.diagramgenerate.processPicture.CustomProcessDiagramGeneratorService;
import com.t1.sys.activiti.dto.DetailedProcessTrackDto;
import com.t1.sys.activiti.dto.IInitiatedProcessDto;
import com.t1.sys.activiti.mapper.CommonEntityBusinessMapper;
import com.t1.sys.activiti.service.IInitiatedProcessService;
import com.t1.sys.activiti.util.BusinessProcessingUtil;
import com.t1.common.constant.CommonConstants;
import com.t1.common.enums.ActivitiBusinessSql;
import com.t1.common.enums.TaskStatusEnum;
import lombok.AllArgsConstructor;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.Comment;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.InputStream;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 我发起的流程业务层实现
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/14
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class IInitiatedProcessServiceImpl implements IInitiatedProcessService {

    private final HistoryService historyService;

    private final RepositoryService repositoryService;

    private final CommonEntityBusinessMapper commonEntityBusinessMapper;

    private final RuntimeService runtimeService;

    private final ProcessEngineFactoryBean processEngine;

    private final TaskService taskService;

    @Override
    public IPage list(Map<String, Object> params, String username) {
        //查询当前用户发起的流程
        HistoricProcessInstanceQuery historicProcessInstanceQuery = historyService.createHistoricProcessInstanceQuery();
        historicProcessInstanceQuery.startedBy(username);

        int page = MapUtil.getInt(params, CommonConstants.CURRENT);
        int limit = MapUtil.getInt(params, CommonConstants.SIZE);

        IPage result = new Page(page, limit);
        result.setTotal(historicProcessInstanceQuery.count());

        List<IInitiatedProcessDto> processInstanceDTOList = historicProcessInstanceQuery
                .orderByProcessInstanceEndTime().orderByProcessInstanceStartTime().desc()
                .listPage((page - 1) * limit, limit).stream().map(processInstance -> {
                    IInitiatedProcessDto dto = new IInitiatedProcessDto();
                    //填充数据
                    dto.setProcDefId(processInstance.getProcessDefinitionId());
                    dto.setProcInstId(processInstance.getId());
                    dto.setStartUserId(processInstance.getStartUserId());
                    dto.setBusinessKey(processInstance.getBusinessKey());
                    dto.setDuration(formatDuring(processInstance.getDurationInMillis(), processInstance.getStartTime()));
                    dto.setStartTime(processInstance.getStartTime());
                    dto.setEndTime(processInstance.getEndTime());
                    //采用1+N的方式读取附加数据,使用了分页不会很慢
                    HistoricVariableInstanceQuery historicVariableInstanceQuery =
                            historyService.createHistoricVariableInstanceQuery();
                    historicVariableInstanceQuery.executionId(processInstance.getId());
                    List<HistoricVariableInstance> historicVariableInstances = historicVariableInstanceQuery.list();
                    //收集变量数据
                    Map<String, Object> historyVariable = new HashMap<>();
                    historicVariableInstances.forEach(item -> {
                        if (item.getVariableName().matches("classpath|businessTitle|idField")) {
                            historyVariable.put(item.getVariableName(), item.getValue());
                        }
                    });
                    dto.setBusinessTitle(historyVariable.get("businessTitle").toString());
                    //查询流程编码查询对应procDef流程定义表
                    ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
                    processDefinitionQuery.processDefinitionId(processInstance.getProcessDefinitionId());
                    List<ProcessDefinition> processDefinitions = processDefinitionQuery.list();
                    ProcessDefinition processDefinition = processDefinitions.get(0);
                    dto.setProcessName(processDefinition.getName());
                    //查询目前流程的业务数据
                    try {
                        Class<?> clz = Class.forName(historyVariable.get("classpath").toString());
                        String tableName = clz.getAnnotation(TableName.class).value();
                        Map<String, Object> businessData = commonEntityBusinessMapper.fetchFormBusinessData(tableName,
                                historyVariable.get("idField").toString(), processInstance.getBusinessKey().split("_")[1]);
                        if (ObjectUtil.isNotEmpty(businessData.get("status"))) {
                            dto.setStatus(businessData.get("status").toString());
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    return dto;
                }).collect(Collectors.toList());

        //对数据进行过滤
        String procDefId = (String) params.get("procDefId");
        String processName = (String) params.get("processName");
        if (StrUtil.isNotBlank(procDefId) && StrUtil.isNotBlank(processName)) {
            processInstanceDTOList.removeAll(
                    processInstanceDTOList.stream().filter(
                            e -> !(e.getProcDefId().contains(procDefId.trim()) && e.getProcessName().contains(processName.trim()))
                    ).collect(Collectors.toList()));
            result.setTotal(processInstanceDTOList.size());
        } else if (StrUtil.isNotBlank(procDefId)) {
            processInstanceDTOList.removeAll(processInstanceDTOList.stream().filter(e -> !e.getProcDefId().contains(procDefId.trim()))
                    .collect(Collectors.toList()));
            result.setTotal(processInstanceDTOList.size());
        } else if (StrUtil.isNotBlank(processName)) {
            processInstanceDTOList.removeAll(processInstanceDTOList.stream().filter(e -> !e.getProcessName().contains(processName.trim()))
                    .collect(Collectors.toList()));
            result.setTotal(processInstanceDTOList.size());
        }

        result.setRecords(processInstanceDTOList);

        return result;
    }


    /**
     * 毫秒转换天数
     **/
    private String formatDuring(Long mss, Date StartTime) {
        if (ObjectUtil.isEmpty(mss)) {
            mss = (new Date()).getTime() - StartTime.getTime();
        }
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        //long seconds = (mss % (1000 * 60)) / 1000;
        return days + "天" + hours + "小时" + minutes + "分钟";
    }


    @Override
    public void abolishProcess(String procInstId, String businessKey) throws ClassNotFoundException {
        HistoricVariableInstanceQuery historicVariableInstanceQuery = historyService.createHistoricVariableInstanceQuery();
        historicVariableInstanceQuery.executionId(procInstId);
        List<HistoricVariableInstance> historicVariableInstances = historicVariableInstanceQuery.list();
        Map<String, Object> historyVariable = new HashMap<>();
        historicVariableInstances.forEach(item -> {
            if (item.getVariableName().matches("classpath|idField")) {
                historyVariable.put(item.getVariableName(), item.getValue());
            }
        });
        runtimeService.deleteProcessInstance(procInstId, TaskStatusEnum.ABOLISH_PROCESS.getDescription());
        Class<?> clz = Class.forName(historyVariable.get("classpath").toString());
        String tableName = clz.getAnnotation(TableName.class).value();
        //修改业务数据
        commonEntityBusinessMapper.UpdateBusinessDataStatus(
                tableName,
                historyVariable.get("idField").toString(),
                TaskStatusEnum.ABOLISH_PROCESS.getStatus(),
                businessKey.split("_")[1]
        );
    }


    @Override
    public InputStream fetchProcessTrackingPicture(String processInstanceId) {

        //获取历史记录已经走过的流程记录,后续需要画出颜色
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        ProcessDefinitionEntity definitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        List<HistoricActivityInstance> highLightedActivitiList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).orderByHistoricActivityInstanceStartTime().asc().list();
        // 高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<String>();
        // 高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitiList);
        for (HistoricActivityInstance tempActivity : highLightedActivitiList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }

        Set<String> currIds = runtimeService.createExecutionQuery().processInstanceId(processInstance.getId()).list()
                .stream().map(e -> e.getActivityId()).collect(Collectors.toSet());

        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        CustomProcessDiagramGeneratorService diagramGenerator =
                (CustomProcessDiagramGeneratorService) processEngineConfiguration.getProcessDiagramGenerator();

        return diagramGenerator.generateDiagram(
                bpmnModel,
                "png",
                highLightedActivitis, highLightedFlows,
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                processEngine.getProcessEngineConfiguration().getAnnotationFontName(),
                null, 1.0,
                new Color[]{ProcessChartConstants.COLOR_NORMAL, ProcessChartConstants.COLOR_CURRENT},
                currIds
        );
    }


    /**
     * 获取需要高亮的线
     * 取出节点跟节点连接处的线
     * 内部根据开始时间差来判断节点跟节点之间的关联
     *
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
                                             List<HistoricActivityInstance> historicActivityInstances) {
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<>();
        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size() - 1; ++i) {
            // 得到节点定义的详细信息
            ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());
            // 用以保存后需开始时间相同的节点
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<>();
            ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; ++j) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
                if (Math.abs(activityImpl1.getStartTime().getTime() - activityImpl2.getStartTime().getTime()) < 200) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出节点的所有出去的线
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }


    @Override
    public IPage listDetailedProcess(Map<String, Object> params) {
        //查询当前已经走过的流程信息
        String procInstId = (String) params.get("procInstId");
        NativeHistoricTaskInstanceQuery nativeHistoricTaskInstanceQuery = historyService.createNativeHistoricTaskInstanceQuery();
        nativeHistoricTaskInstanceQuery.parameter("procInstId", procInstId);
        nativeHistoricTaskInstanceQuery.sql(BusinessProcessingUtil.makeCountSql(
                ActivitiBusinessSql.PROCESS_TRACKING_HISTORIC_TASK_INSTANCE.getSql()));

        int page = MapUtil.getInt(params, CommonConstants.CURRENT);
        int limit = MapUtil.getInt(params, CommonConstants.SIZE);

        IPage result = new Page(page, limit);
        result.setTotal(nativeHistoricTaskInstanceQuery.count());

        nativeHistoricTaskInstanceQuery.sql(ActivitiBusinessSql.PROCESS_TRACKING_HISTORIC_TASK_INSTANCE.getSql());
        //当前这个流程的历史审批意见
        List<Comment> comments = taskService.getProcessInstanceComments(procInstId);

        List<DetailedProcessTrackDto> TaskInstanceDTOList = nativeHistoricTaskInstanceQuery
                .listPage((page - 1) * limit, limit).stream().map(taskInstance -> {
                    DetailedProcessTrackDto dto = new DetailedProcessTrackDto();
                    //填充数据
                    dto.setActName(taskInstance.getName());
                    dto.setProcInstId(taskInstance.getProcessInstanceId());
                    dto.setStartTime(taskInstance.getStartTime());
                    dto.setEndTime(taskInstance.getEndTime());
                    dto.setAssignee(taskInstance.getAssignee());
                    //审批意见处理
                    if (ObjectUtil.isNotEmpty(comments)) {
                        comments.stream()
                                .anyMatch(item -> {
                                    if (item.getTaskId().equals(taskInstance.getId())) {
                                        dto.setMessage(item.getFullMessage());
                                        return true;
                                    }
                                    return false;
                                });
                    }
                    //存储处理结果信息
                    dto.setProcessResult(taskInstance.getDeleteReason());
                    if (taskInstance.getDeleteReason().equals("completed")) {
                        dto.setProcessResult("已完成");
                    }
                    return dto;
                }).collect(Collectors.toList());

        result.setRecords(TaskInstanceDTOList);

        return result;
    }


    public static void main(String[] args) {

    }

}
