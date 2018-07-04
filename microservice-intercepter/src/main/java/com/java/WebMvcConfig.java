package com.java;

import com.java.test.interceptor.AuthenticationInterceptor;
import com.java.test.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lu.xu on 2018/7/2.
 * TODO:注册会话拦截器
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    
    @Value("${server.context-path}")
    private String contextPath;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 会话拦截器
         */
        registry.addInterceptor(new SessionInterceptor(contextPath));
        registry.addInterceptor(new AuthenticationInterceptor());
    }
}
