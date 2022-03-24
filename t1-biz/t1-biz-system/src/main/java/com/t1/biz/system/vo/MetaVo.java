package com.t1.biz.system.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Bruce Lee ( copy )
 * @date 2020/3/14
 * @description 路由信息
 */
@Data
@Builder
public class MetaVo implements Serializable {

    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/icons/svg
     */
    private String icon;

    /**
     * 路由缓存，keep-alive
     */
    private boolean noCache;

}
