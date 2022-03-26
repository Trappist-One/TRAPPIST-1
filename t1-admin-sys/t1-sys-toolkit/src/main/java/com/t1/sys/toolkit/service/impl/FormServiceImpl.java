package com.t1.sys.toolkit.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.toolkit.entity.Form;
import com.t1.sys.toolkit.mapper.FormMapper;
import com.t1.sys.toolkit.service.FormService;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )
 * @date 2021-03-11 21:57:03
 * @description 表单管理Service业务层
 */
@Service
public class FormServiceImpl extends ServiceImpl<FormMapper, Form> implements FormService {
    @Override
    public Page<Form> customFormPage(Page<Form> page, Form form) {
        return page.setRecords(baseMapper.selectCustomQuery(page, form));
    }
}
