package com.t1.biz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.biz.system.entity.Application;
import com.t1.biz.system.mapper.ApplicationMapper;
import com.t1.biz.system.service.ApplicationService;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )
 * @date 2020-04-23 18:15:10
 * @description 应用Service业务层
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {
}
