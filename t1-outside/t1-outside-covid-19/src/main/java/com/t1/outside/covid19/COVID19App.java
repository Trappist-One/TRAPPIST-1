package com.t1.outside.covid19;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class COVID19App {
    public static void main(String[] args) {
        SpringApplication.run(COVID19App.class);
        log.info("启动 COVID19App 成功！");
    }
}
