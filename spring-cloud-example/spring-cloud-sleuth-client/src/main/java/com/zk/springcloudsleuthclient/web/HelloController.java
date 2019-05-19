package com.zk.springcloudsleuthclient.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    public static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/say")
    public String say() {
        LOGGER.info("say:: return Ni Hao!");
        return "Ni Hao";
    }
}
