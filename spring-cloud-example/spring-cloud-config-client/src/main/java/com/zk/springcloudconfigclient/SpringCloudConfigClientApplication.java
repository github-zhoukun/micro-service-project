package com.zk.springcloudconfigclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Set;

/**
 * 配置
 *
 * @author zhoukun
 */
@SpringBootApplication
@EnableScheduling
public class SpringCloudConfigClientApplication {
    public static final Logger LOGGER = LoggerFactory.getLogger(SpringCloudConfigClientApplication.class);

    private final ContextRefresher contextRefresher;
    @Autowired
    private Environment environment;

    @Autowired
    public SpringCloudConfigClientApplication(ContextRefresher contextRefresher) {
        this.contextRefresher = contextRefresher;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientApplication.class, args);
    }

    @Scheduled(fixedRate = 10 * 1000, initialDelay = 3 * 1000)
    public void autoRefreshConfig() {
        Set<String> updatePropertySet = contextRefresher.refresh();
        updatePropertySet.forEach(key -> {
                    LOGGER.info("线程：[{}] - 更新Key：[{}] - 跟新Value：[{}]",
                            Thread.currentThread(), key, environment.getProperty(key));
                }
        );
    }
}
