package com.t1.sys.activiti.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.InputStream;
import java.util.Map;

/**
 * <p>
 * 我发起流程业务层接口
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/14
 */
public interface IInitiatedProcessService {


    /**
     * 获取我的发起流程列表
     *
     * @param params
     * @return
     */
    IPage list(Map<String, Object> params, String username);


    /**
     * 获取当前流程的详细流程追踪信息
     *
     * @param params
     * @return
     */
    IPage listDetailedProcess(Map<String, Object> params);

    /** 作废流程 **/
    void abolishProcess(String procInstId,String businessKey) throws ClassNotFoundException;

    /** 流程追踪 **/
    InputStream fetchProcessTrackingPicture(String processInstanceId);

}
