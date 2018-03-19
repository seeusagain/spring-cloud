package com.java.enums;

/**
 * Created by lu.xu on 2017/12/25.
 * TODO: 工作流，执行过程中的参数定义；一经发布后，可以添加，但勿改动
 */
public enum WorkflowVariablesEnums {
    VARIABLE_TASKID("TASK_ID", "任务ID"), VARIABLE_FLOWNAME("FLOWNAME", "流程名称"), VARIABLE_STARTUSER("STARTUSER",
        "发起人用户ID"), VARIABLE_EXCUTEUSER("EXCUTEUSER", "执行当前操作用户ID"), VARIABLE_EXCUTEDATE("EXCUTEDATE", "操作日期"),
    VARIABLE_OPINION("OPINION","分支流转标识"), VARIABLE_ASSIGNEE("ASSIGNEE", "任务受理人"), VARIABLE_OPINION_REMARKS("OPINION_REMARKS", "审批意见");
    private String code;
    
    private String codeName;
    
    WorkflowVariablesEnums(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
    
    WorkflowVariablesEnums() {
    }
    
    public String getCode() {
        return code;
    }
    
    public String getCodeName() {
        return codeName;
    }
}
