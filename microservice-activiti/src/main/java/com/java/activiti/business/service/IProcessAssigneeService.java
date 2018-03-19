package com.java.activiti.business.service;

import com.alibaba.fastjson.JSONArray;
import com.java.activiti.business.entity.jpa.CsmActAssignee;
import com.java.activiti.business.entity.jpa.CsmActAssigneeObject;
import com.java.activiti.business.service.impl.ProcessAssigneeServiceImpl;
import com.java.dto.PageInfo;
import com.java.dto.ResultMsg;

import java.util.List;

/**
 * TODO:工作流授权节点信息采用的是自定义授权表，在工作流执行的接口中，会查询授权信息
 * 
 */
public interface IProcessAssigneeService {
    
    /**
     * 添加流程节点授权
     * @param assignee
     * @param accessToken
     * @return
     */
    ResultMsg addAssignee(CsmActAssignee assignee, String accessToken);
    
    /**
     * 删除流程节点授权
     * @param assigneeId
     * @param accessToken
     * @return
     */
    ResultMsg delAssignee(String assigneeId, String accessToken);
    
    /**
     * 添加流程节点授权对象
     * @param assigneeObject
     * @param accessToken
     * @return
     */
    ResultMsg addAssigneeObject(CsmActAssigneeObject assigneeObject, String accessToken);
    
    /**
     * 删除流程节点授权对象
     * @param assigneeObjId
     * @param accessToken
     * @return
     */
    ResultMsg delAssigneeObj(String assigneeObjId, String accessToken);
    
    /**
     * 删除授权
     * @param id
     * @return
     */
    ResultMsg delAuthorization(String id);
    
    /**
     * 模糊分页查询授权模板列表
     * @param keywords
     * @param pageInfo
     * @return
     */
    ResultMsg queryProcessAssigneeList(String keywords, PageInfo pageInfo);
    
    /**
     * 根据模板名称查询授权节点信息
     * @param processKey
     * @return
     */
    ResultMsg queryElementList(String processKey);
    
    /**
     * 根据授权信息ID,获取授权对象列表
     * @param assigneeId
     * @return
     */
    ResultMsg queryAssigneeList(String assigneeId);
    
    /**
     *获取系统中现有的流程
     * @return
     */
    JSONArray getAllProcessDefinition();
    
    /**
     * 根据流程定义的ID获取所有的节点
     * @param processDefinitionKey
     * @return
     */
    public Object getElementByProcesskey(String processDefinitionKey);
}
