package com.t1.biz.activiti.dto;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 纤细流程追踪DTO
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/16
 */
@Data
public class DetailedProcessTrackDto {

    /** 节点名称 **/
    private String actName;
    /** 流程实例ID **/
    private String procInstId;
    /** 开始日期 **/
    private Date startTime;
    /** 结束时间 **/
    private Date endTime;
    /** 负责人 **/
    private String assignee;
    /** 处理结果 **/
    private String processResult;
    /** 审批意见 **/
    private String message;

}
