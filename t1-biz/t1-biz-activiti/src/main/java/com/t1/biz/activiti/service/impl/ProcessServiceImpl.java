package com.t1.biz.activiti.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.biz.activiti.dto.ProcessDto;
import com.t1.biz.activiti.entity.Leave;
import com.t1.biz.activiti.service.LeaveService;
import com.t1.biz.activiti.service.ProcessService;
import com.t1.common.constant.CommonConstants;
import com.t1.common.enums.TaskStatusEnum;
import lombok.AllArgsConstructor;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 流程 service
 */
@Service
@AllArgsConstructor
public class ProcessServiceImpl implements ProcessService {

    private final RepositoryService repositoryService;
    private final RuntimeService runtimeService;
    private final LeaveService leaveService;

    /**
     * 分页流程列表
     *
     * @param params
     * @return
     */
    @Override
    public IPage<ProcessDto> list(Map<String, Object> params) {
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery().latestVersion();
        String name = (String) params.get("name");
        if (StrUtil.isNotBlank(name)) {
            query.processDefinitionNameLike(name);
        }

        String category = MapUtil.getStr(params, "category");
        if (StrUtil.isNotBlank(category)) {
            query.processDefinitionCategory(category);
        }

        int current = MapUtil.getInt(params, CommonConstants.CURRENT);
        int size = MapUtil.getInt(params, CommonConstants.SIZE);

        IPage result = new Page(current, size);
        result.setTotal(query.count());

        List<ProcessDto> deploymentList = query.listPage((current - 1) * size, size)
                .stream()
                .map(processDefinition -> {
                    Deployment deployment = repositoryService.createDeploymentQuery()
                            .deploymentId(processDefinition.getDeploymentId()).singleResult();
                    return ProcessDto.toProcessDto(processDefinition, deployment);
                }).collect(Collectors.toList());
        result.setRecords(deploymentList);
        return result;
    }

    /**
     * 读取xml/image资源
     *
     * @param procInsId
     * @param procDefId
     * @param resType
     * @return
     */
    @Override
    public InputStream readResource(String procInsId, String procDefId, String resType) {

        if (StrUtil.isBlank(procDefId)) {
            ProcessInstance processInstance = runtimeService
                    .createProcessInstanceQuery()
                    .processInstanceId(procInsId)
                    .singleResult();
            procDefId = processInstance.getProcessDefinitionId();
        }
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionId(procDefId)
                .singleResult();

        String resourceName = "";
        if ("image".equals(resType)) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if ("xml".equals(resType)) {
            resourceName = processDefinition.getResourceName();
        }

        InputStream resourceAsStream = repositoryService
                .getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        return resourceAsStream;
    }

    /**
     * 更新状态
     *
     * @param procDefId
     * @param status
     * @return
     */
    @Override
    public Boolean changeStatus(String procDefId, String status) {
        if ("active".equals(status)) {
            repositoryService.activateProcessDefinitionById(procDefId, true, null);
        } else if ("suspend".equals(status)) {
            repositoryService.suspendProcessDefinitionById(procDefId, true, null);
        }
        return Boolean.TRUE;
    }

    /**
     * 删除部署的流程，级联删除流程实例
     *
     * @param deployId
     * @return
     */
    @Override
    public Boolean removeProcIns(String deployId) {
        repositoryService.deleteDeployment(deployId, true);
        return Boolean.TRUE;
    }


    /**
     * 启动请假流程
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean startLeaveProcess(Integer id, String username) {
        Leave leave = leaveService.getById(id);
        leave.setStatus(TaskStatusEnum.CHECKING.getStatus());
        //设置流程发起人
        Authentication.setAuthenticatedUserId(username);
        //设置全部变量
        Map<String, Object> variables = new HashMap<>(1);
        variables.put("classpath" ,leave.getClass().getCanonicalName());
        variables.put("businessTitle","【请假】申请单");
        variables.put("idField","id");

        String key = leave.getClass().getSimpleName();
        String businessKey = key + "_" + leave.getId();
        runtimeService.startProcessInstanceByKey(key,businessKey,variables);
        leaveService.updateById(leave);
        return Boolean.TRUE;
    }

    public static void main(String[] args) {

    }

}
