package com.t1.sys.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t1.common.entity.Role;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<Role> selectRolesByUserId(Integer userId);

}
