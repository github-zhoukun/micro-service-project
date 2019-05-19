package com.zk.springcloudzkservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhoukun
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudZkServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZkServiceApplication.class, args);
    }
}
