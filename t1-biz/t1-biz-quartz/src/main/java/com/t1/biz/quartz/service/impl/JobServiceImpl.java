package com.t1.biz.quartz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.biz.quartz.entity.Job;
import com.t1.biz.quartz.mapper.JobMapper;
import com.t1.biz.quartz.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度表
 *
 * @author Bruce Lee ( copy )
 * @date 2019-06-28
 */
@Service
@AllArgsConstructor
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

}
