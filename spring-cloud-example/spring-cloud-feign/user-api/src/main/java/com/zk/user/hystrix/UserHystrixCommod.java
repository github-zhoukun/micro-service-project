package com.zk.user.hystrix;

import com.zk.user.api.UserService;
import com.zk.user.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * Hystrix 整合feign
 *
 * @author zhoukun
 */
@Component
public class UserHystrixCommod implements UserService {
    public static final Logger logger = LoggerFactory.getLogger(UserHystrixCommod.class);

    @Override
    public boolean save(UserInfo userInfo) {
        logger.error("发生熔断  save");
        return false;
    }

    @Override
    public Collection<UserInfo> findAll() {
        logger.error("发生熔断  findAll");
        return Collections.emptyList();
    }
}
