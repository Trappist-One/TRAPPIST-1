import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询快捷方式列表
export function listShortcut(query) {
  return request({
    url: Api.API_SYSTEM + '/system/shortcut/list',
    method: 'get',
    params: query
  })
}

// 查询快捷方式列表
export function shortcutList(query) {
  return request({
    url: Api.API_SYSTEM + '/system/shortcut/shortcutList',
    method: 'get',
    params: query
  })
}

// 查询快捷方式详细
export function getShortcut(shortcutId) {
  return request({
    url: Api.API_SYSTEM + '/system/shortcut/' + shortcutId,
    method: 'get'
  })
}

// 新增快捷方式配置
export function addShortcut(data) {
  return request({
    url: Api.API_SYSTEM + '/system/shortcut/save',
    method: 'post',
    data: data
  })
}

// 修改快捷方式配置
export function editShortcut(data) {
  return request({
    url: Api.API_SYSTEM + '/system/shortcut/update',
    method: 'put',
    data: data
  })
}

// 删除快捷方式配置
export function delShortcut(shortcutId) {
  return request({
    url: Api.API_SYSTEM + '/system/shortcut/remove/' + shortcutId,
    method: 'delete'
  })
}

