import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询流程列表
export function listProcess(query) {
  return request({
    url: Api.API_SYSTEM + '/activiti/process/list',
    method: 'get',
    params: query
  })
}

// 查询流程详细
export function getProcess(processId) {
  return request({
    url: Api.API_SYSTEM + '/activiti/process/' + processId,
    method: 'get'
  })
}


// 改变流程配置
export function changeStatus(procDefId, status) {
  const data = {
    procDefId,
    status
  }
  return request({
    url: Api.API_SYSTEM + '/activiti/process/changeStatus',
    method: 'put',
    params: data
  })
}

// 删除流程配置
export function delProcess(id) {
  return request({
    url: Api.API_SYSTEM + '/activiti/process/remove/' + id,
    method: 'delete'
  })
}


