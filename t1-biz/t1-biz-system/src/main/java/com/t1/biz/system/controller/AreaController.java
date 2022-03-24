package com.t1.biz.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.common.model.R;
import com.t1.biz.system.entity.Area;
import com.t1.biz.system.service.AreaService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @author Bruce Lee ( copy )
 * @date 2019-10-11 09:13:04
 * @description 区域Controller
 */
@RestController
@AllArgsConstructor
@RequestMapping("/system/area")
public class AreaController {

    private final AreaService areaService;

    private QueryWrapper<Area> getQueryWrapper(Area area) {
        return new QueryWrapper<Area>().orderByDesc("create_time");
    }

    @PreAuthorize("@ps.hasPerm('area_view')")
    @GetMapping("/list")
    @ResponseBody
    public R list(Page page, Area area) {
        IPage<Area> areaPage = areaService.page(page, getQueryWrapper(area));
        return R.success(areaPage.getRecords(), areaPage.getTotal());
    }

    @GetMapping("/{id}")
    public R getById(@PathVariable("id") Integer id) {
        return R.success(areaService.getById(id));
    }

    @PreAuthorize("@ps.hasPerm('area_add')")
    @PostMapping("/save")
    @ResponseBody
    public R save(@RequestBody Area area) {
        if(area.getId() == null){
            area.setParentId(0);
            area.setAncestors("0");
            area.setType("0");
        }
        areaService.save(area);
        return R.success();
    }

    @PreAuthorize("@ps.hasPerm('area_edit')")
    @PutMapping("/update")
    @ResponseBody
    public R update(@RequestBody Area area) {
        areaService.updateById(area);
        return R.success();
    }

    @PreAuthorize("@ps.hasPerm('area_del')")
    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public R remove(@PathVariable("id") Integer id) {
        return R.success(areaService.removeById(id));
    }
}
