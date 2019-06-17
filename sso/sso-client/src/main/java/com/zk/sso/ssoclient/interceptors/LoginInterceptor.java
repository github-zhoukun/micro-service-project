package com.zk.sso.ssoclient.interceptors;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登入拦截
 *
 * @author zhoukun
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final String OK = "ok";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        String originUrl = request.getRequestURL().toString();
        if (StringUtils.isEmpty(token)) {
            response.sendRedirect("http://127.0.0.1:2080?originUrl=" + originUrl);
            return false;
        }
        if (!StringUtils.isEmpty(token)) {
            Cookie cookie = new Cookie("token", token);
            cookie.setDomain("zk.com");
            response.addCookie(cookie);
        }
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        String result = httpClientGet(map);
        if (OK.equalsIgnoreCase(result)) {
            return true;
        } else {
            response.sendRedirect("http://127.0.0.1:2080?originUrl=" + originUrl);
            return false;
        }
    }


    public static String httpClientGet(Map<String, String> map) throws URISyntaxException, IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        List<NameValuePair> params = new ArrayList<>();
        map.forEach((k, v) ->
                params.add(new BasicNameValuePair(k, v))
        );

        URI uri = new URIBuilder().setScheme("http").setHost("localhost")
                .setPort(2080).setPath("/auth/token")
                .setParameters(params).build();

        HttpGet get = new HttpGet(uri);
        CloseableHttpResponse response = httpClient.execute(get);

        HttpEntity responseEntity = response.getEntity();
        System.out.println("响应状态为:" + response.getStatusLine());
        String result = EntityUtils.toString(responseEntity);

        httpClient.close();
        response.close();
        return result;

    }

}
