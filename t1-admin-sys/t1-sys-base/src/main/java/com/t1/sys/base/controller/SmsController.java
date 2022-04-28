package com.t1.sys.base.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.sys.base.entity.Sms;
import com.t1.sys.base.service.SmsService;
import org.springframework.security.access.prepost.PreAuthorize;
import com.t1.common.model.R;
import com.t1.common.utils.ExcelUtil;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.t1.log.annotation.OperLog;

import java.util.List;
import java.util.Arrays;

/**
 * @author Bruce Lee
 * @date 2022-04-28 00:41:00
 * @description 消息中心Controller
 */
@Api("消息中心管理")
@RestController
@AllArgsConstructor
@RequestMapping("/system/sms")
public class SmsController {

    private final SmsService sysSmsService;

    private QueryWrapper<Sms> getQueryWrapper(Sms sysSms) {
        return new QueryWrapper<Sms>()
                .eq(StrUtil.isNotBlank(sysSms.getEsTitle()), "es_title", sysSms.getEsTitle())
                .eq(StrUtil.isNotBlank(sysSms.getEsType()), "es_type", sysSms.getEsType())
                .eq(StrUtil.isNotBlank(sysSms.getEsReceiver()), "es_receiver", sysSms.getEsReceiver())
                .eq(StrUtil.isNotBlank(sysSms.getEsParam()), "es_param", sysSms.getEsParam())
                .eq(StrUtil.isNotBlank(sysSms.getEsContent()), "es_content", sysSms.getEsContent())
                .eq(!StrUtil.isEmptyIfStr(sysSms.getEsSendTime()), "es_send_time", sysSms.getEsSendTime())
                .eq(StrUtil.isNotBlank(sysSms.getEsSendStatus()), "es_send_status", sysSms.getEsSendStatus())
                .eq(!StrUtil.isEmptyIfStr(sysSms.getEsSendNum()), "es_send_num", sysSms.getEsSendNum())
                .eq(StrUtil.isNotBlank(sysSms.getEsResult()), "es_result", sysSms.getEsResult())
                .orderByDesc("id");
    }

    @ApiOperation("消息中心列表")
    @PreAuthorize("@ps.hasPerm('sms_view')")
    @GetMapping("/list")
    public R list(Page page, Sms sysSms) {
        IPage<Sms> sysSmsPage = sysSmsService.page(page, getQueryWrapper(sysSms));
        return R.success(sysSmsPage.getRecords(), sysSmsPage.getTotal());
    }

    @ApiOperation("消息中心查询")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        Sms sysSms = sysSmsService.getById(id);
        return R.success(sysSms);
    }

    @OperLog("消息中心新增")
    @ApiOperation("消息中心新增")
    @PreAuthorize("@ps.hasPerm('sms_add')")
    @PostMapping("/save")
    public R save(@Validated @RequestBody Sms sysSms) {
        sysSmsService.save(sysSms);
        return R.success();
    }

    @OperLog("消息中心修改")
    @ApiOperation("消息中心修改")
    @PreAuthorize("@ps.hasPerm('sms_edit')")
    @PutMapping("/update")
    public R update(@Validated @RequestBody Sms sysSms) {
        sysSmsService.updateById(sysSms);
        return R.success();
    }


    @OperLog("消息中心删除")
    @ApiOperation("消息中心删除")
    @PreAuthorize("@ps.hasPerm('sms_del')")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") Integer[] id) {
        return R.success(sysSmsService.removeByIds(Arrays.asList(id)));
    }

    @PreAuthorize("@ps.hasPerm('sms_export')")
    @GetMapping("/export")
    public R export(Sms sysSms) {
        List<Sms> list = sysSmsService.list(getQueryWrapper(sysSms));
        ExcelUtil<Sms> util = new ExcelUtil<Sms>(Sms.class);
        return util.exportExcel(list, "消息中心数据");
    }
}
