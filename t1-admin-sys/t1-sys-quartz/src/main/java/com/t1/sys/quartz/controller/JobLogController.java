package com.t1.sys.quartz.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.sys.quartz.entity.JobLog;
import com.t1.sys.quartz.service.JobLogService;
import com.t1.common.model.R;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 定时任务执行日志表
 *
 * @author Bruce Lee ( copy )
 * @date 2019-06-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/quartz/jobLog")
public class JobLogController {
    private final JobLogService jobLogService;

    private QueryWrapper<JobLog> getQueryWrapper(JobLog jobLog) {
        return new QueryWrapper<JobLog>().like(StrUtil.isNotBlank(jobLog.getJobName()), "job_name", jobLog.getJobName()).like(StrUtil.isNotBlank(jobLog.getJobGroup()), "job_group", jobLog.getJobGroup()).eq(StrUtil.isNotBlank(jobLog.getJobLogStatus()), "job_log_status", jobLog.getJobLogStatus())
                .between(StrUtil.isNotBlank(jobLog.getBeginTime()) && StrUtil.isNotBlank(jobLog.getEndTime()), "create_time", jobLog.getBeginTime(), jobLog.getEndTime()).orderByDesc("create_time");
    }

    @GetMapping("/list")
    public R list(Page page, JobLog jobLog) {
        IPage<JobLog> jobLogPage = jobLogService.page(page, getQueryWrapper(jobLog));
        return R.success(jobLogPage.getRecords(), jobLogPage.getTotal());
    }

    @OperLog("定时任务日志删除")
    @PreAuthorize("@ps.hasPerm('jobLog_del')")
    @DeleteMapping("/{id}")
    public R removeById(@PathVariable Integer[] id) {
        jobLogService.removeByIds(Arrays.asList(id));
        return R.success();
    }
}
