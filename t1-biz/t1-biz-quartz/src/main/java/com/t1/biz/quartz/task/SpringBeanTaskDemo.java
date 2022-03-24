package com.t1.biz.quartz.task;

import com.t1.biz.quartz.enums.T1QuartzEnum;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 测试SpringBean调用的demo
 *
 * @author Bruce Lee ( copy )
 * @date 2019-06-28
 */
@Slf4j
@Component("demo")
public class SpringBeanTaskDemo {
	/**
	 * 测试Spring Bean的演示方法
	 */
	@SneakyThrows
	public String demoMethod(String para) {
		log.info("测试于:{}，输入参数{}", LocalDateTime.now(), para);
		return T1QuartzEnum.JOB_LOG_STATUS_SUCCESS.getType();
	}
}
