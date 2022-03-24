package com.t1.biz.activiti.dto;

import com.t1.biz.activiti.entity.Leave;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/3/24
 * @description 请假 dto
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LeaveDto extends Leave {
	/**
	 * 任务ID
	 */
	private String taskId;
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
