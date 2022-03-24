import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询字典数据列表
export function listData(query) {
  return request({
    url: Api.API_SYSTEM + '/system/dictData/list',
    method: 'get',
    params: query
  })
}

// 查询字典数据详细
export function getData(dictCode) {
  return request({
    url: Api.API_SYSTEM + '/system/dictData/' + dictCode,
    method: 'get'
  })
}

// 根据字典类型查询字典数据信息
export function getDicts(dictType) {
  return request({
    url: Api.API_SYSTEM + '/system/dictData/dictType/' + dictType,
    method: 'get'
  })
}

// 新增字典数据
export function addData(data) {
  return request({
    url: Api.API_SYSTEM + '/system/dictData/save',
    method: 'post',
    data: data
  })
}

// 修改字典数据
export function editData(data) {
  return request({
    url: Api.API_SYSTEM + '/system/dictData/update',
    method: 'put',
    data: data
  })
}

// 删除字典数据
export function delData(dictCode) {
  return request({
    url: Api.API_SYSTEM + '/system/dictData/remove/' + dictCode,
    method: 'delete'
  })
}

// 导出字典数据
export function exportData(query) {
  return request({
    url: Api.API_SYSTEM + '/system/dictData/export',
    method: 'get',
    params: query
  })
}
