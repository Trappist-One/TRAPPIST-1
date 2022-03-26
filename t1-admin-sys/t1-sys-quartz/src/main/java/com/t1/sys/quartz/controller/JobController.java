package com.t1.sys.quartz.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.sys.quartz.entity.Job;
import com.t1.sys.quartz.entity.JobLog;
import com.t1.sys.quartz.enums.T1QuartzEnum;
import com.t1.sys.quartz.service.JobLogService;
import com.t1.sys.quartz.service.JobService;
import com.t1.sys.quartz.util.TaskUtil;
import com.t1.common.model.R;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务管理
 *
 * @author Bruce Lee ( copy )
 * @date 2019-06-28
 */
@RestController
@AllArgsConstructor
@RequestMapping("/quartz/job")
public class JobController {
    private final JobService jobService;
    private final JobLogService jobLogService;
    private final TaskUtil taskUtil;
    private final Scheduler scheduler;

    /**
     * 定时任务分页查询
     *
     * @param page 分页对象
     * @param job  定时任务调度表
     * @return
     */
    @GetMapping("/list")
    public R list(Page page, Job job) {
        IPage<Job> jobPage = jobService.page(page, Wrappers.query(job));
        return R.success(jobPage.getRecords(), jobPage.getTotal());
    }


    /**
     * 通过id查询定时任务
     *
     * @param id id
     * @return R
     */
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(jobService.getById(id));
    }

    /**
     * 新增定时任务
     *
     * @param job 定时任务调度表
     * @return R
     */
    @OperLog("定时任务新增")
    @PostMapping
    @PreAuthorize("@ps.hasPerm('job_add')")
    public R save(@RequestBody Job job) {
        job.setJobStatus(T1QuartzEnum.JOB_STATUS_RELEASE.getType());
        return R.success(jobService.save(job));
    }

    /**
     * 修改定时任务
     *
     * @param job 定时任务调度表
     * @return R
     */
    @OperLog("定时任务修改")
    @PutMapping
    @PreAuthorize("@ps.hasPerm('job_edit')")
    public R updateById(@RequestBody Job job) {
        Job queryJob = jobService.getById(job.getId());
        if (T1QuartzEnum.JOB_STATUS_NOT_RUNNING.getType().equals(queryJob.getJobStatus())) {
            taskUtil.addOrUpateJob(job, scheduler);
            jobService.updateById(job);
        } else if (T1QuartzEnum.JOB_STATUS_RELEASE.getType().equals(queryJob.getJobStatus())) {
            jobService.updateById(job);
        }
        return R.success();
    }

    /**
     * 通过id删除定时任务
     *
     * @param id id
     * @return R
     */
    @OperLog("删除定时任务")
    @DeleteMapping("/{id}")
    @PreAuthorize("@ps.hasPerm('job_del')")
    public R removeById(@PathVariable Integer id) {
        Job queryJob = jobService.getById(id);
        if (T1QuartzEnum.JOB_STATUS_NOT_RUNNING.getType().equals(queryJob.getJobStatus())) {
            taskUtil.removeJob(queryJob, scheduler);
            jobService.removeById(id);
        } else if (T1QuartzEnum.JOB_STATUS_RELEASE.getType().equals(queryJob.getJobStatus())) {
            jobService.removeById(id);
        }
        return R.success();
    }

    /**
     * 立即执行定时任务一次
     */
    @OperLog("执行定时任务一次")
    @PostMapping("/runJob")
    @PreAuthorize("@ps.hasPerm('job_start')")
    public R runJob(@RequestBody Job job) {
        taskUtil.runJob(job, scheduler);
        return R.success();
    }

    /**
     * 启动全部定时任务
     *
     * @return
     */
    @OperLog("启动全部定时任务")
    @PostMapping("/startJobs")
    @PreAuthorize("@ps.hasPerm('job_start')")
    public R startJobs() {
        //更新定时任务状态条件，暂停状态3更新为运行状态2
        jobService.update(Job.builder().jobStatus(T1QuartzEnum.JOB_STATUS_RUNNING
                .getType()).build(), new UpdateWrapper<Job>().lambda()
                .eq(Job::getJobStatus, T1QuartzEnum.JOB_STATUS_NOT_RUNNING.getType()));
        taskUtil.startJobs(scheduler);
        return R.success();
    }

    /**
     * 暂停全部定时任务
     *
     * @return
     */
    @OperLog("暂停全部定时任务")
    @PostMapping("/stopJobs")
    @PreAuthorize("@ps.hasPerm('job_stop')")
    public R stopJobs() {
        taskUtil.pauseJobs(scheduler);
        int count = jobService.count(new LambdaQueryWrapper<Job>()
                .eq(Job::getJobStatus, T1QuartzEnum.JOB_STATUS_RUNNING.getType()));
        if (count <= 0) {
            return R.success("无正在运行定时任务");
        } else {
            //更新定时任务状态条件，运行状态2更新为暂停状态2
            jobService.update(Job.builder()
                    .jobStatus(T1QuartzEnum.JOB_STATUS_NOT_RUNNING.getType()).build(), new UpdateWrapper<Job>()
                    .lambda().eq(Job::getJobStatus, T1QuartzEnum.JOB_STATUS_RUNNING.getType()));
            return R.success("暂停成功");
        }
    }

    /**
     * 刷新全部定时任务
     *
     * @return
     */
    @OperLog("刷新全部定时任务")
    @PostMapping("/refreshJobs")
    @PreAuthorize("@ps.hasPerm('job_refresh')")
    public R refreshJobs() {
        jobService.list().forEach((job) -> {
            if (T1QuartzEnum.JOB_STATUS_RELEASE.getType().equals(job.getJobStatus())
                    || T1QuartzEnum.JOB_STATUS_DEL.getType().equals(job.getJobStatus())) {
                taskUtil.removeJob(job, scheduler);
            } else if (T1QuartzEnum.JOB_STATUS_RUNNING.getType().equals(job.getJobStatus())
                    || T1QuartzEnum.JOB_STATUS_NOT_RUNNING.getType().equals(job.getJobStatus())) {
                taskUtil.addOrUpateJob(job, scheduler);
            } else {
                taskUtil.removeJob(job, scheduler);
            }
        });
        return R.success();
    }

    /**
     * 启动定时任务
     *
     * @param id
     * @return
     */
    @OperLog("启动定时任务")
    @PostMapping("/startJob/{id}")
    @PreAuthorize("@ps.hasPerm('job_start')")
    public R startJob(@PathVariable("id") Integer id) {
        Job queryJob = jobService.getById(id);
        if (queryJob != null && T1QuartzEnum.JOB_LOG_STATUS_FAIL.getType()
                .equals(queryJob.getJobStatus())) {
            taskUtil.addOrUpateJob(queryJob, scheduler);
        } else {
            taskUtil.resumeJob(queryJob, scheduler);
        }
        //更新定时任务状态条件，暂停状态3更新为运行状态2
        jobService.updateById(Job.builder().id(id)
                .jobStatus(T1QuartzEnum.JOB_STATUS_RUNNING.getType()).build());
        return R.success();
    }

    /**
     * 暂停定时任务
     *
     * @return
     */
    @OperLog("暂停定时任务")
    @PostMapping("/stopJob/{id}")
    @PreAuthorize("@ps.hasPerm('job_stop')")
    public R shutdownJob(@PathVariable("id") Integer id) {
        Job queryJob = jobService.getById(id);
        //更新定时任务状态条件，运行状态2更新为暂停状态3
        jobService.updateById(Job.builder().id(queryJob.getId())
                .jobStatus(T1QuartzEnum.JOB_STATUS_NOT_RUNNING.getType()).build());
        taskUtil.pauseJob(queryJob, scheduler);
        return R.success();
    }

    /**
     * 查询定时执行日志
     *
     * @return
     */
    @GetMapping("/jobLog")
    public R getJobLog(Page page, JobLog jobLog) {
        return R.success(jobLogService.page(page, Wrappers.query(jobLog)));
    }

    /**
     * 检验任务名称和任务组联合是否唯一
     *
     * @return
     */
    @GetMapping("/isValidName")
    public R isValidTaskName(@RequestParam String jobName, @RequestParam String jobGroup) {
        return jobService
                .count(Wrappers.query(Job.builder().jobName(jobName).jobGroup(jobGroup).build())) > 0
                ? R.error() : R.success();
    }
}