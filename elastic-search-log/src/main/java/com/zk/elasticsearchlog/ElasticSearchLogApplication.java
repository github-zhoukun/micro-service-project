package com.zk.elasticsearchlog;

import com.zk.elasticsearchlog.listener.A;
import com.zk.elasticsearchlog.listener.InitRateLimiterListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 启动类
 *
 * @author zhoukun
 */
@SpringBootApplication
@EnableConfigurationProperties
@EnableAspectJAutoProxy
@MapperScan("com.zk.elasticsearchlog.db.dao")
public class ElasticSearchLogApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ElasticSearchLogApplication.class, args)
//                .addApplicationListener(new InitRateLimiterListener());
        SpringApplication application = new SpringApplication(ElasticSearchLogApplication.class);
        application.addListeners(new InitRateLimiterListener(), new A());
        application.run(args);

    }


}
