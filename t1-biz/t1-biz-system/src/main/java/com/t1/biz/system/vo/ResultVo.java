package com.t1.biz.system.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/3/15
 * @description 返回结果
 */
@Data
@Builder
public class ResultVo<T> implements Serializable {

    /**
     * 查询结果
     */
    private T result;
    /**
     * 扩展信息
     */
    private T extend;

}
