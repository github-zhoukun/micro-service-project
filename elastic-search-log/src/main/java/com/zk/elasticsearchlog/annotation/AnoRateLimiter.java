package com.zk.elasticsearchlog.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 漏桶注解
 *
 * @author zhoukun
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface AnoRateLimiter {

    /**
     * 令牌桶名字 唯一
     *
     * @return
     */
    String value();

    /**
     * 默认每秒创建100个令牌
     *
     * @return
     */
    double permitsPerSecond() default 100;
}
