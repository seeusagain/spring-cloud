package com.java.activiti.business.enums;

/**
 * Created by lu.xu on 2017/12/25.
 * TODO: 工作流状态
 */
public enum CsmFlowTaskStatusEnums {
    RUNNING("RUNNING", "运行中"), COMPLETED("COMPLETED", "已完成");

    private String code;

    private String codeName;

    CsmFlowTaskStatusEnums(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    CsmFlowTaskStatusEnums() {
    }

    public String getCode() {
        return code;
    }

    public String getCodeName() {
        return codeName;
    }
}
