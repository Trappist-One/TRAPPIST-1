import request from '@/utils/request'
import Api from "@/consts/apiConst";

// 查询消息中心列表
export function listSmsTemplate(query) {
  return request({
    url: Api.API_SYSTEM + '/system/smsTemplate/list',
    method: 'get',
    params: query
  })
}

// 查询消息中心详细
export function getSmsTemplate(id) {
  return request({
    url: Api.API_SYSTEM + '/system/smsTemplate/' + id,
    method: 'get'
  })
}

// 新增消息中心
export function addSmsTemplate(data) {
  return request({
    url: Api.API_SYSTEM + '/system/smsTemplate/save',
    method: 'post',
    data: data
  })
}

// 修改消息中心
export function editSmsTemplate(data) {
  return request({
    url: Api.API_SYSTEM + '/system/smsTemplate/update',
    method: 'put',
    data: data
  })
}

// 删除消息中心
export function delSmsTemplate(id) {
  return request({
    url: Api.API_SYSTEM + '/system/smsTemplate/remove/' + id,
    method: 'delete'
  })
}



// 导出消息中心
export function exportSmsTemplate(query) {
  return request({
    url: Api.API_SYSTEM + '/system/smsTemplate/export',
    method: 'get',
    params: query
  })
}
