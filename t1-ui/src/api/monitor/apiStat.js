import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询api统计详细
export function getApiStat() {
  return request({
    url: Api.API_SYSTEM + '/monitor/apiStat',
    method: 'get'
  })
}
