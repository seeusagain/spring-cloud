package com.java.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.constants.AuthenticationConstats;
import com.java.dto.LogonUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by lu.xu on 2018/7/2.
 * TODO:拦截会话，给当前访问的线程threadLocal存放会话中的用户信息，其目的：
 * 1.可以在service层中直接获取会话中的用户信息，而不用在controller中获取再传入，或者是将renquest传入service（不推荐）
 * 2.可以自定义注解，拦截访问service层的方法，并判断用户信息是否有效
 *
 * 设计思路：
 * 1.在controller执行之前将会话中的用户信息（如有），存放在当前线程的threadLocal变量中
 * 2.在controller执行之后将当前线程的threadLocal变量移除（避免占用内存，此处可能访问后线程就失效了，
 * 目前知识面还没有到达能理解各个容器的线程管理方面，所以还是先手动移除）
 */
public class AuthenticationInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationInterceptor.class);
    
    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        LogonUser LogonUser = (LogonUser) request.getSession().getAttribute(AuthenticationConstats.USER_INFO_SESSION_KEY);
        if (null != LogonUser) {
            AuthenticationHolder.setUser(LogonUser);
        }
        return true;
    }
    
    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        AuthenticationHolder.clear();
    }
    
    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        
    }
    
}
