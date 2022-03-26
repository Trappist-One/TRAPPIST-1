package com.t1.sys.quartz.config;

import com.t1.sys.quartz.entity.Job;
import com.t1.sys.quartz.enums.T1QuartzEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 动态任务工厂
 *
 * @date 2019-06-28
 */
@Slf4j
@DisallowConcurrentExecution
public class T1QuartzFactory implements org.quartz.Job {

	@Autowired
	private T1QuartzInvokeFactory t1QuartzInvokeFactory;


	@Override
	@SneakyThrows
	public void execute(JobExecutionContext jobExecutionContext) {
		Job job = (Job) jobExecutionContext.getMergedJobDataMap().get(T1QuartzEnum.SCHEDULE_JOB_KEY.getType());
		t1QuartzInvokeFactory.init(job, jobExecutionContext.getTrigger());
	}
}
