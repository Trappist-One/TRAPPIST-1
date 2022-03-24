package com.t1.biz.activiti.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.t1.biz.activiti.dto.ModelDto;
import com.t1.biz.activiti.service.ModelService;
import com.t1.common.model.R;
import com.t1.log.annotation.OperLog;
import lombok.AllArgsConstructor;
import org.activiti.engine.repository.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/4/4
 * @description 模型 controller
 */
@RestController
@RequestMapping("/activiti/model")
@AllArgsConstructor
public class ModelController {
    private final ModelService modelService;

    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        IPage<Model> modelPage = modelService.list(params);
        return R.success(modelPage.getRecords(), modelPage.getTotal());
    }

     @OperLog("模型新增")
    @PostMapping("/save")
    public R save(@RequestBody ModelDto form) {
        modelService.save(form.getName(), form.getCategory(), form.getKey(), form.getDescription());
        return R.success();
    }

     @OperLog("模型删除")
    @DeleteMapping("/remove/{id}")
    public R remove(@PathVariable("id") String id) {
        return R.success(modelService.removeById(id));

    }

     @OperLog("模型部署")
    @PostMapping("/deploy/{id}")
    public R deploy(@PathVariable("id") String id) {
        return R.success(modelService.deploy(id));
    }
}
