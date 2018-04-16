package com.java.dynamicDataSource.entity.mybatis;

/**
 * Created by lu.xu on 2018/4/2.
 * TODO:
 */
public class OracleQueryEntity {
    private String userId;
    
    private String userName;
    
    private String type;
    
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
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
