package com.t1.sys.activiti.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t1.sys.activiti.dto.FormActivitiEngineDesignDto;
import com.t1.sys.activiti.entity.FormActivitiEngine;

/**
 * <p>
 * 工作流动态表单业务接口
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/19
 */
public interface FormActivitiEngineService extends IService<FormActivitiEngine> {


    Boolean formActivitiEngineDesignUpdate(FormActivitiEngineDesignDto formActivitiEngineDesignDto, String username);


    FormActivitiEngine formActivitiEngineDesignSelect(Long id);

    String fetchFormActivitiEngine(String code);

}
