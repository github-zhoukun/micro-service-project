package com.zk.elasticsearchlog.aop;

import cn.hutool.core.annotation.AnnotationUtil;
import com.google.common.util.concurrent.RateLimiter;
import com.zk.elasticsearchlog.annotation.AnoRateLimiter;
import com.zk.elasticsearchlog.exception.RateLimiterMaxRequerNumException;
import com.zk.elasticsearchlog.latelimiter.RateLimiterContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * web Aop切面
 * 这里主要实现漏桶 限制并发访问
 *
 * @author zhoukun
 */
@Slf4j
@Aspect
@Component
public class WebAspect {
    @Autowired
    private RateLimiterContext rateLimiterContext;

    @Pointcut("@annotation(com.zk.elasticsearchlog.annotation.AnoRateLimiter)")
    public void rateLimiterExecution() {
    }

    @Around("rateLimiterExecution()")
    public void doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        Class clazz = joinPoint.getTarget().getClass();
        String className = clazz.getName();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        String methodName = signature.getName();
        AnoRateLimiter anoRateLimiter = AnnotationUtil.getAnnotation(methodSignature.getMethod(), AnoRateLimiter.class);
        if (anoRateLimiter == null) {
            joinPoint.proceed();
        } else {
            String rateLimiterName = anoRateLimiter.value();
            if (StringUtils.isEmpty(rateLimiterName)) {
                rateLimiterName = className + "_" + methodName;
            }
            RateLimiter rl = rateLimiterContext.getRateLimiterMap(rateLimiterName);
            //5秒超时就结束
            LocalDateTime start = LocalDateTime.now();
            boolean isAcquire = rl.tryAcquire(5, TimeUnit.SECONDS);
            log.info("获取令牌结果: 结果：{} 时间差(毫秒)：{}", isAcquire, Duration.between(start, LocalDateTime.now()).toMillis());

            if (isAcquire) {
                joinPoint.proceed();
            } else {
                throw new RateLimiterMaxRequerNumException("超过最大请求数！ 请稍后再试");
            }
        }

    }
}
