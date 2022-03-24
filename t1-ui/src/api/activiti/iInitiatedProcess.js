/**
 * @program: T-1
 *
 * @description: 我发起的流程API
 *
 * @author Bruce Lee ( copy )
 *
 * @create: 2021-04-14
 **/

import request from '@/utils/request'
import Api from '@/consts/apiConst'


// 查询我发起流程列表
export function listIInitiatedProcess(query) {
  return request({
    url: Api.API_SYSTEM + '/activiti/IInitiatedProcess/list',
    method: 'get',
    params: query
  })
}

// 流程作废
export function abolishProcess(params) {
  return request({
    url: Api.API_SYSTEM + '/activiti/IInitiatedProcess/abolishProcess/',
    method: 'get',
    params: params
  })
}

// 查询流程历史跟踪列表
export function listDetailedProcess(query) {
  return request({
    url: Api.API_SYSTEM + '/activiti/IInitiatedProcess/detailedProcess',
    method: 'get',
    params: query
  })
}


