package com.t1.biz.activiti.util;

import lombok.experimental.UtilityClass;

/**
 * <p>
 * 业务处理工具类：
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/18
 */

@UtilityClass
public class BusinessProcessingUtil {

    /** 拼接总条数查询sql **/
    public String makeCountSql(String sql){
        return String.join("","select count(*) from (",sql,") temp");
    }

    /**
     * 清理sql模板
     *@Param [sql, condition]
     *@return java.lang.String
     */
    public String clearTemplate(String sql){
        return sql.replaceAll("%s|%c|%b|%d|%x|%o|%f|%a|%e|%g|%h|%%|%n|%tx","");
    }

    public static void main(String[] args) {
        System.out.println(String.format("您好%s，晚上好！您目前余额：%s", "张三", 10));
    }

}
