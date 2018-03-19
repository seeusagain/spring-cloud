package com.java.activiti.business.enums;

/**
 * Created by lu.xu on 2017/12/25.
 * TODO: 工作流节点授权，是否多人会签
 */
public enum CsmActAssigneeMultiEnums {
    Y("Y", "会签"), N("N", "非会签");

    private String code;

    private String codeName;

    CsmActAssigneeMultiEnums(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    CsmActAssigneeMultiEnums() {
    }
    
    public String getCode() {
        return code;
    }
    
    public String getCodeName() {
        return codeName;
    }
}
