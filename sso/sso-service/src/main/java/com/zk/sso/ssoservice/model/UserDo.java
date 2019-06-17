package com.zk.sso.ssoservice.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * 用户Do
 *
 * @author zhoukun
 */
@Alias("UserDo")
@Data
public class UserDo {
    /**
     * 主键id
     */
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
}
