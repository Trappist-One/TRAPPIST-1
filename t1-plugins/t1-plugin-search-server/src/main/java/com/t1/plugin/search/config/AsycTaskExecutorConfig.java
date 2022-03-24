package com.t1.plugin.search.config;

import com.t1.common.config.DefaultAsycTaskConfig;
import org.springframework.context.annotation.Configuration;

/**
 * 线程池配置、启用异步
 * @Async quartz 需要使用
 *
 * @author Bruce Lee (copy)
 */
@Configuration
public class AsycTaskExecutorConfig extends DefaultAsycTaskConfig {

}
