import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询应用列表
export function listApplication(query) {
  return request({
    url: Api.API_SYSTEM + '/system/application/list',
    method: 'get',
    params: query
  })
}

// 查询应用树
export function treeApplication(query) {
  return request({
    url: Api.API_SYSTEM + '/system/application/tree',
    method: 'get',
    params: query
  })
}

// 查询应用详细
export function getApplication(id) {
  return request({
    url: Api.API_SYSTEM + '/system/application/' + id,
    method: 'get'
  })
}

// 新增应用
export function addApplication(data) {
  return request({
    url: Api.API_SYSTEM + '/system/application/save',
    method: 'post',
    data: data
  })
}

// 修改应用
export function editApplication(data) {
  return request({
    url: Api.API_SYSTEM + '/system/application/update',
    method: 'put',
    data: data
  })
}

// 删除应用
export function delApplication(id) {
  return request({
    url: Api.API_SYSTEM + '/system/application/remove/' + id,
    method: 'delete'
  })
}



// 导出应用
export function exportApplication(query) {
  return request({
    url: Api.API_SYSTEM + '/system/application/export',
    method: 'get',
    params: query
  })
}
