package com.zk.springcloudhystrix.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

/**
 * @author zhoukun
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {
    /**
     * http://127.0.0.1:4000/actuator/hystrix.stream 可以查看失败的信息
     */

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    public static final String serviceUrl = "http://eureka-discovery-provide/eureka/provide/hello";

    @HystrixCommand(
            fallbackMethod = "errMethod",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5")
            }
    )
    @RequestMapping("/hello")
    public String hello() throws Exception {
        Thread.sleep(new Random().nextInt(10));
        return restTemplate.getForEntity(serviceUrl, String.class).getBody();
    }

    public String errMethod() {
        return "系统出了小差！";
    }
}
