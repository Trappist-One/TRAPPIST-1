package com.t1.sys.activiti.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t1.sys.activiti.entity.FormActivitiEngine;

/**
 * <p>
 * 工作流动态表单Mapper
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/19
 */
public interface FormActivitiEngineMapper extends BaseMapper<FormActivitiEngine> {

    FormActivitiEngine findFormActivitiEngineDesignerData(Long id);

    /**
     * 获取表单扩展模板
     *@Param [cod]
     *@return java.lang.String
     */
    String findByCodeOne(String cod);

}
