package com.t1;

import com.t1.feign.annotation.EnableFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
* @author Bruce Lee(copy)
*/
@EnableFeignClients("com.t1")
@EnableFeignInterceptor
@EnableDiscoveryClient
//@EnableRedisHttpSession
@SpringBootApplication
public class AuthServerApp {
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(AuthServerApp.class);
		application.setEnvironmentPrefix("t1-auth");
		application.run(args);
	}
}
