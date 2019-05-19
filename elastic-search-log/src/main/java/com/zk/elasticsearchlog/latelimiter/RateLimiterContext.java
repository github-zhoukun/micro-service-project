package com.zk.elasticsearchlog.latelimiter;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 漏桶上下文
 *
 * @author zhoukun
 */
@Component
public class RateLimiterContext {

    Map<String, RateLimiter> rateLimiterMap = new HashMap<>();

    public RateLimiter getRateLimiterMap(String rateLimiterName) {
        return this.rateLimiterMap.get(rateLimiterName);
    }

    public void addRateLimiterMap(String rateLimiterName, RateLimiter value) {
        this.rateLimiterMap.put(rateLimiterName, value);
    }
}
