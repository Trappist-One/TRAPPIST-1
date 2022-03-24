package com.t1.zookeeper.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * zookeeper配置
 *
 * @author Bruce Lee(copy)
 * @version 1.0
 * @date 2021/4/3
 * <p>
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "t1.zookeeper")
public class ZookeeperProperty {
    /**
     * zk连接集群，多个用逗号隔开
     */
    private String connectString;

    /**
     * 会话超时时间(毫秒)
     */
    private int sessionTimeout = 15000;

    /**
     * 连接超时时间(毫秒)
     */
    private int connectionTimeout = 15000;

    /**
     * 初始重试等待时间(毫秒)
     */
    private int baseSleepTime = 2000;

    /**
     * 重试最大次数
     */
    private int maxRetries = 10;
}
