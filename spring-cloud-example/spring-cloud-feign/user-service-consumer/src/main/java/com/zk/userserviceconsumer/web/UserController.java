package com.zk.userserviceconsumer.web;

import com.zk.user.api.UserService;
import com.zk.user.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController implements UserService {

    @Autowired
    private UserService userService;

    @Override
    public boolean save(@RequestBody UserInfo userInfo) {
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userService.save(userInfo);
    }

    @Override
    public Collection<UserInfo> findAll() {
        return userService.findAll();
    }
}
