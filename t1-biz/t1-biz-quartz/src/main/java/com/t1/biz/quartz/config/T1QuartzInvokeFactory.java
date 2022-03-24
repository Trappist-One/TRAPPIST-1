package com.t1.biz.quartz.config;

import com.t1.biz.quartz.entity.Job;
import com.t1.biz.quartz.event.JobEvent;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.quartz.Trigger;
import org.springframework.context.ApplicationEventPublisher;

/**
 *
 * @date 2019-06-28
 */
@Aspect
@Slf4j
@AllArgsConstructor
public class T1QuartzInvokeFactory {

	private final ApplicationEventPublisher publisher;

	@SneakyThrows
	void init(Job job, Trigger trigger) {
		publisher.publishEvent(new JobEvent(job, trigger));
	}
}
