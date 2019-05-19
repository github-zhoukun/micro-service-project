package com.zk.user.api;

import com.zk.user.hystrix.UserHystrixCommod;
import com.zk.user.model.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;

/**
 * {@link com.zk.user.model.UserInfo}
 *
 * @author zhoukun
 */
@FeignClient(value = "user-service-provide", fallback = UserHystrixCommod.class)
public interface UserService {

    @PostMapping("/user/save")
    boolean save(@RequestBody UserInfo userInfo);

    @GetMapping("/user/find/all")
    Collection<UserInfo> findAll();
}
