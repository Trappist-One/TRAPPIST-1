package com.t1.biz.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.t1.biz.system.entity.RoleMenu;
import com.t1.common.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<Menu> findMenusByRoleIds(@Param("roleIds") Set<Integer> roleIds);

}
