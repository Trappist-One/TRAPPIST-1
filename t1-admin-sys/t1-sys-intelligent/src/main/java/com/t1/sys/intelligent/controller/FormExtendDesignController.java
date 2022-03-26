package com.t1.sys.intelligent.controller;

import com.t1.common.annotation.LoginUser;
import com.t1.common.entity.User;
import com.t1.common.model.R;
import com.t1.sys.intelligent.dto.FormExtendDesignDto;
import com.t1.sys.intelligent.service.FormExtendDesignService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 表单扩展设计器控制
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/1/21
 */
@RestController
@AllArgsConstructor
@RequestMapping("/intelligent/FormExtendDesign")
public class FormExtendDesignController {

    private final FormExtendDesignService formExtendDesignService;

    @PostMapping("/save")
    public R save(@LoginUser User user, @RequestBody FormExtendDesignDto formExtendDesignDto) {
        formExtendDesignService.insert(formExtendDesignDto, user.getUserName());
        return R.success();
    }


    @PutMapping("/update")
    public R update(@LoginUser User user, @RequestBody FormExtendDesignDto formExtendDesignDto) {
        formExtendDesignService.update(formExtendDesignDto, user.getUserName());
        return R.success();
    }


    @GetMapping("/tableValidator/{table}")
    public R validatorBusinessTable(@PathVariable String table) {
        return R.success(formExtendDesignService.validatorBusinessTable(table));
    }

}
