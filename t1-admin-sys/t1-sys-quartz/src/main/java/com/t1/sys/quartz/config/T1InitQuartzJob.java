package com.t1.sys.quartz.config;

import com.t1.sys.quartz.enums.T1QuartzEnum;
import com.t1.sys.quartz.service.JobService;
import com.t1.sys.quartz.util.TaskUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化加载定时任务
 *
 * @date 2019-06-28
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class T1InitQuartzJob {
	private final JobService jobService;
	private final TaskUtil taskUtil;
	private final Scheduler scheduler;

	@Bean
	public void customize() {
		jobService.list().forEach(job -> {
			if (T1QuartzEnum.JOB_STATUS_RELEASE.getType().equals(job.getJobStatus())) {
				taskUtil.removeJob(job, scheduler);
			} else if (T1QuartzEnum.JOB_STATUS_RUNNING.getType().equals(job.getJobStatus())) {
				taskUtil.resumeJob(job, scheduler);
			} else if (T1QuartzEnum.JOB_STATUS_NOT_RUNNING.getType().equals(job.getJobStatus())) {
				taskUtil.pauseJob(job, scheduler);
			} else {
				taskUtil.removeJob(job, scheduler);
			}
		});
	}
}
