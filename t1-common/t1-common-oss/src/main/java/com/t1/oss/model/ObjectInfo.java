package com.t1.oss.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Bruce Lee(copy)
 * @date 2021/2/11
 * <p>
 */
@Setter
@Getter
public class ObjectInfo {
    /**
     * 对象查看路径
     */
    private String objectUrl;
    /**
     * 对象保存路径
     */
    private String objectPath;
}
