/**
 * @program: T-1
 *
 * @description: 用户Api
 *
 * @author Bruce Lee ( copy )
 *
 * @create: 2021-03-26
 **/
import request from '@/utils/request'
import {version} from "@/utils/formExpandDesign/formIntegrated";
import Api from '@/consts/apiConst'


// 查询用户列表
export function listDemoUser(query) {
  return request({
    url: Api.API_SYSTEM + '/intelligent/DemoUser/list',
    method: 'get',
    params: query
  })
}


// 查询用户详细
export function getDemoUser(id, formCode, versionIndex = 0) {
  return request({
    url: Api.API_SYSTEM + `/intelligent/DemoUser/getById/${id}/${formCode}/${version[versionIndex]}`,
    method: 'get'
  })
}


// 新增用户
export function addDemoUser(data) {
  return request({
    url: Api.API_SYSTEM + '/intelligent/DemoUser/save',
    method: 'post',
    data: data
  })
}


// 修改用户
export function editDemoUser(data) {
  return request({
    url: Api.API_SYSTEM + '/intelligent/DemoUser/update',
    method: 'put',
    data: data
  })
}


// 删除用户
export function delDemoUser(ids, formCode, versionIndex = 0) {
  return request({
    url: Api.API_SYSTEM + `/intelligent/DemoUser/remove/${ids}/${formCode}/${version[versionIndex]}`,
    method: 'delete'
  })
}
