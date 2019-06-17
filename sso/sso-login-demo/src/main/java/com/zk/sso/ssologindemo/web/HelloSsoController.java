package com.zk.sso.ssologindemo.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoukun
 */
@RestController
@RequestMapping("/hello")
public class HelloSsoController {


    @GetMapping("/")
    public String hello(HttpServletRequest request) {
        String token = request.getParameter("token");
        return "登入成功 token=" + token;
    }


}
