import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询调度日志列表
export function listJobLog(query) {
  return request({
    url: Api.API_SYSTEM + '/quartz/jobLog/list',
    method: 'get',
    params: query
  })
}

// 删除调度日志
export function delJobLog(id) {
  return request({
    url: Api.API_SYSTEM + '/quartz/jobLog/' + id,
    method: 'delete'
  })
}
