import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 查询令牌
export function list(query) {
  return request({
    url: Api.API_AUTH + '/token/list',
    method: 'get',
    params: query
  })
}

// 删除令牌
export function delToken(token) {
  return request({
    url: Api.API_AUTH + '/token/' + token,
    method: 'delete'
  })
}
