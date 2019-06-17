package com.zk.sso.ssoservice.web;

import com.zk.sso.ssoservice.utils.auth.JwtUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 认证Token
 *
 * @author zhoukun
 */
@RestController
@RequestMapping("auth")
public class AuthTokenController {
    public static final String OK = "OK";
    public static final String FAIL = "FAIL";


    @GetMapping("token")
    public String authToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        try {
            Map<String, String> map = JwtUtil.verifyToken(token);
            return OK;
        } catch (Exception e) {
            e.printStackTrace();
            return FAIL;
        }
    }


}
