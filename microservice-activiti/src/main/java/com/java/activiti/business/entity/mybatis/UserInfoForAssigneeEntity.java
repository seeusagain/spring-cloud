package com.java.activiti.business.entity.mybatis;

/**
 * Created by lu.xu on 2017/12/28.
 * TODO:
 */
public class UserInfoForAssigneeEntity {
    private String userId;
    
    private String userName;
    
    private String emailAddress;
    
    public UserInfoForAssigneeEntity(String userId, String userName, String emailAddress) {
        this.userId = userId;
        this.userName = userName;
        this.emailAddress = emailAddress;
    }
    
    public UserInfoForAssigneeEntity() {
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
