package com.t1.biz.activiti.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 获取实体类业务数据Mapper
 * 只自动做单表
 * 多表自己写SQL太复杂了不做处理
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/14
 */
public interface CommonEntityBusinessMapper {

    /**
     * 动态获取业务表数据
     *@Param [TableName, idField, businessKey]
     *@return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> fetchFormBusinessData(@Param("TableName") String tableName,
                                             @Param("IdField") String idField,
                                             @Param("BusinessKey") Object businessKey);



    /**
     * 修改业务表状态
     */
    int UpdateBusinessDataStatus(@Param("TableName") String tableName,
                                 @Param("IdField") String idField,
                                 @Param("Status")  String status,
                                 @Param("BusinessKey") Object businessKey);

}
