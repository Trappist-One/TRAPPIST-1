package com.t1.biz.toolkit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t1.biz.toolkit.entity.Form;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Bruce Lee ( copy )
 * @date 2021-03-11 21:57:03
 * @description 表单管理Mapper接口
 */
public interface FormMapper extends BaseMapper<Form> {
    List<Form> selectCustomQuery(Page<Form> page, @Param("query") Form form);
}
