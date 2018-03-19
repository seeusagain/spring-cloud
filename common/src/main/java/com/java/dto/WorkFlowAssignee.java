package com.java.dto;

import java.util.ArrayList;

/**
 * Created by lu.xu on 2018/1/3.
 * TODO: 工作流执行，受理人对象封装
 * @WorkflowExcuteServiceImpl.completeTask 方法中会解析
 */
public class WorkFlowAssignee {
    
    /**
     * 是否多人会签
     */
    private Boolean multiSign = false;
    
    /**
     *节点对象受理标识，会匹配自定义流程节点处理对象表
     * 当不是多人会签的时候，只会按照第一个下标进行匹配
     */
    private ArrayList<String> assigneeCode;
    
    /**
     * 受理人，单个用户ID；当系统中自定义流程节点处理对象为空,且只有当isMultiSign=false，才会使用
     */
    private String assignee;
    
    /**
     * 会签受理人，支持多个；只有当isMultiSign=true 时，才会解析
     */
    private ArrayList<String> multiAssignees;
    
    /**
     * 会签节点CODE，只有当isMultiSign=true 时，才会解析
     */
    private String multiActivitiCode;
    
    public WorkFlowAssignee() {
    }
    
    public WorkFlowAssignee(Boolean multiSign, ArrayList<String> assigneeCode, String assignee,
        ArrayList<String> multiAssignees, String multiActivitiCode) {
        this.multiSign = multiSign;
        this.assigneeCode = assigneeCode;
        this.assignee = assignee;
        this.multiAssignees = multiAssignees;
        this.multiActivitiCode = multiActivitiCode;
    }
    
    public Boolean getMultiSign() {
        return multiSign;
    }
    
    public void setMultiSign(Boolean multiSign) {
        this.multiSign = multiSign;
    }
    
    public ArrayList<String> getAssigneeCode() {
        return assigneeCode;
    }
    
    public void setAssigneeCode(ArrayList<String> assigneeCode) {
        this.assigneeCode = assigneeCode;
    }
    
    public String getAssignee() {
        return assignee;
    }
    
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
    
    public ArrayList<String> getMultiAssignees() {
        return multiAssignees;
    }
    
    public void setMultiAssignees(ArrayList<String> multiAssignees) {
        this.multiAssignees = multiAssignees;
    }
    
    public String getMultiActivitiCode() {
        return multiActivitiCode;
    }
    
    public void setMultiActivitiCode(String multiActivitiCode) {
        this.multiActivitiCode = multiActivitiCode;
    }
}
