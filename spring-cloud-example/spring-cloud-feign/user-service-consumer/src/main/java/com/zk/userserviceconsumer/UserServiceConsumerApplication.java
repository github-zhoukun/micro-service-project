package com.zk.userserviceconsumer;

import com.zk.user.api.UserService;
import com.zk.userserviceconsumer.rule.FisterServiceRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * feign 服务消费端
 *
 * @author zhoukun
 */
@SpringBootApplication(scanBasePackages = "com.zk")
@EnableFeignClients(clients = {UserService.class})
@EnableDiscoveryClient
@EnableHystrix
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@RibbonClient(value = "user-service-provide", configuration = UserServiceConsumerApplication.class) //不与eureka配合使用时
public class UserServiceConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceConsumerApplication.class, args);
    }

    @Bean
    public FisterServiceRule fisterServiceRule() {
        return new FisterServiceRule();
    }
}
