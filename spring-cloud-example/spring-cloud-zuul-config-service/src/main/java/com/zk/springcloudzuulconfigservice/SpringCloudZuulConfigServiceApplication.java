package com.zk.springcloudzuulconfigservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * zuul 整合 config 服务
 *
 * @author zhoukun
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class SpringCloudZuulConfigServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulConfigServiceApplication.class);
    }
}
