package com.t1.sys.activiti.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1.sys.activiti.dto.CommentDto;
import com.t1.sys.activiti.dto.LeaveDto;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 任务 service
 */
public interface TaskService {
    /**
     * 获取任务列表
     *
     * @param params
     * @return
     */
    IPage list(Map<String, Object> params, String username);

    /**
     * 获取历史审批任务列表
     *
     * @param params
     * @return
     */
    IPage historyList(Map<String, Object> params, String username);

    /**
     * 通过任务ID查询任务信息关联申请单信息
     *
     * @param id
     * @return
     */
    Map<String, Object> getTaskById(String id) throws ClassNotFoundException;


    /**
     * 通过任务ID查询任务信息关联申请单信息 历史
     *
     * @param id
     * @return
     */
    Map<String, Object> getHistoryTaskById(String id) throws ClassNotFoundException;

    /**
     * 审批任务
     *
     * @param leaveDto
    * @return
     */
    Boolean checkTask(LeaveDto leaveDto);

    /**
     * 通过任务ID 查询审批意见信息
     *
     * @param taskId 任务ID
     * @return
     */
    List<CommentDto> commitList(String taskId);

    /**
     * 追踪图片节点
     *
     * @param id
     * @return
     */
    InputStream trackImage(String id);
}
