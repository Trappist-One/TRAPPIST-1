import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询字典类型列表
export function listDict(query) {
  return request({
    url: Api.API_SYSTEM + '/system/dict/list',
    method: 'get',
    params: query
  })
}

// 查询字典类型详细
export function getDict(dictId) {
  return request({
    url: Api.API_SYSTEM + '/system/dict/' + dictId,
    method: 'get'
  })
}

// 新增字典类型
export function addDict(data) {
  return request({
    url: Api.API_SYSTEM + '/system/dict/save',
    method: 'post',
    data: data
  })
}

// 修改字典类型
export function editDict(data) {
  return request({
    url: Api.API_SYSTEM + '/system/dict/update',
    method: 'put',
    data: data
  })
}

// 删除字典类型
export function delDict(dictId) {
  return request({
    url: Api.API_SYSTEM + '/system/dict/remove/' + dictId,
    method: 'delete'
  })
}

// 导出字典类型
export function exportDict(query) {
  return request({
    url: Api.API_SYSTEM + '/system/dict/export',
    method: 'get',
    params: query
  })
}

// 获取所有字典列表
export function dictList() {
  return request({
    url: Api.API_SYSTEM + '/system/dict/dictList',
    method: 'get'
  })
}
