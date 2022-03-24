import request from '@/utils/request'
import Api from '@/consts/apiConst'

// 新增表单扩展
export function addFormExtendDesign(data) {
  return request({
    url: Api.API_SYSTEM + '/intelligent/FormExtendDesign/save',
    method: 'post',
    data: data
  })
}


// 新增表单扩展
export function editFormExtendDesign(data) {
  return request({
    url: Api.API_SYSTEM + '/intelligent/FormExtendDesign/update',
    method: 'put',
    data: data
  })
}

//数据映射表校验是否重复
export function tableValidator(table) {
  return request({
    url: Api.API_SYSTEM + '/intelligent/FormExtendDesign/tableValidator/' + table,
    method: 'get'
  })
}
