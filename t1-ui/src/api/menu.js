import request from '@/utils/request'
import Api from '@/consts/apiConst'


// 获取路由
export const getMenus = () => {
  return request({
    url: Api.API_SYSTEM + '/system/menu',
    method: 'get'
  })
}
