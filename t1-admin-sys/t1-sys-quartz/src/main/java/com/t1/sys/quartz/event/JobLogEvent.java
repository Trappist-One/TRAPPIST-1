package com.t1.sys.quartz.event;

import com.t1.sys.quartz.entity.JobLog;
import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * 定时任务日志多线程事件
 *
 * @date 2019-06-28
 */
@Getter
@AllArgsConstructor
public class JobLogEvent {
	private final JobLog jobLog;
}
