package com.t1.sys.quartz.event;

import com.t1.sys.quartz.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.quartz.Trigger;

/**
 * 定时任务多线程事件
 *
 * @date 2019-06-28
 */
@Getter
@AllArgsConstructor
public class JobEvent {

	private final Job job;

	private final Trigger trigger;
}
