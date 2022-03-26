package com.t1.sys.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.quartz.entity.JobLog;
import com.t1.sys.quartz.mapper.JobLogMapper;
import com.t1.sys.quartz.service.JobLogService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 定时任务执行日志表
 *
 * @author Bruce Lee ( copy )
 * @date 2019-06-28
 */
@Service
@AllArgsConstructor
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

}
