package com.t1.sys.quartz.event;

import com.t1.sys.quartz.config.T1QuartzInvokeFactory;
import com.t1.sys.quartz.service.JobLogService;
import com.t1.sys.quartz.util.TaskInvokUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 多线程自动配置
 *
 * @date 2019-06-28
 */
@EnableAsync
@Configuration
@ConditionalOnWebApplication
public class EventAutoConfiguration {
	@Autowired
	private TaskInvokUtil taskInvokUtil;
	@Autowired
	private JobLogService jobLogService;

	@Bean
	public JobListener jobListener() {
		return new JobListener(taskInvokUtil);
	}

	@Bean
	public T1QuartzInvokeFactory tmQuartzInvokeFactory(ApplicationEventPublisher publisher) {
		return new T1QuartzInvokeFactory(publisher);
	}

	@Bean
	public JobLogListener jobLogListener() {
		return new JobLogListener(jobLogService);
	}

	@Bean
	public TaskInvokUtil taskInvokUtil(ApplicationEventPublisher publisher) {
		return new TaskInvokUtil(publisher);
	}

}
