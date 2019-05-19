package com.zk.springcloudzkclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhoukun
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudZkClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZkClientApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
