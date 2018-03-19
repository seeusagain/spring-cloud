package com.java.activiti.business.entity.mybatis;

import java.util.Date;

/**
 * Created by lu.xu on 2017/12/26.
 * TODO: 工作流任务查询实体
 */
public class WorkflowTaskQueryEntity {
    /**
     * 任务ID
     */
    private String taskId;
    
    /**
     * 流程名称
     */
    private String flowName;
    
    /**
     * {@link com.java.activiti.business.enums.CsmFlowTaskStatusEnums}
     */
    private String status;
    
    /**
     * 当前节点名称
     */
    private String currentTaskName;
    
    /**
     * 流程定义key，也就是工作流模板code
     */
    private String processDefineKey;
    
    /**
     * 当前节点
     */
    private String currentNode;
    
    /**
     * 业务主键
     */
    private String bussinessKey;
    
    /**
     * 流程实例ID，可用于查看流程实例进程
     */
    private String processInstanceId;
    
    /**
     * 开始时间
     */
    private Date startTime;
    
    /**
     * 结束时间
     */
    private Date updateTime;
    
    /**
     * 参与时间
     */
    private Date participateTime;
    
    /**
     * 发起人姓名
     */
    private String createUserName;
    
    public String getProcessInstanceId() {
        return processInstanceId;
    }
    
    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getTaskId() {
        return taskId;
    }
    
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    
    public String getFlowName() {
        return flowName;
    }
    
    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }
    
    public String getCurrentTaskName() {
        return currentTaskName;
    }
    
    public void setCurrentTaskName(String currentTaskName) {
        this.currentTaskName = currentTaskName;
    }
    
    public String getProcessDefineKey() {
        return processDefineKey;
    }
    
    public void setProcessDefineKey(String processDefineKey) {
        this.processDefineKey = processDefineKey;
    }
    
    public String getBussinessKey() {
        return bussinessKey;
    }
    
    public void setBussinessKey(String bussinessKey) {
        this.bussinessKey = bussinessKey;
    }
    
    public Date getStartTime() {
        return startTime;
    }
    
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public Date getParticipateTime() {
        return participateTime;
    }
    
    public void setParticipateTime(Date participateTime) {
        this.participateTime = participateTime;
    }
    
    public String getCurrentNode() {
        return currentNode;
    }
    
    public void setCurrentNode(String currentNode) {
        this.currentNode = currentNode;
    }
    
    public String getCreateUserName() {
        return createUserName;
    }
    
    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
