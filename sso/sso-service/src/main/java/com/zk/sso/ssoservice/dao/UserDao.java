package com.zk.sso.ssoservice.dao;

import com.zk.sso.ssoservice.model.UserDo;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息表
 *
 * @author zhoukun
 */
public interface UserDao {
    int create(@Param("userDo") UserDo userDo);

    UserDo findByName(@Param("name") String name);

    int deleteById(@Param("id") Long id);

    UserDo verifyUser(@Param("name") String name, @Param("password") String password);

}
