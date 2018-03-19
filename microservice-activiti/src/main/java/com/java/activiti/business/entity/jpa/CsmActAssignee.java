package com.java.activiti.business.entity.jpa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "csm_act_assignee")
@ApiModel(description = "流程实体")
public class CsmActAssignee implements Serializable {
    @Id
    private String id;
    
    @ApiModelProperty(value = "流程定义编码")
    private String processKey;
    
    @ApiModelProperty(value = "流程定义名称")
    private String processName;
    
    private String elementCode;
    
    private String elementName;
    
    //    private String isMultiSign;
    
    private String assigneeCode;
    
    private String remarks;
    
    private Date createDate;
    
    private String createUser;
    
    private Date updateDate;
    
    private String updateUser;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }
    
    public String getProcessKey() {
        return processKey;
    }
    
    public void setProcessKey(String processKey) {
        this.processKey = processKey == null ? null : processKey.trim();
    }
    
    public String getProcessName() {
        return processName;
    }
    
    public void setProcessName(String processName) {
        this.processName = processName == null ? null : processName.trim();
    }
    
    public String getElementCode() {
        return elementCode;
    }
    
    public void setElementCode(String elementCode) {
        this.elementCode = elementCode == null ? null : elementCode.trim();
    }
    
    public String getElementName() {
        return elementName;
    }
    
    public void setElementName(String elementName) {
        this.elementName = elementName == null ? null : elementName.trim();
    }
    
    public String getAssigneeCode() {
        return assigneeCode;
    }
    
    public void setAssigneeCode(String assigneeCode) {
        this.assigneeCode = assigneeCode;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public String getCreateUser() {
        return createUser;
    }
    
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }
    
    public Date getUpdateDate() {
        return updateDate;
    }
    
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public String getUpdateUser() {
        return updateUser;
    }
    
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}