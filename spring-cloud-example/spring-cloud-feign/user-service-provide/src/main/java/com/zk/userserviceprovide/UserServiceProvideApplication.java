package com.zk.userserviceprovide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 用户服务提供者
 *
 * @author zhoukun
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
public class UserServiceProvideApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceProvideApplication.class, args);
    }
}
