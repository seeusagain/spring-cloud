package com.java.activiti.business.service.impl;

import com.java.activiti.business.dao.WorkflowTaskQueryMapper;
import com.java.activiti.business.entity.mybatis.WorkflowTaskQueryEntity;
import com.java.activiti.business.service.IRunTaskManagementService;
import com.java.dto.PageInfo;
import com.java.dto.ResultMsg;
import com.java.utils.EmptyUtils;
import java.util.List;
import javax.annotation.Resource;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lu.xu on 2018/3/2. TODO:
 */
@Service
public class RunTaskManagementServiceImpl implements IRunTaskManagementService {

  private static final Logger logger = LoggerFactory.getLogger(RunTaskManagementServiceImpl.class);

  @Resource
  private WorkflowTaskQueryMapper workflowTaskQueryMapper;

  @Resource
  TaskService taskService;

  @Override
  public PageInfo queryRunTaskList(PageInfo pageInfo, String keywords) {
    if (pageInfo == null) {
      pageInfo = new PageInfo();
    }
    int total = this.workflowTaskQueryMapper.queryRunTaskCount(keywords);
    List<WorkflowTaskQueryEntity> data =
        this.workflowTaskQueryMapper
            .queryRunTask(keywords, pageInfo.getStartRecord(), pageInfo.getEndRecord());
    pageInfo.setTotal(total);
    pageInfo.setResultSets(data);
    return pageInfo;
  }

  @Override
  public ResultMsg updateAssignee(String accessToken, String taskId, String assigneeUserId) {
    logger.info("更改任务签收人taskId:{} , assigneeUserId:{}，accessToken：{}", taskId, assigneeUserId,
        accessToken);
    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    if (task == null) {
      return ResultMsg.error("任务不存在");
    }
    if (EmptyUtils.isEmpty(assigneeUserId)) {
      return ResultMsg.error("受理人参数为空");
    }
    task.setAssignee(assigneeUserId);
    taskService.saveTask(task);
    return ResultMsg.ok("更改成功");
  }

}
