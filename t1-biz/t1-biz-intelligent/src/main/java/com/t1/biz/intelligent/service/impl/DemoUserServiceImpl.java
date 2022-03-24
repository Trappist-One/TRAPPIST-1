package com.t1.biz.intelligent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.biz.intelligent.entity.DemoUser;
import com.t1.biz.intelligent.mapper.DemoUserMapper;
import com.t1.biz.intelligent.service.DemoUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户例子
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/3/26
 */
@Service
@AllArgsConstructor
public class DemoUserServiceImpl extends ServiceImpl<DemoUserMapper, DemoUser> implements DemoUserService {
}
