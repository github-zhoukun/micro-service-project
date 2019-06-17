package com.zk.sso.ssoservice.service.impl;

import com.zk.sso.ssoservice.dao.UserDao;
import com.zk.sso.ssoservice.model.UserDo;
import com.zk.sso.ssoservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息
 *
 * @author zhoukun
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean create(UserDo userDo) {
        UserDo isExist = this.findByName(userDo.getUserName());
        if (isExist != null) {
            log.info("该{}已经存在！", userDo.getUserName());
            return false;
        }
        int num = userDao.create(userDo);
        return num > 0;
    }

    @Override
    public UserDo findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id) > 0;
    }

    @Override
    public UserDo verifyUser(String name, String password) {
        return userDao.verifyUser(name, password);
    }
}
