package com.java.activiti.business.enums;

/**
 * Created by lu.xu on 2017/12/25.
 * TODO: 工作流节点授权，授权类型
 */
public enum CsmActAssigneeObjectTypeEnums {
    TYPE_USER("USER", "个人用户"), TYPE_ROLE("ROLE", "角色");
    
    private String code;
    
    private String codeName;
    
    CsmActAssigneeObjectTypeEnums(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }
    
    CsmActAssigneeObjectTypeEnums() {
    }
    
    public String getCode() {
        return code;
    }
    
    public String getCodeName() {
        return codeName;
    }
}
