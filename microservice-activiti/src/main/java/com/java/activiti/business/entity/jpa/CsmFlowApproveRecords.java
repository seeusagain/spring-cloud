package com.java.activiti.business.entity.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "csm_flow_approve_records")
public class CsmFlowApproveRecords {
    @Id
    private String id;
    
    private String taskId;
    
    private String taskName;
    
    private String opinion;
    
    private String opinionRemarks;
    
    private String createUser;
    
    private Date createTime;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public String getOpinion() {
        return opinion;
    }
    
    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
    
    public String getOpinionRemarks() {
        return opinionRemarks;
    }
    
    public void setOpinionRemarks(String opinionRemarks) {
        this.opinionRemarks = opinionRemarks;
    }
    
    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}