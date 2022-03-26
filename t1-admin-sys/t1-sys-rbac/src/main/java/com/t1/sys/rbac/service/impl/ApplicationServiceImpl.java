package com.t1.sys.rbac.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.rbac.service.ApplicationService;
import com.t1.sys.rbac.entity.Application;
import com.t1.sys.rbac.mapper.ApplicationMapper;
import org.springframework.stereotype.Service;

/**
 * @author Bruce Lee ( copy )
 * @date 2020-04-23 18:15:10
 * @description 应用Service业务层
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {
}
