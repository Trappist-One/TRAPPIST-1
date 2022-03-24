package com.t1.biz.system.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import cn.hutool.system.OsInfo;
import cn.hutool.system.SystemUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.common.config.GlobalConfig;
import com.t1.common.constant.CommonConstants;
import com.t1.common.model.R;
import com.t1.biz.system.entity.Backup;
import com.t1.biz.system.service.BackupService;
import com.t1.common.utils.DateUtil;
import com.t1.log.annotation.OperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Bruce Lee ( copy )
 * @date 2020-03-02 16:33:24
 * @description 备份Controller
 */
@RestController
@RequestMapping("/system/backup")
public class BackupController {

    @Value("${spring.datasource.druid.username}")
    private String username;

    @Value("${spring.datasource.druid.password}")
    private String password;

    @Autowired
    private BackupService backupService;

    private QueryWrapper<Backup> getQueryWrapper(Backup backup) {
        return new QueryWrapper<Backup>().like(StrUtil.isNotBlank(backup.getName()), "name", backup.getName())
                .between(StrUtil.isNotBlank(backup.getBeginTime()) && StrUtil.isNotBlank(backup.getEndTime()), "create_time", backup.getBeginTime(), backup.getEndTime()).orderByDesc("create_time");
    }

    @PreAuthorize("@ps.hasPerm('backup_view')")
    @GetMapping("/list")
    @ResponseBody
    public R list(Page page, Backup backup) {
        IPage<Backup> backupPage = backupService.page(page, getQueryWrapper(backup));
        return R.success(backupPage.getRecords(), backupPage.getTotal());
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(backupService.getById(id));
    }

    @OperLog("备份新增")
    @PreAuthorize("@ps.hasPerm('backup_add')")
    @PostMapping("/save")
    @ResponseBody
    @Transactional
    public R save(@RequestBody Backup backup) {
        String name = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN);
        backup.setName(name);
        String filePath = GlobalConfig.getProfile() + "backup/";
        File uploadDir = new File(filePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String cmd = getOsCmd() + "mysqldump -u" + username + " -p" + password + " " + CommonConstants.DB_NAME + " > "
                + filePath + CommonConstants.DB_NAME + "_" + name + ".sql";
        backup.setPath(filePath + CommonConstants.DB_NAME + "_" + name + ".sql");
        //执行备份命令
        try {
            StaticLog.info("执行备份命令：" + cmd);
            RuntimeUtil.exec(cmd);
        } catch (Exception ex) {
            return R.error(ex.getMessage());
        }
        backupService.save(backup);
        return R.success();
    }

    @OperLog("备份还原")
    @PreAuthorize("@ps.hasPerm('backup_restore')")
    @GetMapping("/restore/{id}")
    @ResponseBody
    public R restore(@PathVariable("id") Integer id) {
        Backup backup = backupService.getById(id);
        if (backup != null) {
            String cmd = getOsCmd() + "mysql -u" + username + " -p" + password + " " + CommonConstants.DB_NAME + " < " + backup.getPath();
            //执行还原命令
            try {
                StaticLog.info("执行还原命令：" + cmd);
                RuntimeUtil.exec(cmd);
            } catch (Exception ex) {
                return R.error(ex.getMessage());
            }
        }
        return R.success();
    }

    @OperLog("备份删除")
    @PreAuthorize("@ps.hasPerm('backup_del')")
    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public R remove(@PathVariable("id") Integer[] id) {
        //自行增加删除本地文件
        return R.success(backupService.removeByIds(Arrays.asList(id)));
    }

    private String getOsCmd() {
        OsInfo osInfo = SystemUtil.getOsInfo();
        String cmd = "";
        if (osInfo.isWindows()) {
            cmd = "cmd /c ";
        }
        return cmd;
    }

}
