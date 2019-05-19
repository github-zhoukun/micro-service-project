package com.zk.springcloudhystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhoukun
 */
@SpringBootApplication
@EnableDiscoveryClient
//@EnableHystrix 这个是Hystrix  提供的 EnableCircuitBreaker SpringCloud 提供的
@EnableCircuitBreaker
public class SpringCloudHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        //ClientHttpRequestFactory 也可以获取这个子类实例构造器
        return new RestTemplate();
    }
}
