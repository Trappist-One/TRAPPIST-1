package com.t1.sys.activiti.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1.sys.activiti.dto.ProcessDto;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/3/23
 */
public interface ProcessService {

    /**
     * 分页流程列表
     *
     * @param params
     * @return
     */
    IPage<ProcessDto> list(Map<String, Object> params);

    /**
     * 读取xml/image资源
     *
     * @param procInsId
     * @param procDefId
     * @param resType
     * @return
     */
    InputStream readResource(String procInsId, String procDefId, String resType);

    /**
     * 更新状态
     *
     * @param status
     * @param procDefId
     * @return
     */
    Boolean changeStatus(String procDefId, String status);

    /**
     * 删除流程实例
     *
     * @param deployId
     * @return
     */
    Boolean removeProcIns(String deployId);

    /**
     * 启动流程
     *
     * @param id
     * @return
     */
    Boolean startLeaveProcess(Integer id, String username);
}
