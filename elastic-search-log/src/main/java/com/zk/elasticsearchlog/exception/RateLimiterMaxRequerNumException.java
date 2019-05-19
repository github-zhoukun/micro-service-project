package com.zk.elasticsearchlog.exception;

/**
 * 令牌桶限制异常
 *
 * @author zhoukun
 */
public class RateLimiterMaxRequerNumException extends RuntimeException {

    public RateLimiterMaxRequerNumException(String message) {
        super(message);
    }
}
