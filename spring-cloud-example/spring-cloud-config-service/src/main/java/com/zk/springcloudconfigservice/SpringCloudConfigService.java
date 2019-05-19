package com.zk.springcloudconfigservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 配置启动类
 *
 * @author zhoukun
 */
@SpringBootApplication
@EnableConfigServer
@EnableScheduling
@EnableAsync
public class SpringCloudConfigService {


    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigService.class, args);
    }

}
