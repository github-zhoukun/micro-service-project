package com.zk.springcloudconfigclient.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhoukun
 */
@Controller
@RefreshScope //TODO 这个注解可以读取到 refresh 后的值
public class EchoController {
    @Value("${you.name}")
    private String name;

    @RequestMapping("echo/name")
    public String name() {
        return name;
    }
}
