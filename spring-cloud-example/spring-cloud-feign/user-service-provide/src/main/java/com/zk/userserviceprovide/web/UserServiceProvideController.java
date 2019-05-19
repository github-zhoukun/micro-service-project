package com.zk.userserviceprovide.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zk.user.api.UserService;
import com.zk.user.model.UserInfo;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserServiceProvideController implements UserService {
    Map<Integer, UserInfo> userMap = new HashMap<>();

    @Override
    public boolean save(UserInfo userInfo) {
        return userMap.putIfAbsent(userInfo.getId(), userInfo) == null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "findAllFallbackMethod",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5")
            })
    public Collection<UserInfo> findAll() {

        int sleepTime = new Random().nextInt(8);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println("--------" + e);
        }
        return userMap.values();
    }


    public Collection<UserInfo> findAllFallbackMethod() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1);
        userInfo.setName("失败");
        return new ArrayList<>(Arrays.asList(userInfo));
    }

}
