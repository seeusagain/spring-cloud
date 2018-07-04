package com.java.constants;

/**
 * Created by lu.xu on 2018/7/2.
 * TODO: 系统登录认证相关常量
 */
public class AuthenticationConstats {
    /**
     * 用户登陆后在session中的key
     */
    public static final String USER_INFO_SESSION_KEY = "USER_INFO_SESSION_KEY";
    
    /**
     * 用户登陆后，用户名在session中的key
     */
    public static final String USER_NAME_SESSION_KEY = "USER_NAME_SESSION_KEY";
    
    /**
     * 用户登录接口地址
     * 之所以定义是因为拦截器要用到
     */
    public static final String AUTHENTICATION_LOGIN_URL = "/authentication/login";
    
    /**
     * 用户登录界面地址
     * 之所以定义是因为拦截器要用到
     */
    public static final String AUTHENTICATION_LOGOUT_PAGE_URL = "/authentication/loginPage";
    
    /**
     * 用户注销接口地址
     * 之所以定义是因为拦截器要用到
     */
    public static final String AUTHENTICATION_LOGOUT_URL = "/authentication/logout";
    
}
