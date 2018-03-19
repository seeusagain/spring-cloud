package com.java.activiti.business.enums;

/**
 * Created by lu.xu on 2017/12/25.
 * TODO: 工作流，任务类型
 */
public enum WorkflowTaskTypeEnums {
    TASK_TYPE_TODO("TODO", "我的待办"), TASK_TYPE_PARTICIPATED("PARTICIPATED", "我参与的"), TASK_TYPE_STARTED(
        "STARTED", "我发起的");
    
    private String code;
    
    private String codeName;
    
    WorkflowTaskTypeEnums(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
    
    WorkflowTaskTypeEnums() {
    }
    
    public String getCode() {
        return code;
    }
    
    public String getCodeName() {
        return codeName;
    }
}
