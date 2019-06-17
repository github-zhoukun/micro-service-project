package com.zk.sso.ssoservice.web;

import com.zk.sso.ssoservice.model.UserDo;
import com.zk.sso.ssoservice.service.UserService;
import com.zk.sso.ssoservice.utils.auth.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登入页面
 *
 * @author zhoukun
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String form(HttpServletRequest request, HttpServletResponse response) {
        String originUrl = request.getParameter("originUrl");
        request.getSession().setAttribute("originUrl", originUrl);
        return "login";
    }

    @GetMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
        if (StringUtils.isEmpty(username)) {
            return "username not null";
        }
        if (StringUtils.isEmpty(password)) {
            return "password not null";
        }

        UserDo userDo = userService.verifyUser(username, password);
        if (userDo == null) {
            return "密码或者账号错误!";
        }
        Map<String, String> claims = new HashMap<>();
        claims.put("id", String.valueOf(userDo.getId()));
        claims.put("username", userDo.getUserName());
        LocalDateTime exprieLocalDateTime = LocalDateTime.now().plusMinutes(10L);
        ZonedDateTime zonedDateTime = exprieLocalDateTime.atZone(ZoneId.systemDefault());
        String token = JwtUtil.generateToken(claims, Date.from(zonedDateTime.toInstant()));
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);
        response.setHeader("token", token);
        String originUrl = (String) request.getSession().getAttribute("originUrl");
        response.sendRedirect(originUrl + "?token=" + token);
        return token;
    }
}
