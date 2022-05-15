package com.t1.oauth2.common;

import com.t1.oauth2.common.properties.SecurityProperties;
import com.t1.oauth2.common.properties.TokenStoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * 鉴权自动配置
 *
 * @author Bruce Lee (Copy)
 * @version 1.0
 * @date 2021/7/24
 * <p>

 */
@EnableConfigurationProperties({SecurityProperties.class, TokenStoreProperties.class})
@ComponentScan
public class AuthClientAutoConfiguration {
}
