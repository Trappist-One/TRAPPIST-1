package com.t1.sys.intelligent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.intelligent.entity.FormExtend;
import com.t1.sys.intelligent.mapper.FormExtendMapper;
import com.t1.sys.intelligent.service.FormExtendService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )开发团队-王翔
 * @create 2020-12-24
 */
@Service
@AllArgsConstructor
public class FormExtendServiceImpl extends ServiceImpl<FormExtendMapper, FormExtend> implements FormExtendService {

    @Override
    public FormExtend selectSingle(Integer id) {
        return baseMapper.selectSingle(id);
    }

    @Override
    public String fetchFormExtend(String code) {
        return baseMapper.findByCodeOne(code);
    }
}
