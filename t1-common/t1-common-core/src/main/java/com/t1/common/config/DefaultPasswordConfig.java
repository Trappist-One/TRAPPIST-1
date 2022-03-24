package com.t1.common.config;

import com.t1.common.utils.PwdEncoderUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
* @author Bruce Lee(copy)
* 密码工具类
*/
public class DefaultPasswordConfig {
	/**
	 * 装配BCryptPasswordEncoder用户密码的匹配
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder()	{
		return PwdEncoderUtil.getDelegatingPasswordEncoder("bcrypt");
	}
}
