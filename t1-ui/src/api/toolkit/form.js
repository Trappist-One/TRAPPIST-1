import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询表单管理列表
export function listForm(query) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/form/list',
    method: 'get',
    params: query
  })
}

// 查询表单管理详细
export function getForm(id) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/form/' + id,
    method: 'get'
  })
}

// 新增表单管理
export function addForm(data) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/form/save',
    method: 'post',
    data: data
  })
}

// 修改表单管理
export function editForm(data) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/form/update',
    method: 'put',
    data: data
  })
}

// 删除表单管理
export function delForm(id) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/form/remove/' + id,
    method: 'delete'
  })
}



// 导出表单管理
export function exportForm(query) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/form/export',
    method: 'get',
    params: query
  })
}
