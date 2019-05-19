package com.zk.springcloudzkclient.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhoukun
 */
@RestController
@RequestMapping("/kun")
public class KunController {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/install")
    public String get() {
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("zookeeper-service");
        ServiceInstance serviceInstance = serviceInstances.get(0);
        String url = serviceInstance.getHost() + ":" + serviceInstance.getPort();
        ResponseEntity<String> reslut = restTemplate.getForEntity(serviceInstance.getUri() + "/hello/say/", String.class);
        return reslut.getBody();
    }
}
