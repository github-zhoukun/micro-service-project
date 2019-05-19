package com.zk.userserviceconsumer.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * Ribbon 整合Eureka
 * 自定义负载规则
 *
 * @author zhoukun
 */
public class FisterServiceRule extends AbstractLoadBalancerRule {

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        ILoadBalancer loadBalancer = getLoadBalancer();
        List<Server> serverList = loadBalancer.getAllServers();
        //打印累表
        serverList.forEach(System.out::print);
        return serverList.get(0);
    }
}
