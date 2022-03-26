package com.t1.sys.toolkit.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.t1.sys.toolkit.entity.Form;

/**
 * @author Bruce Lee ( copy )
 * @date 2021-03-11 21:57:03
 * @description 表单管理Service接口
 */
public interface FormService extends IService<Form> {
    Page<Form> customFormPage(Page<Form> page, Form form);
}
