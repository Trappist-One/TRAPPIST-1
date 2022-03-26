package com.t1.sys.activiti.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 我发起流程:传输实体类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/14
 */
@Data
public class IInitiatedProcessDto {


    /** 流程编号 **/
    private String procDefId;
    /** 流程实例ID **/
    private String procInstId;
    /** 发起人 **/
    private String startUserId;
    /** 开始日期 **/
    private Date startTime;
    /** 结束时间 **/
    private Date endTime;
    /** 耗时 **/
    private String duration;
    /** 业务标题 **/
    private String businessTitle;
    /** 流程名称 **/
    private String processName;
    /** 状态 **/
    private String status;
    /** 业务数据Key **/
    private String businessKey;

}
