package com.t1.plugin.tm.txcln;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
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
 * @Date: 2021/5/8 17:39
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableTransactionManagerServer
@Slf4j
public class TxclnApp {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(TxclnApp.class, args);

        ConfigurableApplicationContext application = SpringApplication.run(TxclnApp.class, args);

        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if(path == null) {
            path = "";
        }
        log.info("\n----------------------------------------------------------\n\t" +
                "Application TRAPPIST-1 TxManager is running! Access URLs:\n\t" +
                "Home: \thttp://" + ip + ":" + port + path + "/doc.html\n\t" +
                "----------------------------------------------------------");
    }

}
