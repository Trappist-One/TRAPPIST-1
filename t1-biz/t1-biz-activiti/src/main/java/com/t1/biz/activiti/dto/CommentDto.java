package com.t1.biz.activiti.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 审批意见 dto
 */
@Data
public class CommentDto {

    private String id;
    private String userId;
    private Date time;
    private String taskId;
    private String processInstanceId;
    private String type;
    private String fullMessage;

    //其他信息

}
