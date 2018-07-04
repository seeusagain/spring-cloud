package com.java.test.utils;

import javax.servlet.http.HttpServletRequest;

import com.java.constants.AuthenticationConstats;
import com.java.dto.LogonUser;
import com.java.test.interceptor.AuthenticationHolder;

/**
 * Created by lu.xu on 2018/7/3.
 * TODO: 获取用户认证信息工具类
 */
public class AuthenticationUtils {
    /**
     * 获取当前线程中的用户信息（推荐）
     * @return
     */
    public static LogonUser getThreadUser() {
        return AuthenticationHolder.getUser();
    }
    
    /**
     * 获取当前线程中的用户ID（推荐）
     * @return
     */
    public static String getThreadUserId() {
        LogonUser ui = AuthenticationHolder.getUser();
        return null == ui ? null : ui.getUserId();
    }
    
    /**
     * 从session中获取用户
     * @param request
     * @return
     */
    public static LogonUser getSessionUser(HttpServletRequest request) {
        return (LogonUser) request.getSession().getAttribute(AuthenticationConstats.USER_INFO_SESSION_KEY);
    }
    
    /**
     * 从session获取用户ID
     * @param request
     * @return
     */
    public static String getSessionUserId(HttpServletRequest request) {
        LogonUser ui = (LogonUser) request.getSession().getAttribute(AuthenticationConstats.USER_INFO_SESSION_KEY);
        return null == ui ? null : ui.getUserId();
    }
    
}
