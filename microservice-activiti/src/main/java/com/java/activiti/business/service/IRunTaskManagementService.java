package com.java.activiti.business.service;

import com.java.dto.PageInfo;
import com.java.dto.ResultMsg;

/**
 * Created by lu.xu on 2018/3/2. TODO:
 */
public interface IRunTaskManagementService {

  /**
   * 查询正在运行的任务列表
   */
  PageInfo queryRunTaskList(PageInfo pageInfo, String keywords);

  /**
   * 更改受理人
   *
   * @param taskId 任务id
   * @param assigneeUserId 受理人id
   */
  ResultMsg updateAssignee(String accessToken, String taskId, String assigneeUserId);
}
