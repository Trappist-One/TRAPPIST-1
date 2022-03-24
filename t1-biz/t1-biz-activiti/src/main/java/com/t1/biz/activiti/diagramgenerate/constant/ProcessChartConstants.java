package com.t1.biz.activiti.diagramgenerate.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

/**
 * <p>
 * 图标生成器常量
 * </p>
 *
 * @author Bruce Lee ( copy )
 * @Date: 2021/4/15
 */

@Getter
@AllArgsConstructor
public class ProcessChartConstants {

    /** 动态流程图颜色定义 **/
    public static final Color COLOR_NORMAL = new Color(1, 117, 1);
    public static final Color COLOR_CURRENT = new Color(233, 6, 6);
    /** 扩大裁剪尺寸(像素),保证图片可以居中显示 **/
    public static final int PROCESS_PADDING = 150;


}
