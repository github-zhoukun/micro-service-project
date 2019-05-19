package com.zk.springcloudeurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author zhoukun
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudDiscoveryEurekaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDiscoveryEurekaServiceApplication.class, args);
    }

}
