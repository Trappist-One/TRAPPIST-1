package com.t1.biz.quartz.util;


import com.t1.biz.quartz.entity.Job;
import com.t1.biz.quartz.exception.TaskException;

/**
 * 定时任务反射实现接口类
 *
 * @date 2019-06-28
 */
public interface ITaskInvok {

	/**
	 * 执行反射方法
	 *
	 * @param job 配置类
	 * @throws TaskException
	 */
	void invokMethod(Job job) throws TaskException;
}
