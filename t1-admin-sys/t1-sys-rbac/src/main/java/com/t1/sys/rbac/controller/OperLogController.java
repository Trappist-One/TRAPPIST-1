package com.t1.sys.rbac.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.common.model.R;
import com.t1.sys.rbac.entity.OperLog;
import com.t1.sys.rbac.service.OperLogService;
import com.t1.common.utils.ExcelUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统操作记录
 *
 * @author Bruce Lee ( copy )
 */
@RestController
@AllArgsConstructor
@RequestMapping("/monitor/operLog")
public class OperLogController {

    private final OperLogService operLogService;

    private QueryWrapper<OperLog> getQueryWrapper(OperLog operLog) {
        return new QueryWrapper<OperLog>().like(StrUtil.isNotBlank(operLog.getOperName()), "oper_name", operLog.getOperName()).like(StrUtil.isNotBlank(operLog.getTitle()), "title", operLog.getTitle()).eq(!StrUtil.isEmptyIfStr(operLog.getStatus()), "status", operLog.getStatus())
                .between(StrUtil.isNotBlank(operLog.getBeginTime()) && StrUtil.isNotBlank(operLog.getEndTime()), "oper_time", operLog.getBeginTime(), operLog.getEndTime()).orderByDesc("id");
    }

    @PreAuthorize("@ps.hasPerm('operLog_view')")
    @GetMapping("/list")
    public R list(Page page, OperLog operLog) {
        IPage<OperLog> operLogPage = operLogService.page(page, getQueryWrapper(operLog));
        return R.success(operLogPage.getRecords(), operLogPage.getTotal());
    }

    @com.t1.log.annotation.OperLog("操作日志删除")
    @PreAuthorize("@ps.hasPerm('operLog_del')")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable String[] id) {
        try {
            operLogService.removeByIds(Arrays.asList(id));
            return R.success();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @com.t1.log.annotation.OperLog("操作日志清空")
    @PreAuthorize("@ps.hasPerm('operLog_del')")
    @DeleteMapping("/clean")
    public R clean() {
        operLogService.remove(new QueryWrapper<>());
        return R.success();
    }

    @SneakyThrows
    @com.t1.log.annotation.OperLog("操作日志" )
    @PreAuthorize("@ps.hasPerm('operLog_export')" )
    @GetMapping("/export" )
    public R export(OperLog operLog) {
        List<OperLog> list = operLogService.list(getQueryWrapper(operLog));
        ExcelUtil<OperLog> util = new ExcelUtil<OperLog>(OperLog.class);
        return util.exportExcel(list, "操作日志" );
    }
}
