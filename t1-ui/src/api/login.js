import request from '@/utils/request'
import {getUUID} from '@/utils/math'
import Api from '@/consts/apiConst'
import App from '@/consts/appConst'

// 登录方法
export function login(username, password, validCode, deviceId) {
  const grant_type = 'password_code'
  const scope = App.SCOPE
  return request({
    url: Api.API_AUTH + '/oauth/token',
    headers: {
      isToken:false,
      'Authorization': 'Basic ' + window.btoa(App.CLIENT_ID + ":" + App.CLIENT_SECRET)
    },
    method: 'post',
    params: { username, password, grant_type, scope, validCode, deviceId }
  })
}

// 获取用户详细信息
export function getInfo() {
  return request({
    url: Api.API_SYSTEM + '/system/user/info',
    method: 'get'
  })
}

// 退出方法
export function logout() {
  return request({
    url: Api.API_AUTH + '/token/logout',
    method: 'delete'
  })
}

// 获取验证码
export function getCodeImg() {
  return request({
    url: Api.API_AUTH + '/validata/code/'+ getUUID(),
    method: 'get'
  })
}
