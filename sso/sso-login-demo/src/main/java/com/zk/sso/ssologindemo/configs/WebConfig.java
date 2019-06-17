package com.zk.sso.ssologindemo.configs;

import com.zk.sso.ssoclient.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置
 *
 * @author zhoukun
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns("/login**", "/error").addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //授权的访问源
                .allowedOrigins("*")
                //允许的请求动词
                .allowedMethods("POST, GET, OPTIONS, DELETE")
                //预检授权的有效期，单位：秒
                .maxAge(3600)
                //额外允许访问的响应头
                .allowedHeaders("x-requested-with")
                //是否允许携带
                .allowCredentials(true);
    }
}
