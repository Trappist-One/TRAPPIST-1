package com.t1.biz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.biz.system.entity.LoginLog;
import com.t1.biz.system.mapper.LoginLogMapper;
import com.t1.biz.system.service.LoginLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

}
