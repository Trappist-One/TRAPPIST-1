import request from '@/utils/request'
import Api from "@/consts/apiConst";

// 查询消息中心列表
export function listSms(query) {
  return request({
    url: Api.API_SYSTEM + '/system/sms/list',
    method: 'get',
    params: query
  })
}

// 查询消息中心详细
export function getSms(id) {
  return request({
    url: Api.API_SYSTEM + '/system/sms/' + id,
    method: 'get'
  })
}

// 新增消息中心
export function addSms(data) {
  return request({
    url: Api.API_SYSTEM + '/system/sms/save',
    method: 'post',
    data: data
  })
}

// 修改消息中心
export function editSms(data) {
  return request({
    url: Api.API_SYSTEM + '/system/sms/update',
    method: 'put',
    data: data
  })
}

// 删除消息中心
export function delSms(id) {
  return request({
    url: Api.API_SYSTEM + '/system/sms/remove/' + id,
    method: 'delete'
  })
}



// 导出消息中心
export function exportSms(query) {
  return request({
    url: Api.API_SYSTEM + '/system/sms/export',
    method: 'get',
    params: query
  })
}
