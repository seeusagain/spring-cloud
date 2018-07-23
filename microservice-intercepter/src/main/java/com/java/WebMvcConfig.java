package com.java;

import com.java.test.interceptor.AuthenticationInterceptor;
import com.java.test.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by lu.xu on 2018/7/2.
 * TODO:注册会话拦截器
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    
    @Value("${server.context-path}")
    private String contextPath;
    
    /**
     * 使用@Bean可以在拦截器中进行注入操作
     * @return
     */
    @Bean
    AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
    
    @Bean
    SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor(contextPath);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor());
        registry.addInterceptor(authenticationInterceptor())
            /** 会话拦截器, 需要考虑对swaggerUi的影响*/
            .addPathPatterns("/**")
            .excludePathPatterns("/user/login")
            .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
