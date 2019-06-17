package com.zk.sso.ssoservice.service;

import com.zk.sso.ssoservice.model.UserDo;

/**
 * 用户信息表
 *
 * @author zhoukun
 */
public interface UserService {
    boolean create(UserDo userDo);

    UserDo findByName(String name);

    boolean deleteById(Long id);

    UserDo verifyUser(String name, String password);

}
