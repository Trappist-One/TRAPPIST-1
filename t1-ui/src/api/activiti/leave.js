import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询请假列表
export function listLeave(query) {
  return request({
    url: Api.API_SYSTEM + '/activiti/leave/list',
    method: 'get',
    params: query
  })
}

// 查询请假详细
export function getLeave(leaveId) {
  return request({
    url: Api.API_SYSTEM + '/activiti/leave/' + leaveId,
    method: 'get'
  })
}

// 新增请假配置
export function addLeave(data) {
  return request({
    url: Api.API_SYSTEM + '/activiti/leave/save',
    method: 'post',
    data: data
  })
}

// 新增请假配置
export function editLeave(data) {
  return request({
    url: Api.API_SYSTEM + '/activiti/leave/update',
    method: 'put',
    data: data
  })
}
// 删除请假配置
export function delLeave(id) {
  return request({
    url: Api.API_SYSTEM + '/activiti/leave/remove/' + id,
    method: 'delete'
  })
}

// 启动请假流程
export function startProcess(id) {
  return request({
    url: Api.API_SYSTEM + '/activiti/leave/startProcess/' + id,
    method: 'get'
  })
}

// 导出请假数据
export function exportLeave(query) {
  return request({
    url: Api.API_SYSTEM + '/activiti/leave/export',
    method: 'get',
    params: query
  })
}

