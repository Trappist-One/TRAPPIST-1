package com.t1.biz.activiti.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.biz.activiti.dto.CommentDto;
import com.t1.biz.activiti.dto.LeaveDto;
import com.t1.biz.activiti.dto.TaskCheckDto;
import com.t1.biz.activiti.dto.TaskDto;
import com.t1.biz.activiti.entity.Leave;
import com.t1.biz.activiti.mapper.CommonEntityBusinessMapper;
import com.t1.biz.activiti.service.LeaveService;
import com.t1.biz.activiti.util.BusinessProcessingUtil;
import com.t1.common.constant.CommonConstants;
import com.t1.common.enums.ActivitiBusinessSql;
import com.t1.common.enums.TaskStatusEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 流程 service
 */
@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements com.t1.biz.activiti.service.TaskService {
    private static final String FLAG = "审批";
    private final LeaveService leaveService;
    private final TaskService taskService;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final HistoryService historyService;
    private final ProcessEngineFactoryBean processEngine;
    private final CommonEntityBusinessMapper CommonEntityBusinessMapper;

    @Override
    public IPage list(Map<String, Object> params, String username) {
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskCandidateOrAssigned(username);

        String taskName = (String) params.get("taskName");
        if (StrUtil.isNotBlank(taskName)) {
            taskQuery.taskNameLike(taskName);
        }

        int page = MapUtil.getInt(params, CommonConstants.CURRENT);
        int limit = MapUtil.getInt(params, CommonConstants.SIZE);

        IPage result = new Page(page, limit);
        result.setTotal(taskQuery.count());

        List<TaskDto> taskDTOList = taskQuery.orderByTaskCreateTime().desc()
                .listPage((page - 1) * limit, limit).stream().map(task -> {
                    TaskDto dto = new TaskDto();
                    dto.setTaskId(task.getId());
                    dto.setTaskName(task.getName());
                    dto.setProcessInstanceId(task.getProcessInstanceId());
                    dto.setNodeKey(task.getTaskDefinitionKey());
                    dto.setCategory(task.getCategory());
                    dto.setStatus(task.isSuspended() ? "0" : "1");
                    dto.setCreateTime(task.getCreateTime());
                    return dto;
                }).collect(Collectors.toList());
        result.setRecords(taskDTOList);
        return result;
    }


    @Override
    public IPage historyList(Map<String, Object> params, String username) {
        NativeHistoricTaskInstanceQuery nativeHistoricTaskInstanceQuery = historyService.createNativeHistoricTaskInstanceQuery();
        nativeHistoricTaskInstanceQuery.parameter("currentUser",username);
        String sql= ActivitiBusinessSql.PROCESS_TASK_HISTORIC_TASK_INSTANCE.getSql();
        if (ObjectUtil.isNotEmpty(params.get("taskName"))) {
            nativeHistoricTaskInstanceQuery.parameter("taskName",String.join("",
                    "%",params.get("taskName").toString(),"%"));
            sql=String.format(sql, ActivitiBusinessSql.PROCESS_TASK_HISTORIC_TASK_INSTANCE.getCondition()[0]);
        }
        sql=BusinessProcessingUtil.clearTemplate(sql);
        nativeHistoricTaskInstanceQuery.sql(BusinessProcessingUtil.makeCountSql(sql));

        int page = MapUtil.getInt(params, CommonConstants.CURRENT);
        int limit = MapUtil.getInt(params, CommonConstants.SIZE);

        IPage result = new Page(page, limit);
        result.setTotal(nativeHistoricTaskInstanceQuery.count());

        nativeHistoricTaskInstanceQuery.sql(sql);
        List<TaskDto> taskDTOList = nativeHistoricTaskInstanceQuery
            .listPage((page - 1) * limit, limit).stream().map(historyTask -> {
                TaskDto dto = new TaskDto();
                dto.setTaskId(historyTask.getId());
                dto.setTaskName(historyTask.getName());
                dto.setProcessInstanceId(historyTask.getProcessInstanceId());
                dto.setNodeKey(historyTask.getTaskDefinitionKey());
                dto.setCategory(historyTask.getCategory());
                dto.setStatus("0");
                dto.setCreateTime(historyTask.getCreateTime());
                return dto;
            }).collect(Collectors.toList());
        result.setRecords(taskDTOList);
        return result;
    }

    /**
     * 通过任务ID查询任务信息
     *
     * @param taskId
     * @return
     */
    @Override
    public Map<String, Object> getTaskById(String taskId) throws ClassNotFoundException {


        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(task.getProcessInstanceId())
                .singleResult();

        String businessKey = pi.getBusinessKey();
        List<String> comeList = findOutFlagListByTaskId(task);
        Map<String,Object> businessData=new HashMap<>();
        if (StrUtil.isNotBlank(businessKey)) {
            businessKey = businessKey.split("_")[1];
            String idField= (String) taskService.getVariable(taskId,"idField");
            String classpath= (String) taskService.getVariable(taskId,"classpath");
            Class<?> clz = Class.forName(classpath);
            String tableName=clz.getAnnotation(TableName.class).value();
            businessData=CommonEntityBusinessMapper.fetchFormBusinessData(tableName,idField,businessKey);
        }

        String formCode="";
        String formDataUrl="";
        if(StrUtil.isNotBlank(task.getFormKey())){
            try {
                formCode=task.getFormKey().split(",")[0];
                formDataUrl=task.getFormKey().split(",")[1];
            } catch (Exception e){}
        }

        TaskCheckDto taskCheckDto = new TaskCheckDto();
        taskCheckDto.setTaskId(taskId);
        taskCheckDto.setFormCode(formCode);
        taskCheckDto.setFormDataUrl(formDataUrl);
        taskCheckDto.setBusinessKey(businessKey);
        taskCheckDto.setTaskName(task.getName());
        taskCheckDto.setTime(task.getCreateTime());
        taskCheckDto.setFlagList(comeList);

        Map<String,Object> result=new HashMap<>();
        for (Map.Entry<String,Object> item:businessData.entrySet()){
            result.put(StrUtil.toCamelCase(item.getKey()),item.getValue());
        }
        BeanUtil.copyProperties(taskCheckDto,result);
        return result;
    }

    /**
     * 通过任务ID查询任务信息 历史
     *
     * @param taskId
     * @return
     */
    @Override
    public Map<String, Object> getHistoryTaskById(String taskId) throws ClassNotFoundException {

        HistoricTaskInstanceQuery historicTaskInstanceQuery=historyService.createHistoricTaskInstanceQuery();
        historicTaskInstanceQuery.taskId(taskId);
        HistoricTaskInstance historicTaskInstance = historicTaskInstanceQuery.singleResult();


        HistoricProcessInstanceQuery historicProcessInstanceQuery=historyService.createHistoricProcessInstanceQuery();
        historicProcessInstanceQuery.processInstanceId(historicTaskInstance.getProcessInstanceId());
        HistoricProcessInstance historicProcessInstance=historicProcessInstanceQuery.singleResult();

        HistoricVariableInstanceQuery historicVariableInstanceQuery= historyService.createHistoricVariableInstanceQuery();
        historicVariableInstanceQuery.processInstanceId(historicTaskInstance.getProcessInstanceId());
        List<HistoricVariableInstance> historicVariableInstances=historicVariableInstanceQuery.list();
        Map<String,Object> historyVariable=new HashMap<>();
        historicVariableInstances.forEach(item->{
            if(item.getVariableName().matches("classpath|businessTitle|idField")){
                historyVariable.put(item.getVariableName(),item.getValue());
            }
        });

        String businessKey = historicProcessInstance.getBusinessKey();
        Map<String,Object> businessData=new HashMap<>();
        if (StrUtil.isNotBlank(businessKey)) {
            businessKey = businessKey.split("_")[1];
            String idField= (String) historyVariable.get("idField");
            String classpath= (String) historyVariable.get("classpath");
            Class<?> clz = Class.forName(classpath);
            String tableName=clz.getAnnotation(TableName.class).value();
            businessData=CommonEntityBusinessMapper.fetchFormBusinessData(tableName,idField,businessKey);
        }

        String formCode="";
        String formDataUrl="";
        if(StrUtil.isNotBlank(historicTaskInstance.getFormKey())){
            try {
                formCode=historicTaskInstance.getFormKey().split(",")[0];
                formDataUrl=historicTaskInstance.getFormKey().split(",")[1];
            } catch (Exception e){}
        }

        TaskCheckDto taskCheckDto = new TaskCheckDto();
        taskCheckDto.setTaskId(taskId);
        taskCheckDto.setFormCode(formCode);
        taskCheckDto.setFormDataUrl(formDataUrl);
        taskCheckDto.setBusinessKey(businessKey);
        taskCheckDto.setTaskName(historicTaskInstance.getName());
        taskCheckDto.setTime(historicTaskInstance.getCreateTime());

        Map<String,Object> result=new HashMap<>();
        for (Map.Entry<String,Object> item:businessData.entrySet()){
            result.put(StrUtil.toCamelCase(item.getKey()),item.getValue());
        }

        BeanUtil.copyProperties(taskCheckDto,result);
        return result;
    }

    /**
     * 提交任务
     *
     * @param leaveDto
     * @return
     */
    @Override
    public Boolean checkTask(LeaveDto leaveDto) {
        String taskId = leaveDto.getTaskId();
        String status=StrUtil.equals(TaskStatusEnum.REJECT.getDescription(),leaveDto.getTaskFlag()) ?
                TaskStatusEnum.REJECT.getStatus() : TaskStatusEnum.COMPLETED.getStatus();
        String message = StrUtil.isNotBlank(leaveDto.getComment())?leaveDto.getComment():"";
        Integer id = leaveDto.getId();

        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        String processInstanceId = task.getProcessInstanceId();
        taskService.addComment(taskId, processInstanceId, message);
        Map<String, Object> variables = new HashMap<>(1);
        variables.put("flag" , leaveDto.getTaskFlag());
        variables.put("type" , leaveDto.getType());
        variables.put("days" , leaveDto.getDays());

        taskService.complete(taskId,variables);
        ProcessInstance pi = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult();

        if (pi == null) {
            Leave bill = new Leave();
            bill.setId(id);
            bill.setStatus(status);
            leaveService.updateById(bill);
        }
        return null;
    }

    @Override
    public List<CommentDto> commitList(String taskId) {
        //使用当前任务ID，获取当前任务对象
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();
        //获取流程实例ID
        List<CommentDto> commentDtoList = taskService
                .getProcessInstanceComments(task.getProcessInstanceId())
                .stream().map(comment -> {
                            CommentDto commentDto = new CommentDto();
                            commentDto.setId(comment.getId());
                            commentDto.setTime(comment.getTime());
                            commentDto.setType(comment.getType());
                            commentDto.setTaskId(comment.getTaskId());
                            commentDto.setUserId(comment.getUserId());
                            commentDto.setFullMessage(comment.getFullMessage());
                            commentDto.setProcessInstanceId(comment.getProcessInstanceId());
                            return commentDto;
                        }
                ).collect(Collectors.toList());
        return commentDtoList;
    }

    /**
     * 追踪图片节点
     *
     * @param id
     */
    @Override
    public InputStream trackImage(String id) {
        //使用当前任务ID，获取当前任务对象
        Task task = taskService.createTaskQuery()
                .taskId(id)
                .singleResult();

        String processInstanceId = task.getProcessInstanceId();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        List<String> executedActivityIdList = new ArrayList<>();
        if (processInstance != null) {
            processDefinitionId = processInstance.getProcessDefinitionId();
            executedActivityIdList = this.runtimeService.getActiveActivityIds(processInstance.getId());
        } else if (historicProcessInstance != null) {
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            executedActivityIdList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .orderByHistoricActivityInstanceId().asc().list()
                    .stream().map(HistoricActivityInstance::getActivityId)
                    .collect(Collectors.toList());
        }

        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        ProcessEngineConfiguration processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();

        return diagramGenerator.generateDiagram(
                bpmnModel, "png" ,
                executedActivityIdList, Collections.emptyList(),
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                "宋体" ,
                null, 1.0);

    }

    private List<String> findOutFlagListByTaskId(Task task) {
        //查询ProcessDefinitionEntiy对象
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
                .getProcessDefinition(task.getProcessDefinitionId());

        ActivityImpl activityImpl = processDefinitionEntity.findActivity(task.getTaskDefinitionKey());

        //获取当前活动完成之后连线的名称
        List<String> nameList = activityImpl.getOutgoingTransitions().stream()
                .map(pvm -> {
                    String name = (String) pvm.getProperty("name");
                    return StrUtil.isNotBlank(name) ? name : FLAG;
                }).collect(Collectors.toList());
        return nameList;
    }

    public static void main(String[] args) {
        System.out.println("ddd,fff".split(",").length);
    }


}
