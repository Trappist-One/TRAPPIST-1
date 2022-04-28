package com.t1.sys.base.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.common.model.R;
import com.t1.sys.base.entity.LoginLog;
import com.t1.sys.base.service.LoginLogService;
import com.t1.common.utils.ExcelUtil;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统登录记录
 *
 * @author Bruce Lee ( copy )
 */
@RestController
@AllArgsConstructor
@RequestMapping("/monitor/loginLog")
public class LoginLogController {

    private final LoginLogService loginLogService;

    private QueryWrapper<LoginLog> getQueryWrapper(LoginLog loginLog) {
        return new QueryWrapper<LoginLog>().like(StrUtil.isNotBlank(loginLog.getLoginName()), "login_name", loginLog.getLoginName()).like(StrUtil.isNotBlank(loginLog.getLoginIp()), "login_ip", loginLog.getLoginIp())
                .eq(StrUtil.isNotBlank(loginLog.getStatus()), "status", loginLog.getStatus()).between(StrUtil.isNotBlank(loginLog.getBeginTime()) && StrUtil.isNotBlank(loginLog.getEndTime()), "login_time", loginLog.getBeginTime(), loginLog.getEndTime()).orderByDesc("id");
    }

    @PreAuthorize("@ps.hasPerm('loginLog_view')")
    @GetMapping("/list")
    public R list(Page page, LoginLog loginLog) {
        IPage<LoginLog> loginLogPage = loginLogService.page(page, getQueryWrapper(loginLog));
        return R.success(loginLogPage.getRecords(), loginLogPage.getTotal());
    }

    @OperLog("登录日志删除")
    @PreAuthorize("@ps.hasPerm('loginLog_del')")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable Integer[] id) {
        try {
            loginLogService.removeByIds(Arrays.asList(id));
            return R.success();
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @OperLog("登录日志清空")
    @PreAuthorize("@ps.hasPerm('loginLog_del')")
    @DeleteMapping("/clean")
    public R clean() {
        loginLogService.remove(new QueryWrapper<>());
        return R.success();
    }

    @SneakyThrows
    @OperLog("登录日志" )
    @PreAuthorize("@ps.hasPerm('loginLog_export')" )
    @GetMapping("/export" )
    public R export(LoginLog loginLog) {
        List<LoginLog> list = loginLogService.list(getQueryWrapper(loginLog));
        ExcelUtil<LoginLog> util = new ExcelUtil<LoginLog>(LoginLog.class);
        return util.exportExcel(list, "登录日志" );
    }
}
