package com.t1.sys.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t1.common.entity.Menu;
import com.t1.common.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRoleKeys(Integer userId);
    /**
     * 根据用户ID查询我的角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<Role> selectMyRolesByUserId(Integer userId);

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<Role> selectRolesByUserId(Integer userId);

    boolean insertRole(Role role);

    /**
     * 修改数据权限信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public boolean updatePerms(Role role);

    /**
     * 修改角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    public boolean updateRoleMenu(Role role);

    List<Menu> findMenusByRoleIds(@Param("roleIds") Set<Integer> roleIds);

}
