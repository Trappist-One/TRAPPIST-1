package com.t1.sys.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t1.common.entity.Menu;
import com.t1.sys.base.vo.MenuVo;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @since 2019-01-30
 */
public interface MenuService extends IService<Menu> {

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<Menu> selectMenuListByRoleId(Integer roleId);

    /**
     * 通过角色编号查询菜单
     *
     * @param roleId 角色ID
     * @return 菜单编号
     */
    List<Integer> selectMenusByRoleId(Integer roleId);

    /**
     * 构建树
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<Menu> buildTree(List<Menu> list, int parentId);

    /**
     * 菜单转换为路由
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<MenuVo> buildMenus(List<Menu> menus);

}
