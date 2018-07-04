package com.java.test.entity;

import java.util.Date;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "用户信息")
public class UserInfo {
    private String userId;
    
    private String userPwd;
    
    private String userName;
    
    private String email;
    
    private String mobile;
    
    private Date createDate;
    
    private Date lastLoginDate;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }
    
    public String getUserPwd() {
        return userPwd;
    }
    
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Date getLastLoginDate() {
        return lastLoginDate;
    }
    
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }
}