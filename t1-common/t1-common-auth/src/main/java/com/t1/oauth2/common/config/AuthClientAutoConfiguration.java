package com.t1.oauth2.common.config;

import com.t1.oauth2.common.properties.SecurityProperties;
import com.t1.oauth2.common.properties.TokenStoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Bruce Lee(copy)
 * @date 2019/10/7
 * <p>
 */
@EnableConfigurationProperties({SecurityProperties.class, TokenStoreProperties.class})
@ComponentScan
public class AuthClientAutoConfiguration {
}
