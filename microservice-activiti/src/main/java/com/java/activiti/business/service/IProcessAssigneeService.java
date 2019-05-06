package com.java.activiti.business.service;

import com.alibaba.fastjson.JSONArray;
import com.java.activiti.business.entity.jpa.CsmActAssignee;
import com.java.activiti.business.entity.jpa.CsmActAssigneeObject;
import com.java.dto.PageInfo;
import com.java.dto.ResultMsg;

/**
 * TODO:工作流授权节点信息采用的是自定义授权表，在工作流执行的接口中，会查询授权信息
 */
public interface IProcessAssigneeService {

  /**
   * 添加流程节点授权
   */
  ResultMsg addAssignee(CsmActAssignee assignee, String accessToken);

  /**
   * 删除流程节点授权
   */
  ResultMsg delAssignee(String assigneeId, String accessToken);

  /**
   * 添加流程节点授权对象
   */
  ResultMsg addAssigneeObject(CsmActAssigneeObject assigneeObject, String accessToken);

  /**
   * 删除流程节点授权对象
   */
  ResultMsg delAssigneeObj(String assigneeObjId, String accessToken);

  /**
   * 删除授权
   */
  ResultMsg delAuthorization(String id);

  /**
   * 模糊分页查询授权模板列表
   */
  ResultMsg queryProcessAssigneeList(String keywords, PageInfo pageInfo);

  /**
   * 根据模板名称查询授权节点信息
   */
  ResultMsg queryElementList(String processKey);

  /**
   * 根据授权信息ID,获取授权对象列表
   */
  ResultMsg queryAssigneeList(String assigneeId);

  /**
   * 获取系统中现有的流程
   */
  JSONArray getAllProcessDefinition();

  /**
   * 根据流程定义的ID获取所有的节点
   */
  public Object getElementByProcesskey(String processDefinitionKey);
}
