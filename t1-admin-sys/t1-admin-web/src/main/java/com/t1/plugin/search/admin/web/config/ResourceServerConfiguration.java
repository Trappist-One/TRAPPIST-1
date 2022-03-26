package com.t1.plugin.search.admin.web.config;

import com.t1.common.config.DefaultPasswordConfig;
import com.t1.oauth2.common.config.DefaultResourceServerConf;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @@author Bruce Lee (copy)
 */
@Configuration
@EnableResourceServer
@Import({DefaultPasswordConfig.class})
public class ResourceServerConfiguration extends DefaultResourceServerConf {
}
