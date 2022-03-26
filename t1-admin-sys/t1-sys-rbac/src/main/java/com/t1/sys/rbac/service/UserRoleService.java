package com.t1.sys.rbac.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t1.sys.rbac.entity.UserRole;
import com.t1.common.entity.Role;

import java.util.List;

/**
 * <p>
 * 用户和角色关联表 服务类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
public interface UserRoleService extends IService<UserRole> {

    /**
     * 根据用户id获取角色
     *
     * @param userId
     * @return
     */
    List<Role> findRolesByUserId(Integer userId);

    /**
     * 根据用户ids 获取
     *
     * @param userIds
     * @return
     */
    List<Role> findRolesByUserIds(List<Long> userIds);
}
