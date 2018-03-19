package com.java.activiti.business.entity.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "csm_act_assignee_object")
public class CsmActAssigneeObject {
    @Id
    private String id;
    
    private String assigneeId;
    
    private String assigneeType;
    
    private String assigneeObjectId;
    
    private String assigneeObjectName;
    
    private String createUser;
    
    private Date createDate;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getAssigneeId() {
        return assigneeId;
    }
    
    public void setAssigneeId(String assigneeId) {
        this.assigneeId = assigneeId;
    }
    
    public String getAssigneeType() {
        return assigneeType;
    }
    
    public void setAssigneeType(String assigneeType) {
        this.assigneeType = assigneeType;
    }
    
    public String getAssigneeObjectId() {
        return assigneeObjectId;
    }
    
    public void setAssigneeObjectId(String assigneeObjectId) {
        this.assigneeObjectId = assigneeObjectId;
    }
    
    public String getAssigneeObjectName() {
        return assigneeObjectName;
    }
    
    public void setAssigneeObjectName(String assigneeObjectName) {
        this.assigneeObjectName = assigneeObjectName;
    }
    
    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}