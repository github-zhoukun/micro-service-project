package com.zk.springclouddiscoveryclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhoukun
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudDiscoveryClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDiscoveryClientApplication.class, args);
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
