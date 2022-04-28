package com.t1.sys.base.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.common.model.R;
import com.t1.sys.base.entity.DictData;
import com.t1.sys.base.service.DictDataService;
import com.t1.sys.base.service.DictService;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 数据字典信息
 *
 * @author Bruce Lee ( copy )
 */
@RestController
@AllArgsConstructor
@RequestMapping("/system/dictData")
public class DictDataController {

    private final DictDataService dictDataService;
    private final DictService dictService;

    private QueryWrapper<DictData> getQueryWrapper(DictData dictData) {
        return new QueryWrapper<DictData>().like(StrUtil.isNotBlank(dictData.getLabel()), "label", dictData.getLabel())
                .eq(StrUtil.isNotBlank(dictData.getDictType()), "dict_type", dictData.getDictType()).orderByDesc("id");
    }

    @PreAuthorize("@ps.hasPerm('dictData_view')")
    @GetMapping("/list")
    @ResponseBody
    public R list(Page page, DictData dictData) {
        IPage<DictData> dictPage = dictDataService.page(page, getQueryWrapper(dictData));
        return R.success(dictPage.getRecords(), dictPage.getTotal());
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(dictDataService.getById(id));
    }

    @GetMapping("/dictType/{dictType}")
    @ResponseBody
    public R dictType(@PathVariable String dictType) {
        return R.success(dictDataService.getDictDataList(dictType));
    }

    @OperLog("字典数据新增")
    @PreAuthorize("@ps.hasPerm('dictData_add')")
    @PostMapping("/save")
    @ResponseBody
    public R save(@RequestBody DictData dictData) {
        dictDataService.save(dictData);
        return R.success();
    }

    @OperLog("字典数据修改")
    @PreAuthorize("@ps.hasPerm('dictData_edit')")
    @PutMapping("/update")
    public R update(@RequestBody DictData dictData) {
        dictDataService.updateById(dictData);
        return R.success();
    }

    @OperLog("字典数据删除")
    @PreAuthorize("@ps.hasPerm('dictData_del')")
    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public R remove(@PathVariable Integer[] id) {
        dictDataService.removeByIds(Arrays.asList(id));
        return R.success();
    }

    @GetMapping("/getDictData")
    @ResponseBody
    public R getDictData(String dictType, String value) {
        DictData dictData = dictDataService.getOne(new QueryWrapper<DictData>().eq("dict_type", dictType).eq("value", value));
        if(dictData != null){
            return R.success(dictData.getLabel());
        }else {
            return R.error("数据不存在");
        }
    }
}
