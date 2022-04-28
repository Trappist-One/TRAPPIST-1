package com.t1.sys.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t1.sys.base.service.UserRoleService;
import com.t1.sys.base.entity.UserRole;
import com.t1.sys.base.mapper.UserRoleMapper;
import com.t1.common.entity.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Resource
    private UserRoleMapper sysUserRoleMapper;
    @Override
    public List<Role> findRolesByUserId(Integer userId) {
        return sysUserRoleMapper.findRolesByUserId(userId);
    }

    @Override
    public List<Role> findRolesByUserIds(List<Long> userIds) {
        return sysUserRoleMapper.findRolesByUserIds(userIds);
    }
}
