package com.t1.biz.toolkit.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.biz.toolkit.entity.Form;
import com.t1.biz.toolkit.service.FormService;
import com.t1.common.model.R;
import com.t1.common.utils.ExcelUtil;
import io.swagger.annotations.Api;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bruce Lee ( copy )
 * @date 2021-03-11 21:57:03
 * @description 表单Controller
 */
@Api("表单管理")
@RestController
@AllArgsConstructor
@RequestMapping("/toolkit/form")
public class FormController {

    private final FormService formService;

    private QueryWrapper<Form> getQueryWrapper(Form form) {
        return new QueryWrapper<Form>()
                .like(StrUtil.isNotBlank(form.getName()), "name", form.getName())
                .eq(StrUtil.isNotBlank(form.getType()), "type", form.getType())
                .like(StrUtil.isNotBlank(form.getTableName()), "table_name", form.getTableName())
                .between(StrUtil.isNotBlank(form.getBeginTime()) && StrUtil.isNotBlank(form.getEndTime()), "create_time", form.getBeginTime(), form.getEndTime())
                .orderByDesc("id");
    }

    @OperLog("表单列表")
    @PreAuthorize("@ps.hasPerm('form_view')")
    @GetMapping("/list")
    public R list(Page page, Form form) {
        IPage<Form> formPage = formService.page(page, getQueryWrapper(form));
        return R.success(formPage.getRecords(), formPage.getTotal());
    }

    @OperLog("表单查询")
    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(formService.getById(id));
    }

    @OperLog("表单新增")
    @PreAuthorize("@ps.hasPerm('form_add')")
    @PostMapping("/save")
    public R save(@Validated @RequestBody Form form) {
        formService.save(form);
        return R.success();
    }

    @OperLog("表单修改")
    @PreAuthorize("@ps.hasPerm('form_edit')")
    @PutMapping("/update")
    public R update(@Validated @RequestBody Form form) {
        formService.updateById(form);
        return R.success();
    }

    @OperLog("表单删除")
    @PreAuthorize("@ps.hasPerm('form_del')")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") Integer[] id) {
        return R.success(formService.removeByIds(Arrays.asList(id)));
    }


    @PreAuthorize("@ps.hasPerm('form_export')")
    @GetMapping("/export")
    public R export(Form form) {
        List<Form> list = formService.list(getQueryWrapper(form));
        ExcelUtil<Form> util = new ExcelUtil<Form>(Form.class);
        return util.exportExcel(list, "表单数据");
    }

    @GetMapping("/customFormList")
    public R customFormList(Page page, Form form) {
        page = formService.customFormPage(page, form);
        return R.success(page.getRecords(), page.getTotal());
    }
}
