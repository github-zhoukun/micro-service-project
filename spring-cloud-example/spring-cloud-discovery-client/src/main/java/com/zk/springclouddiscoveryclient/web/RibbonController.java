package com.zk.springclouddiscoveryclient.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {
    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String get() {
        String host ="http://" +  "eureka-discovery-provide"+"/eureka/provide/hello";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(host, String.class);
        return responseEntity.getBody();
    }
}
