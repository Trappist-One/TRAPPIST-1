package com.t1.sys.activiti.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.sys.activiti.dto.FormActivitiEngineDesignDto;
import com.t1.sys.activiti.entity.FormActivitiEngine;
import com.t1.sys.activiti.service.FormActivitiEngineService;
import com.t1.common.annotation.LoginUser;
import com.t1.common.entity.User;
import com.t1.common.model.R;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;

/**
 * <p>
 * 工作流动态表单控制层
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/19
 */
@RestController
@RequestMapping("/activiti/FormActivitiEngine")
@AllArgsConstructor
public class FormActivitiEngineController {


    private final FormActivitiEngineService formActivitiEngineService;


    private QueryWrapper<FormActivitiEngine> getQueryWrapper(FormActivitiEngine formActivitiEngine) {
        return new QueryWrapper<FormActivitiEngine>()
                .like(StrUtil.isNotBlank(formActivitiEngine.getCode()),"code", formActivitiEngine.getCode())
                .like(StrUtil.isNotBlank(formActivitiEngine.getName()), "name", formActivitiEngine.getName())
                .between(StrUtil.isNotBlank(formActivitiEngine.getBeginTime()) && StrUtil.isNotBlank(formActivitiEngine.getEndTime()), "create_time", formActivitiEngine.getBeginTime(), formActivitiEngine.getEndTime());
    }


    @GetMapping("/list")
    public R list(Page page, FormActivitiEngine formExtend) {
        IPage<FormActivitiEngine> roleIPage = formActivitiEngineService.page(page, getQueryWrapper(formExtend));
        return R.success(roleIPage.getRecords(), roleIPage.getTotal());
    }


    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(formActivitiEngineService.getById(id));
    }


    @GetMapping("fetchFormActivitiEngine/{code}")
    public R fetchFormActivitiEngine(@PathVariable("code") String code) {
        return R.success(formActivitiEngineService.fetchFormActivitiEngine(code));
    }


    @PostMapping("/save")
    public R save(@LoginUser User user, @RequestBody FormActivitiEngine formActivitiEngine) {
        if(formActivitiEngineService.list(new QueryWrapper<FormActivitiEngine>()
                .eq(StrUtil.isNotBlank(formActivitiEngine.getCode()),"code", formActivitiEngine.getCode())).size()==0){
            formActivitiEngine.setCreateBy(user.getUserName());
            formActivitiEngine.setCreateTime(new Date());
            formActivitiEngineService.save(formActivitiEngine);
        }else{
            return R.error("编码重复!");
        }
        return R.success();
    }


    @PutMapping("/update")
    public R update(@LoginUser User user,@RequestBody FormActivitiEngine formActivitiEngine) {
        formActivitiEngine.setUpdateBy(user.getUserName());
        formActivitiEngine.setUpdateTime(new Date());
        formActivitiEngineService.update(formActivitiEngine,new UpdateWrapper<FormActivitiEngine>().eq("id", formActivitiEngine.getId()));
        return R.success();
    }


    @DeleteMapping("/remove/{ids}")
    public R remove(@PathVariable Integer[] ids) {
        formActivitiEngineService.removeByIds(Arrays.asList(ids));
        return R.success();
    }


    @PutMapping("design/update")
    public R save(@LoginUser User user, @RequestBody FormActivitiEngineDesignDto formActivitiEngineDesignDto) {
        formActivitiEngineService.formActivitiEngineDesignUpdate(formActivitiEngineDesignDto, user.getUserName());
        return R.success();
    }


    @GetMapping("design/select/{id}")
    public R select(@PathVariable Long id) {
        return R.success(formActivitiEngineService.formActivitiEngineDesignSelect(id));
    }


}
