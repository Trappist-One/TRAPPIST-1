import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询数据库列表
export function listDatasource(query) {
  return request({
    url: Api.API_SYSTEM + '/system/datasource/list',
    method: 'get',
    params: query
  })
}

// 查询数据库详细
export function getDatasource(datasourceId) {
  return request({
    url: Api.API_SYSTEM + '/system/datasource/' + datasourceId,
    method: 'get'
  })
}

// 新增数据库配置
export function addDatasource(data) {
  return request({
    url: Api.API_SYSTEM + '/system/datasource/save',
    method: 'post',
    data: data
  })
}

// 修改数据库配置
export function editDatasource(data) {
  return request({
    url: Api.API_SYSTEM + '/system/datasource/update',
    method: 'put',
    data: data
  })
}

// 删除数据库配置
export function delDatasource(datasourceId) {
  return request({
    url: Api.API_SYSTEM + '/system/datasource/remove/' + datasourceId,
    method: 'delete'
  })
}

// 查询数据库列表结构
export function datasourceList() {
  return request({
    url: Api.API_SYSTEM + '/system/datasource/datasourceList',
    method: 'get'
  })
}
