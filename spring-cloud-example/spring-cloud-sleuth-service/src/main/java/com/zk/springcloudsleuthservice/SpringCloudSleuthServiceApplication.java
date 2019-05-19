package com.zk.springcloudsleuthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class SpringCloudSleuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudSleuthServiceApplication.class, args);
    }
}
