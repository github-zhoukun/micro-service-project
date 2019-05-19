package com.zk.springcloudeurekadiscoveryprovide;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author zhoukun
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudEurekaDiscoveryProvideApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaDiscoveryProvideApplication.class, args);
    }
}
