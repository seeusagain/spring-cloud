package com.java.exampleSharding.entity.mybatis;

/**
 * Created by lu.xu on 2018/3/19.
 * TODO:
 */
public class UserOrdersQuery {
    private String userId;
    
    private String userName;
    
    private int orderPrice;
    
    private String orderRemarks;
    
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
    
    public int getOrderPrice() {
        return orderPrice;
    }
    
    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }
    
    public String getOrderRemarks() {
        return orderRemarks;
    }
    
    public void setOrderRemarks(String orderRemarks) {
        this.orderRemarks = orderRemarks;
    }
}
