package com.java.activiti.business.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.java.activiti.business.dao.ActRuTaskMapper;
import com.java.activiti.business.entity.jpa.CsmActAssignee;
import com.java.activiti.business.entity.jpa.CsmFlowApproveRecords;
import com.java.activiti.business.entity.jpa.CsmFlowTask;
import com.java.activiti.business.entity.mybatis.ActRuTask;
import com.java.activiti.business.enums.CsmFlowTaskStatusEnums;
import com.java.activiti.business.repository.CsmActAssigneeRespository;
import com.java.activiti.business.repository.CsmFlowApproveRecordsRespository;
import com.java.activiti.business.repository.CsmFlowTaskRespository;
import com.java.activiti.business.service.IWorkflowExcuteService;
import com.java.dto.ResultMsg;
import com.java.dto.WorkFlowAssignee;
import com.java.enums.WorkflowVariablesEnums;
import com.java.utils.EmptyUtils;
import com.java.utils.IdGeneratorUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class WorkflowExcuteServiceImpl implements IWorkflowExcuteService {

  private static final Logger logger = LoggerFactory.getLogger(WorkflowExcuteServiceImpl.class);

  @Resource
  private ProcessEngine processEngine;

  @Resource
  RepositoryService repositoryService;

  @Resource
  ManagementService managementService;

  @Resource
  protected RuntimeService runtimeService;

  @Resource
  ProcessEngineConfiguration processEngineConfiguration;

  @Resource
  TaskService taskService;

  @Resource
  HistoryService historyService;

  @Resource
  CsmActAssigneeRespository csmActAssigneeRespository;

  @Resource
  StringRedisTemplate stringRedisTemplate;

  @Resource
  private CsmFlowTaskRespository csmFlowTaskRespository;

  @Resource
  private ActRuTaskMapper actRuExecutionMapper;

  @Resource
  private ActRuTaskMapper actRuTaskMapper;

  @Resource
  private CsmFlowApproveRecordsRespository csmFlowApproveRecordsRespository;

  @Override
  public ResultMsg startWorkflow(String processDefinitionKey, String flowName, String businessKey,
      String accessToken) {
    if (EmptyUtils.isAnyEmpty(processDefinitionKey, flowName, businessKey, accessToken)) {
      return ResultMsg.error("流程key、流程名称、业务主键、accessToken 必填");
    }
    logger
        .info(">>工作流开始：processDefinitionKey：{}，flowName：{}，businessKey：{}，accessToken：{}",
            processDefinitionKey, flowName, businessKey, accessToken);
    String userId = stringRedisTemplate.opsForValue().get(accessToken);
    if (EmptyUtils.isAnyEmpty(userId)) {
      return ResultMsg.error("用户令牌失效");
    }
    Map<String, Object> variables = new HashMap<>();
    variables.put(WorkflowVariablesEnums.VARIABLE_FLOWNAME.getCode(), flowName);
    variables.put(WorkflowVariablesEnums.VARIABLE_EXCUTEUSER.getCode(), userId);
    try {
      ProcessInstance processInstance = processEngine.getRuntimeService()
          .startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
      Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId())
          .singleResult();

      //往自定义表插入数据
      CsmFlowTask csmFlowTask = new CsmFlowTask();
      csmFlowTask.setId(IdGeneratorUtils.getSerialNo());
      csmFlowTask.setFlowName(flowName);
      csmFlowTask.setStatus(CsmFlowTaskStatusEnums.RUNNING.getCode());
      csmFlowTask.setCreateTime(new Date());
      csmFlowTask.setCreateUser(userId);
      csmFlowTask.setUpdateTime(new Date());
      csmFlowTask.setProcessInstanceId(task.getProcessInstanceId());
      csmFlowTask.setBusinessKey(businessKey);
      csmFlowTask.setProcessDefineKey(processDefinitionKey);
      csmFlowTaskRespository.save(csmFlowTask);

      JSONObject jsonObject = new JSONObject();
      jsonObject.put(WorkflowVariablesEnums.VARIABLE_TASKID.getCode(), task.getId());
      String noticeMsg = "执行成功";
      logger.info(noticeMsg);
      return ResultMsg.ok(noticeMsg, jsonObject);
    } catch (Exception e) {
      e.printStackTrace();
      logger.error("开始一个工作流异常");
      return ResultMsg.error(null, e.getMessage());
    }
  }

  @Override
  public ResultMsg completeTask(String taskId, String opinion, String opinionRemarks,
      WorkFlowAssignee workFlowAssignee, String accessToken) {
    String userId = stringRedisTemplate.opsForValue().get(accessToken);
    if (EmptyUtils.isAnyEmpty(userId)) {
      return ResultMsg.error("用户令牌失效");
    }
    logger
        .info(">>工作流执行开始：taskId：{}，opinion：{}，opinionRemarks：{}，accessToken：{}", taskId, opinion,
            opinionRemarks, accessToken);
    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    if (task == null) {
      String noticeMsg = "任务不存在";
      logger.info(noticeMsg);
      return ResultMsg.error(noticeMsg);
    }
    HashMap<String, Object> variables = new HashMap<>();
    variables.put(WorkflowVariablesEnums.VARIABLE_TASKID.getCode(), taskId);
    variables.put(WorkflowVariablesEnums.VARIABLE_EXCUTEUSER.getCode(), userId);
    variables.put(WorkflowVariablesEnums.VARIABLE_OPINION.getCode(), opinion);
    variables.put(WorkflowVariablesEnums.VARIABLE_OPINION_REMARKS.getCode(), opinionRemarks);
    //add logs
    logAssignee(workFlowAssignee);
    //execute
    ResultMsg rmg;
    if (null == workFlowAssignee || workFlowAssignee.getMultiSign() == false) {
      //正常userTask
      rmg = this.completeTaskNormal(taskId, variables, workFlowAssignee);
    } else {
      //会签
      rmg = this.completeTaskMilutSign(taskId, variables, workFlowAssignee);
    }

    if (rmg.isSuccess()) {
      //异步添加审批记录
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          addApproveRecords(taskId, task.getName(), opinion, opinionRemarks, userId);
        }
      };
      Thread addRecordThread = new Thread(runnable);
      addRecordThread.start();

      //异步发送邮件
      WorkFlowExcuteMailSender mailSender = new WorkFlowExcuteMailSender(
          task.getProcessInstanceId());
      Thread thread = new Thread(mailSender);
      thread.start();
    }
    return rmg;
  }

  /**
   * TODO:正常工作流执行
   */
  private ResultMsg completeTaskNormal(String taskId, HashMap<String, Object> variables,
      WorkFlowAssignee workFlowAssignee) {
    logger.info(">>正常userTask签署开始..taskId:{}", taskId);
    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
    String processInstanceId = task.getProcessInstanceId();

    List<ActRuTask> tasksBefore = this.actRuTaskMapper.selectByProcInstId(processInstanceId);
    //执行工作流
    taskService.complete(taskId, variables);
    List<ActRuTask> tasksAfter = this.actRuTaskMapper.selectByProcInstId(processInstanceId);

    //更新自定义表
    if (EmptyUtils.isEmpty(tasksAfter)) {
      csmFlowTaskRespository
          .updateByInstanceId(CsmFlowTaskStatusEnums.COMPLETED.getCode(), new Date(), task
              .getProcessInstanceId());
      String noticeMsg = "任务已执行，且工作流已经执行完毕";
      logger.info(noticeMsg);
      return ResultMsg.ok(noticeMsg);
    } else {
      csmFlowTaskRespository
          .updateByInstanceId(CsmFlowTaskStatusEnums.RUNNING.getCode(), new Date(),
              task.getProcessInstanceId());
    }
    //判断流程执行后是否执行完毕，如果没有完毕，则分配受理人
    for (ActRuTask afterTask : tasksAfter) {
      boolean isNewTask = true;
      for (ActRuTask beforeTask : tasksBefore) {
        if (beforeTask.getId().equals(afterTask.getId())) {
          isNewTask = false;
          break;
        }
      }
      //指定受理人
      if (isNewTask) {
        String assigneeCode = null;
        if (EmptyUtils.isNotEmpty(workFlowAssignee.getAssigneeCode())) {
          assigneeCode = workFlowAssignee.getAssigneeCode().get(0);
        }
        this.setAssigneeForUsertask(afterTask.getId(), assigneeCode,
            workFlowAssignee.getAssignee());
      }
    }

    String noticeMsg = "工作流执行成功";
    logger.info("completeTaskNormal " + noticeMsg);
    return ResultMsg.ok(noticeMsg);
  }

  /**
   * TODO:并行会签工作流执行,因为会签会有多个受理人，且是动态的，所以需要在执行工作流之前指定,这样才能生成多个并行节点
   *
   * @param workFlowAssignee 受理人信息
   */
  private ResultMsg completeTaskMilutSign(String taskId, HashMap<String, Object> variables,
      WorkFlowAssignee workFlowAssignee) {
    logger.info("会签开始>>> taskId:{}", taskId);
    if (EmptyUtils.isNotEmpty(workFlowAssignee.getAssigneeCode())) {
      logger.info("会签编码：{}", Arrays.toString(workFlowAssignee.getAssigneeCode().toArray()));
    }
    if (workFlowAssignee.getMultiSign() == false) {
      throw new RuntimeException("非会签节点请走正常执行流程");
    }
    if (EmptyUtils.isEmpty(workFlowAssignee.getMultiAssignees())
        && EmptyUtils.isEmpty(workFlowAssignee.getMultiActivitiCode())) {
      String noticeMsg = "会签参数异常，会签节点和会签者必须指定一个";
      logger.error(noticeMsg);
      return ResultMsg.error(noticeMsg);
    }
    if (null == variables) {
      variables = new HashMap<>();
    }

    ArrayList<String> fianlAssigness = new ArrayList<>();
    if (EmptyUtils.isEmpty(workFlowAssignee.getMultiActivitiCode())) {
      fianlAssigness = workFlowAssignee.getMultiAssignees();
    } else {
      Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
      ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
          .processDefinitionId(task.getProcessDefinitionId())
          .singleResult();
      String processKey = processDefinition.getKey();

      //查询出当前节点的受理对象
      List<CsmActAssignee> assignees;
      if (EmptyUtils.isEmpty(workFlowAssignee.getAssigneeCode())) {
        assignees = this.csmActAssigneeRespository
            .queryByProcessKeyAndElementCode(processKey, workFlowAssignee.getMultiActivitiCode());
      } else {
        assignees = this.csmActAssigneeRespository
            .queryByProcessKeyAndElementCodeAndAssigneeCodeIn(processKey, workFlowAssignee
                .getMultiActivitiCode(), workFlowAssignee.getAssigneeCode());
      }

      if (EmptyUtils.isNotEmpty(assignees)) {
        for (CsmActAssignee assignee : assignees) {
          fianlAssigness.add(assignee.getId());
        }
      }
    }

    if (EmptyUtils.isEmpty(fianlAssigness)) {
      String noticeMsg = "会签未指定受理人，同时也无法查询到自定义受理人，无法执行";
      logger.error(noticeMsg);
      return ResultMsg.error(noticeMsg);
    } else {
      logger.info("即将进行会签,taskId:{},会签对象Id：{}", taskId, Arrays.toString(fianlAssigness.toArray()));
    }

    //指定会签受理人参数，并执行工作流
    variables.put(WorkflowVariablesEnums.VARIABLE_ASSIGNEE.getCode(), fianlAssigness);

    taskService.complete(taskId, variables);
    String noticeMsg = "会签任务已经执行";
    logger.info(noticeMsg);
    return ResultMsg.ok(noticeMsg);
  }

  /**
   * TODO:给指定的usertask授权
   *
   * @param assigneeCode 节点授权标识
   * @param assigneeId 授权人
   */
  private void setAssigneeForUsertask(String taskId, String assigneeCode, String assigneeId) {
    logger.info(">>给userTask授权开始：taksid：{}，assigneeCode：{}，assigneeId：{}", taskId, assigneeCode,
        assigneeId);
    Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

    ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
        .processDefinitionId(task.getProcessDefinitionId())
        .singleResult();
    String processKey = processDefinition.getKey();
    String activitiKey = task.getTaskDefinitionKey();

    logger.info("userTask自定义授权：processKey:{},activitiKey:{}", processKey, activitiKey);

    List<CsmActAssignee> assignees;
    if (EmptyUtils.isEmpty(assigneeCode)) {
      assignees = csmActAssigneeRespository
          .queryByProcessKeyAndElementCode(processKey, activitiKey);
    } else {
      ArrayList<String> assigneeCodes = new ArrayList<String>();
      assigneeCodes.add(assigneeCode);
      assignees = csmActAssigneeRespository
          .queryByProcessKeyAndElementCodeAndAssigneeCodeIn(processKey, activitiKey, assigneeCodes);
    }

    /**
     * 默认使用系统查询授权人
     *  如果查询不到，则使用自定义授权
     *  如果查询不到，且自定义授权不存在，报个错
     */
    String assignee;
    if (EmptyUtils.isNotEmpty(assignees)) {
      assignee = assignees.get(0).getId();
      if (assignees.size() > 1) {
        logger.error(">> 工作流设置受理人，发现同一节点多个重复授权；processKey:{},activitiKey:{}", processKey,
            activitiKey);
      }
    } else {
      if (EmptyUtils.isEmpty(assigneeId)) {
        logger.error("userTask 未指定受理人processKey:{}, activitiKey:{}", processKey, activitiKey);
        throw new RuntimeException("流程没有受理人.taskid:" + taskId);
      }
      //如果没有自定义受理人，使用手工指定
      assignee = assigneeId;
    }
    taskService.claim(taskId, assignee);
  }

  /**
   * 打印一下assignee参数信息
   */
  private void logAssignee(WorkFlowAssignee assignee) {
    if (null == assignee) {
      logger.info("WorkFlowAssignee is null..");
      return;
    }
    logger.info(">>>>工作流-受理人对象日志记录开始>>>>");
    logger.info("是否会签：{}", assignee.getMultiSign());
    if (assignee.getMultiSign()) {
      logger.info("会签节点：{}", assignee.getMultiActivitiCode());
      if (EmptyUtils.isEmpty(assignee.getMultiAssignees())) {
        logger.info("多人会签，签署人集合 is null");
      } else {
        logger.info("多人会签，签署人集合：{}", Arrays.toString(assignee.getMultiAssignees().toArray()));
      }
    } else {
      logger.info("签署者：{}", assignee.getAssignee());
      if (EmptyUtils.isEmpty(assignee.getAssigneeCode())) {
        logger.info("节点对象受理标识 is null");
      } else {
        logger.info("节点对象受理标识：{}", Arrays.toString(assignee.getAssigneeCode().toArray()));
      }
    }
    logger.info(">>>>工作流-受理人对象日志记录结束>>>>");
  }

  /**
   * 添加审批记录
   */
  private void addApproveRecords(String taskId, String taskName, String opinion,
      String opinionRemarks,
      String createUserId) {
    try {
      logger
          .info("添加审批记录: taskId:{},opinion:{},opinionRemarks:{},createUserId:{}", taskId, opinion,
              opinionRemarks, createUserId);
      CsmFlowApproveRecords records = new CsmFlowApproveRecords();
      records.setId(IdGeneratorUtils.getSerialNo());
      records.setTaskId(taskId);
      records.setTaskName(taskName);
      records.setOpinion(opinion);
      records.setOpinionRemarks(opinionRemarks);
      records.setCreateUser(createUserId);
      records.setCreateTime(new Date());
      this.csmFlowApproveRecordsRespository.save(records);
      logger.info("添加审批记录完成");
    } catch (Exception e) {
      logger
          .error("添加审批记录异常,taskId:{},opinion:{},opinionRemarks:{},createUserId:{}", taskId, opinion,
              opinionRemarks, createUserId);
      e.printStackTrace();
    }
  }
}
