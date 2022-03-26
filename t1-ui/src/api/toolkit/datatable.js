import request from '@/utils/request'
import { praseStrEmpty } from "@/utils/t1";
import Api from '@/consts/apiConst'

// 查询数据表列表
export function listDatatable(query) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/list',
    method: 'get',
    params: query
  })
}


export function treeDatatable(query) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/tree',
    method: 'get',
    params: query
  })
}

// 查看数据表详细
export function getDatatable(datatableId) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/' + praseStrEmpty(datatableId),
    method: 'get'
  })
}

// 查询表详细信息
export function getGenTable(tableName, tableComment) {
  const data = {
    tableName,
    tableComment
  }
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/getGenTable',
    method: 'post',
    data: data
  })
}

// 修改代码生成信息
export function updateGenTable(data) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/update',
    method: 'put',
    data: data
  })
}

// 预览生成代码
export function previewTable(tableId) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/preview/' + tableId,
    method: 'get'
  })
}

// 删除数据表
export function delDatatable(alias, tableName) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/remove?alias='+alias+'&tableName='+tableName,
    method: 'delete',
  })
}

// 生成文件到本地
export function batchGenToLocal(tables) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/batchGenToLocal/' + tables,
    method: 'get'
  })
}

// 删除数据表
export function dropDataTable(alias, tableName) {
  return request({
    url: Api.API_SYSTEM + '/toolkit/datatable/drop?alias='+alias+'&tableName='+tableName,
    method: 'delete',
  })
}
