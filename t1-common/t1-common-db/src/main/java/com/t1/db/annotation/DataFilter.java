package com.t1.db.annotation;

import java.lang.annotation.*;

/**
 * 数据过滤
 *
 * @author Bruce Lee ( copy )
 * @since  2017-09-17
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {
    /**
     * 表的别名
     */
    String tableAlias() default "";

}

