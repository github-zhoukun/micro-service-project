package com.zk.elasticsearchlog.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 欢迎页面
 */
@RestController
public class HelloEsController {

    @GetMapping("/")
    public String hello() {
        return "Welcome to Es Please " +
                "Go to the target address http://localhost:8080/swagger-ui.html";
    }
}
