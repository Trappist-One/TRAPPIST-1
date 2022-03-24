import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询任务列表
export function listTask(query) {
  return request({
    url: Api.API_SYSTEM + '/activiti/task/list',
    method: 'get',
    params: query
  })
}


// 查询历史任务列表
export function listHistoryTask(query) {
  return request({
    url: Api.API_SYSTEM + '/activiti/task/historyList',
    method: 'get',
    params: query
  })
}


// 查询任务详细
export function getTask(taskId) {
  return request({
    url: Api.API_SYSTEM + '/activiti/task/' + taskId,
    method: 'get'
  })
}


// 查询历史任务详细
export function getHistoryTask(taskId) {
  return request({
    url: Api.API_SYSTEM + '/activiti/task/history/' + taskId,
    method: 'get'
  })
}


// 审批
export function checkTask(data) {
  return request({
    url: Api.API_SYSTEM + '/activiti/task/checkTask',
    method: 'post',
    data: data
  })
}

// 查询审批意见列表
export function taskComment(taskId) {
  return request({
    url: Api.API_SYSTEM + '/activiti/task/commitList/' + taskId,
    method: 'get'
  })
}


