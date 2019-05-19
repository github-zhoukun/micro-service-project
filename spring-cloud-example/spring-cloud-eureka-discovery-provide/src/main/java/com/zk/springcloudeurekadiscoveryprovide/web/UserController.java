package com.zk.springcloudeurekadiscoveryprovide.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eureka/provide")
public class UserController {

    @RequestMapping("/hello")
    public String getString() {
        return "Hello";
    }
}
