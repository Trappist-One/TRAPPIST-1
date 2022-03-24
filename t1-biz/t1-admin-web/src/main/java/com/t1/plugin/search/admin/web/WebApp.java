package com.t1.plugin.search.admin.web;


import com.t1.common.search.annotation.EnableSearchClient;
import lombok.extern.slf4j.Slf4j;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.t1", exclude= SecurityAutoConfiguration.class)
@EnableDiscoveryClient
@EnableSearchClient
@EnableFeignClients(basePackages = "com.t1")
@MapperScan(basePackages = {"com.t1.biz.*.mapper"})
@Slf4j
public class WebApp {
    public static void main(String[] args) {
        SpringApplication.run(WebApp.class, args);
        log.info("启动WebApp成功！");
    }
}
