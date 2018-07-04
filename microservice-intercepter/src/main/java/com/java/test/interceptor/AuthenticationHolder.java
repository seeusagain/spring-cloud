package com.java.test.interceptor;

import com.java.dto.LogonUser;

/**
 * Created by lu.xu on 2018/7/3.
 * TODO: 会话中用户信息的threadLocal
 */
public class AuthenticationHolder {
    
    private static final ThreadLocal<LogonUser> THREAD_USER_INFO = new ThreadLocal<LogonUser>();
    
    public static LogonUser getUser() {
        return THREAD_USER_INFO.get();
    }
    
    public static void setUser(LogonUser LogonUser) {
        THREAD_USER_INFO.set(LogonUser);
    }
    
    public static void clear() {
        THREAD_USER_INFO.remove();
    }
}
