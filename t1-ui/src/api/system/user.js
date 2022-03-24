import request from '@/utils/request'
import Api from '@/consts/apiConst'
import { praseStrEmpty } from "@/utils/hk";

// 查询用户列表
export function listUser(query) {
  return request({
    url: Api.API_SYSTEM + '/system/user/list',
    method: 'get',
    params: query
  })
}

// 查询用户列表(不分页)
export function listAllUser(query) {
  return request({
    url: Api.API_SYSTEM + '/system/user/userList',
    method: 'get',
    params: query
  })
}


// 查询用户详细
export function getUser(userId) {
  return request({
    url: Api.API_SYSTEM + '/system/user/' + praseStrEmpty(userId),
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: Api.API_SYSTEM + '/system/user/save',
    method: 'post',
    data: data
  })
}

// 修改用户
export function editUser(data) {
  return request({
    url: Api.API_SYSTEM + '/system/user/update',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: Api.API_SYSTEM + '/system/user/remove/' + userId,
    method: 'delete'
  })
}

// 导出用户
export function exportUser(query) {
  return request({
    url: Api.API_SYSTEM + '/system/user/exportUser',
    method: 'get',
    params: query
  })
}

// 用户密码重置
export function resetPwd(id, password) {
  const data = {
    id,
    password
  }
  return request({
    url: Api.API_SYSTEM + '/system/user/resetPwd',
    method: 'put',
    data: data
  })
}

// 用户状态修改
export function changeStatus(id, status) {
  const data = {
    id,
    status
  }
  return request({
    url: Api.API_SYSTEM + '/system/user/changeStatus',
    method: 'put',
    data: data
  })
}

// 查询用户个人信息
export function getProfile() {
  return request({
    url: Api.API_SYSTEM + '/system/user/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateProfile(data) {
  return request({
    url: Api.API_SYSTEM + '/system/user/updateProfile',
    method: 'put',
    data: data
  })
}

// 用户密码重置
export function updatePwd(password, newPassword) {
  const data = {
    password,
    newPassword
  }
  return request({
    url: Api.API_SYSTEM + '/system/user/updatePwd',
    method: 'put',
    params: data
  })
}

// 用户头像上传
export function uploadAvatar(data) {
  return request({
    url: Api.API_SYSTEM + '/system/user/updateAvatar',
    method: 'put',
    data: data
  })
}

// 下载用户导入模板
export function importTemplate() {
  return request({
    url: Api.API_SYSTEM + '/system/user/importTemplate',
    method: 'get'
  })
}
