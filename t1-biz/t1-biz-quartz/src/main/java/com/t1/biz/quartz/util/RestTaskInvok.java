package com.t1.biz.quartz.util;

import com.t1.biz.quartz.entity.Job;
import com.t1.biz.quartz.exception.TaskException;
import com.t1.common.constant.CommonConstants;
import com.t1.common.model.R;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 定时任务rest反射实现
 *
 * @date 2019-06-28
 */
@Slf4j
@Component("restTaskInvok")
@AllArgsConstructor
public class RestTaskInvok implements ITaskInvok {

	private RestTemplate restTemplate;

	@Override
	public void invokMethod(Job job) throws TaskException {
		R r = restTemplate.getForObject(job.getExecutePath(), R.class);
		if (CommonConstants.FAIL == r.getCode()) {
			log.error("定时任务restTaskInvok异常,执行任务：{}", job.getExecutePath());
			throw new TaskException("定时任务restTaskInvok业务执行失败,任务：" + job.getExecutePath());
		}
	}
}
