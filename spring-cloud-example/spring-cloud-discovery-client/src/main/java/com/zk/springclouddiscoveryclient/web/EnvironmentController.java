package com.zk.springclouddiscoveryclient.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * .....
 *
 * @author zhoukun
 */
@RestController
@RequestMapping("/env")
public class EnvironmentController {


    public final Environment environment;

    @Autowired
    public EnvironmentController(Environment environment) {
        this.environment = environment;
    }


    @RequestMapping("/{name}")
    public String getEnv(@PathVariable String name) {
        return environment.getProperty(name);
    }

}
