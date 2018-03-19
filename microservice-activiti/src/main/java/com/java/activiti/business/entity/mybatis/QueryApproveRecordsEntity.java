package com.java.activiti.business.entity.mybatis;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lu.xu on 2018/2/4.
 * TODO:查询审批记录实体
 */
public class QueryApproveRecordsEntity implements Serializable{
    /**
     * 审批任务ID
     */
    private String taskId;
    
    /**
     * 审批节点名称
     */
    private String taskName;
    
    /**
     *
     */
    private String userName;
    
    private Date createDate;
    
    private String opinion;
    
    private String opinionRemarks;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
}
