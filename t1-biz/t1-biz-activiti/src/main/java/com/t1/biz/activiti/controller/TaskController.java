package com.t1.biz.activiti.controller;

import cn.hutool.core.io.IoUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1.biz.activiti.dto.LeaveDto;
import com.t1.biz.activiti.dto.TaskDto;
import com.t1.biz.activiti.service.TaskService;
import com.t1.common.annotation.LoginUser;
import com.t1.common.entity.User;
import com.t1.common.model.R;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.util.Map;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 任务 controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/activiti/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/list")
    public R list(@LoginUser User user, @RequestParam Map<String, Object> params) {
        IPage<TaskDto> taskIPage = taskService.list(params, user.getUserName());
        return R.success(taskIPage.getRecords(), taskIPage.getTotal());
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable String id) throws ClassNotFoundException {
        return R.success(taskService.getTaskById(id));
    }


    @GetMapping("history/{id}")
    public R getHistoryById(@PathVariable String id) throws ClassNotFoundException {
        return R.success(taskService.getHistoryTaskById(id));
    }


     @OperLog("任务审批")
    @PostMapping("/checkTask")
    public R checkTask(@RequestBody LeaveDto leaveDto) {
        return R.success(taskService.checkTask(leaveDto));
    }

     @OperLog("任务审批意见")
    @GetMapping("/commitList/{id}")
    public R commitList(@PathVariable String id) {
        return R.success(taskService.commitList(id));
    }

     @OperLog("任务追踪")
    @GetMapping("/track/{id}")
    public ResponseEntity trackImage(@PathVariable String id) {
        InputStream imageStream = taskService.trackImage(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity(IoUtil.readBytes(imageStream), headers, HttpStatus.CREATED);
    }

    /** 审批历史任务节点查询 **/
    @GetMapping("/historyList")
    public R historyList(@LoginUser User user, @RequestParam Map<String, Object> params) {
        IPage<TaskDto> taskIPage = taskService.historyList(params, user.getUserName());
        return R.success(taskIPage.getRecords(), taskIPage.getTotal());
    }


}
