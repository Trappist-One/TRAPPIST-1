package com.t1.common.config;

import com.t1.common.feign.UserService;
import com.t1.common.resolver.ClientArgumentResolver;
import com.t1.common.resolver.TokenArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 默认SpringMVC拦截器
 *
 * @author Bruce Lee(copy)
 * @date 2019/8/5
 * <p>
 */
public class DefaultWebMvcConfig implements WebMvcConfigurer {
	@Lazy
	@Autowired
	private UserService userService;

	/**
	 * Token参数解析
	 *
	 * @param argumentResolvers 解析类
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		//注入用户信息
		argumentResolvers.add(new TokenArgumentResolver(userService));
		//注入应用信息
		argumentResolvers.add(new ClientArgumentResolver());
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		/** 本地文件上传路径 */
		registry.addResourceHandler("/profile/**").addResourceLocations("file:" + GlobalConfig.getProfile() + "/");
	}
}
