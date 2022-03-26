package com.t1.sys.activiti.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 *<p>
 *     审批DTO传输
 * 任务集成动态表单参数Dto
 *</p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/22
 */
@Data
public class TaskCheckDto{
	/**
	 * 任务ID
	 */
	private String taskId;

	/**
	 * 业务数据唯一Key
	 */
	private String businessKey;

	/**
	 * 表单编码
	 */
	private String formCode;

	/**
	 * 表单数据自定义请求地址
	 */
	private String formDataUrl;

	/**
	 * 任务名称
	 */
	private String taskName;

	/**
	 * 批注信息
	 */
	private String comment;

	/**
	 * 连线信息
	 */
	private List<String> flagList;

	/**
	 * 任务连线
	 */
	private String taskFlag;

	/**
	 * 任务提交时间
	 */
	private Date time;

}
