package com.t1.sys.activiti.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 任务 dto
 */
@Data
public class TaskDto {
	private String taskId;
	private String taskName;
	private String title;
	private String pdName;
	private String processInstanceId;
	private String status;
	private String nodeKey;
	private String processDefKey;
	private String category;
	private String version;
	private Date createTime;
}
