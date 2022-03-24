import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询参数列表
export function listConfig(query) {
  return request({
    url: Api.API_SYSTEM + '/system/config/list',
    method: 'get',
    params: query
  })
}

// 查询参数详细
export function getConfig(configId) {
  return request({
    url: Api.API_SYSTEM + '/system/config/' + configId,
    method: 'get'
  })
}

// 根据参数键名查询参数值
export function getByKey(configKey) {
  return request({
    url: Api.API_SYSTEM + '/system/config/getByKey/' + configKey,
    method: 'get'
  })
}

// 新增参数配置
export function addConfig(data) {
  return request({
    url: Api.API_SYSTEM + '/system/config/save',
    method: 'post',
    data: data
  })
}

// 修改参数配置
export function editConfig(data) {
  return request({
    url: Api.API_SYSTEM + '/system/config/update',
    method: 'put',
    data: data
  })
}

// 删除参数配置
export function delConfig(configId) {
  return request({
    url: Api.API_SYSTEM + '/system/config/remove/' + configId,
    method: 'delete'
  })
}

