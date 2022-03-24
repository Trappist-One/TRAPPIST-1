package com.t1.biz.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/5/24
 * @description
 */
@Data
public class UserVo implements Serializable {

    /**
     * 用户ID
     */
    private Integer id;

    /**
     * 昵称
     */
    private String nickName;
}

