package com.t1.common.constant;

/**
 * 配置项常量
 *
 * @author Bruce Lee(copy)
 * @date 2019/9/3
 */
public interface ConfigConstants {
    /**
     * 是否开启自定义隔离规则
     */
    String CONFIG_RIBBON_ISOLATION_ENABLED = "t1.ribbon.isolation.enabled";

    String CONFIG_LOADBALANCE_ISOLATION = "t1.loadbalance.isolation";

    String CONFIG_LOADBALANCE_ISOLATION_ENABLE = CONFIG_LOADBALANCE_ISOLATION + ".enabled";

    String CONFIG_LOADBALANCE_ISOLATION_CHOOSER = CONFIG_LOADBALANCE_ISOLATION + ".chooser";

    String CONFIG_LOADBALANCE_VERSION = "t1.loadbalance.version";
}
