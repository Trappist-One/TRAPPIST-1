import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询redis详细
export function getRedis() {
  return request({
    url: Api.API_SYSTEM + '/monitor/redis',
    method: 'get'
  })
}
