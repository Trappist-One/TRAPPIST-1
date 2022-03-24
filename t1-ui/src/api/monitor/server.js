import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询服务器详细
export function getServer() {
  return request({
    url: Api.API_SYSTEM + '/monitor/server',
    method: 'get'
  })
}
