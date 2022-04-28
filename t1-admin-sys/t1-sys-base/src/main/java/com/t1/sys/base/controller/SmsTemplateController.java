package com.t1.sys.base.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.sys.base.entity.SmsTemplate;
import com.t1.sys.base.service.SmsTemplateService;
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
@RequestMapping("/system/smsTemplate")
public class SmsTemplateController {

    private final SmsTemplateService sysSmsTemplateService;

    private QueryWrapper<SmsTemplate> getQueryWrapper(SmsTemplate sysSmsTemplate) {
        return new QueryWrapper<SmsTemplate>()
                .like(StrUtil.isNotBlank(sysSmsTemplate.getTemplateName()), "template_name", sysSmsTemplate.getTemplateName())
                .eq(StrUtil.isNotBlank(sysSmsTemplate.getTemplateCode()), "template_code", sysSmsTemplate.getTemplateCode())
                .eq(StrUtil.isNotBlank(sysSmsTemplate.getTemplateType()), "template_type", sysSmsTemplate.getTemplateType())
                .eq(StrUtil.isNotBlank(sysSmsTemplate.getTemplateContent()), "template_content", sysSmsTemplate.getTemplateContent())
                .eq(StrUtil.isNotBlank(sysSmsTemplate.getTemplateTestJson()), "template_test_json", sysSmsTemplate.getTemplateTestJson())
                .orderByDesc("id");
    }

    @ApiOperation("消息中心列表")
    @PreAuthorize("@ps.hasPerm('smsTemplate_view')")
    @GetMapping("/list")
    public R list(Page page, SmsTemplate sysSmsTemplate) {
        IPage<SmsTemplate> sysSmsTemplatePage = sysSmsTemplateService.page(page, getQueryWrapper(sysSmsTemplate));
        return R.success(sysSmsTemplatePage.getRecords(), sysSmsTemplatePage.getTotal());
    }

    @ApiOperation("消息中心查询")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        SmsTemplate sysSmsTemplate = sysSmsTemplateService.getById(id);
        return R.success(sysSmsTemplate);
    }

    @OperLog("消息中心新增")
    @ApiOperation("消息中心新增")
    @PreAuthorize("@ps.hasPerm('smsTemplate_add')")
    @PostMapping("/save")
    public R save(@Validated @RequestBody SmsTemplate sysSmsTemplate) {
        sysSmsTemplateService.save(sysSmsTemplate);
        return R.success();
    }

    @OperLog("消息中心修改")
    @ApiOperation("消息中心修改")
    @PreAuthorize("@ps.hasPerm('smsTemplate_edit')")
    @PutMapping("/update")
    public R update(@Validated @RequestBody SmsTemplate sysSmsTemplate) {
        sysSmsTemplateService.updateById(sysSmsTemplate);
        return R.success();
    }


    @OperLog("消息中心删除")
    @ApiOperation("消息中心删除")
    @PreAuthorize("@ps.hasPerm('smsTemplate_del')")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") Integer[] id) {
        return R.success(sysSmsTemplateService.removeByIds(Arrays.asList(id)));
    }


    @PreAuthorize("@ps.hasPerm('smsTemplate_export')")
    @GetMapping("/export")
    public R export(SmsTemplate sysSmsTemplate) {
        List<SmsTemplate> list = sysSmsTemplateService.list(getQueryWrapper(sysSmsTemplate));
        ExcelUtil<SmsTemplate> util = new ExcelUtil<SmsTemplate>(SmsTemplate.class);
        return util.exportExcel(list, "消息中心数据");
    }
}
