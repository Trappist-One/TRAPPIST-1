package com.t1.sys.activiti.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.sys.activiti.entity.Leave;
import com.t1.sys.activiti.service.LeaveService;
import com.t1.sys.activiti.service.ProcessService;
import com.t1.common.annotation.LoginUser;
import com.t1.common.entity.User;
import com.t1.common.enums.TaskStatusEnum;
import com.t1.common.model.R;
import com.t1.common.utils.ExcelUtil;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 请假 controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/activiti/leave")
public class LeaveController {
    private final LeaveService leaveService;
    private final ProcessService processService;

    private QueryWrapper<Leave> getQueryWrapper(Leave leave) {
        return new QueryWrapper<Leave>().like(StrUtil.isNotBlank(leave.getUserName()), "user_name", leave.getUserName()).eq(StrUtil.isNotBlank(leave.getType()), "type", leave.getType()).eq(StrUtil.isNotBlank(leave.getStatus()), "status", leave.getStatus())
                .between(StrUtil.isNotBlank(leave.getBeginTime()) && StrUtil.isNotBlank(leave.getEndTime()), "create_time", leave.getBeginTime(), leave.getEndTime()).apply(StrUtil.isNotBlank(leave.getSqlFilter()), leave.getSqlFilter());
    }

    @GetMapping("/list")
    public R list(Page page, Leave leave) {
        IPage<Leave> leaveIPage = leaveService.page(page, getQueryWrapper(leave));
        return R.success(leaveIPage.getRecords(), leaveIPage.getTotal());
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(leaveService.getById(id));
    }

     @OperLog("请假新增")
    @PreAuthorize("@ps.hasPerm('leave_add')")
    @PostMapping("/save")
    public R save(@LoginUser User user, @RequestBody Leave leave) {
        leave.setUserName(user.getUserName());
        leave.setStatus(TaskStatusEnum.UN_SUBMIT.getStatus());
        return R.success(leaveService.save(leave));
    }

     @OperLog("请假修改 ")
    @PreAuthorize("@ps.hasPerm('leave_edit')")
    @PutMapping("/update")
    public R update(@RequestBody Leave leave) {
        return R.success(leaveService.updateById(leave));
    }

     @OperLog("请假删除")
    @PreAuthorize("@ps.hasPerm('leave_del')")
    @DeleteMapping("/remove/{id}")
    public R removeById(@PathVariable Integer[] id) {
        return R.success(leaveService.removeByIds(Arrays.asList(id)));
    }

     @OperLog("启动请假流程")
    @PreAuthorize("@ps.hasPerm('leave_edit')")
    @GetMapping("/startProcess/{id}")
    public R startProcess(@LoginUser User user, @PathVariable("id") Integer id) {
        return R.success(processService.startLeaveProcess(id, user.getUserName()));
    }

     @OperLog("请假导出")
    @PreAuthorize("@ps.hasPerm('leave_export')")
    @GetMapping("/export")
    @ResponseBody
    public R export(Leave leave) {
        List<Leave> list = leaveService.list(getQueryWrapper(leave));
        ExcelUtil<Leave> util = new ExcelUtil<Leave>(Leave.class);
        return util.exportExcel(list, "请假数据");
    }
}
