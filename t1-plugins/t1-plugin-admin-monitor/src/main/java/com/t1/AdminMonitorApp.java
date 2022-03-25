package com.t1;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Description:
 * @Author: Bruce Lee
 * @Date: 2021/5/7 16:26
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAdminServer
@Slf4j
public class AdminMonitorApp {

    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext application = SpringApplication.run(AdminMonitorApp.class, args);

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if(path == null) {
            path = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application TRAPPIST-1 AdminMonitor is running! Access URLs:\n\t" +
                "Home: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "----------------------------------------------------------");


    }
}
