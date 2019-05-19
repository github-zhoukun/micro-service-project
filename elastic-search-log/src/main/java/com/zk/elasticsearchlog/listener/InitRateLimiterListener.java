package com.zk.elasticsearchlog.listener;

import cn.hutool.core.annotation.AnnotationUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.zk.elasticsearchlog.annotation.AnoRateLimiter;
import com.zk.elasticsearchlog.latelimiter.RateLimiterContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 初始化漏统
 * Listener
 *
 * @author zhoukun
 */
@Slf4j
public class InitRateLimiterListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        RateLimiterContext rateLimiterContext = applicationContext.getBean(RateLimiterContext.class);
        Map<String, Object> rateLimiterBeans = applicationContext.getBeansWithAnnotation(RestController.class);
        if (rateLimiterBeans != null && !rateLimiterBeans.isEmpty()) {
            rateLimiterBeans.forEach((k, v) -> {
                Class clazz;
                if (AopUtils.isAopProxy(v)) {
                    clazz = AopUtils.getTargetClass(v);
                } else {
                    clazz = v.getClass();
                }
                String className = clazz.getName();
                Method[] methods = clazz.getDeclaredMethods();
                if (methods != null) {
                    for (Method method : methods) {
                        String methodName = method.getName();
                        AnoRateLimiter anoRateLimiter = AnnotationUtil.getAnnotation(method, AnoRateLimiter.class);
                        if (anoRateLimiter != null) {
                            String rateLimiterName = anoRateLimiter.value();
                            double permitsPerSecond = anoRateLimiter.permitsPerSecond();
                            rateLimiterName = StringUtils.isEmpty(rateLimiterName) ? className + "_" + methodName : rateLimiterName;
                            rateLimiterContext.addRateLimiterMap(rateLimiterName, RateLimiter.create(permitsPerSecond));
                        }

                    }
                }
            });
        }

    }
}
