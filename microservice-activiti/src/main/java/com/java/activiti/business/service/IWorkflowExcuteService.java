package com.java.activiti.business.service;

import com.java.dto.ResultMsg;
import com.java.dto.WorkFlowAssignee;

/**
 * 工作流执行接口
 */
public interface IWorkflowExcuteService {

  /**
   * 开始一个工作流实例
   *
   * @param processDefinitionKey 流程定义key
   * @param flowName 名称
   * @param businessKey 业务主键
   * @param accessToken 访问令牌
   */
  ResultMsg startWorkflow(String processDefinitionKey, String flowName, String businessKey,
      String accessToken);

  /**
   * 执行工作流任务
   *
   * @param taskId 任务ID
   * @param opinion 分支判断标记
   * @param workFlowAssignee 受理人
   */
  ResultMsg completeTask(String taskId, String opinion, String opinionRemarks,
      WorkFlowAssignee workFlowAssignee,
      String accessToken);

}
