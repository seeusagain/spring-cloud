package com.java.dto;

/**
 * Created by lu.xu on 2018/7/4.
 * TODO:当前登录用户信息，适合存放于session或者redis中
 */
public class LogonUser {
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 用户名
     */
    private String displayName;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
