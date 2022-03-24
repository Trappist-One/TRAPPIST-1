import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询菜单列表
export function listMenu(query) {
  return request({
    url: Api.API_SYSTEM + '/system/menu/list',
    method: 'get',
    params: query
  })
}

// 查询菜单详细
export function getMenu(menuId) {
  return request({
    url: Api.API_SYSTEM + '/system/menu/' + menuId,
    method: 'get'
  })
}

// 查询菜单下拉树结构
export function menuTree(query) {
  return request({
    url: Api.API_SYSTEM + '/system/menu/menuTree',
    method: 'get',
    params: query
  })
}

// 根据角色ID查询菜单下拉树结构
export function roleMenuTree(roleId) {
  return request({
    url: Api.API_SYSTEM + '/system/menu/roleMenuTree/' + roleId,
    method: 'get'
  })
}

// 新增菜单
export function addMenu(data) {
  return request({
    url: Api.API_SYSTEM + '/system/menu/save',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function editMenu(data) {
  return request({
    url: Api.API_SYSTEM + '/system/menu/update',
    method: 'put',
    data: data
  })
}

// 删除菜单
export function delMenu(menuId) {
  return request({
    url: Api.API_SYSTEM + '/system/menu/remove/' + menuId,
    method: 'delete'
  })
}
