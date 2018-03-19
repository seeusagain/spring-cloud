package com.java.activiti.business.service.impl;

import java.io.InputStream;
import java.util.*;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java.activiti.business.dao.QueryApproveRecordsMapper;
import com.java.activiti.business.dao.UserInfoForAssigneeMapper;
import com.java.activiti.business.dao.WorkflowTaskQueryMapper;
import com.java.activiti.business.entity.jpa.CsmActAssignee;
import com.java.activiti.business.entity.jpa.CsmActAssigneeObject;
import com.java.activiti.business.entity.jpa.CsmFlowTask;
import com.java.activiti.business.entity.jpa.UserInfo;
import com.java.activiti.business.entity.mybatis.QueryApproveRecordsEntity;
import com.java.activiti.business.entity.mybatis.WorkflowTaskQueryEntity;
import com.java.activiti.business.enums.CsmActAssigneeObjectTypeEnums;
import com.java.activiti.business.enums.WorkflowTaskTypeEnums;
import com.java.activiti.business.repository.CsmActAssigneeObjectRespository;
import com.java.activiti.business.repository.CsmActAssigneeRespository;
import com.java.activiti.business.repository.CsmFlowTaskRespository;
import com.java.activiti.business.repository.UserInfoRespository;
import com.java.activiti.business.service.ITaskQueryService;
import com.java.dto.PageInfo;
import com.java.enums.WorkflowVariablesEnums;
import com.java.utils.EmptyUtils;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.java.dto.ResultMsg;

@Service
public class TaskQueryServiceImpl implements ITaskQueryService {
    private static final Logger logger = LoggerFactory.getLogger(TaskQueryServiceImpl.class);
    
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
    private StringRedisTemplate stringRedisTemplate;
    
    @Resource
    private WorkflowTaskQueryMapper workflowTaskQueryMapper;
    
    @Resource
    private CsmActAssigneeRespository csmActAssigneeRespository;
    
    @Resource
    private CsmActAssigneeObjectRespository csmActAssigneeObjectRespository;
    
    @Resource
    private CsmFlowTaskRespository csmFlowTaskRespository;
    
    @Resource
    private UserInfoForAssigneeMapper csmAssigneeQueryMapper;
    
    @Resource
    private UserInfoRespository userInfoRespository;
    
    @Resource
    private QueryApproveRecordsMapper queryApproveRecordsMapper;
    
    @Override
    public InputStream queryProcess(String processInstanceId) throws Exception {
        //获取历史流程实例
        HistoricProcessInstance processInstance =
            historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            return null;
        }
        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);
        
        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        ProcessDefinitionEntity definitionEntity =
            (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processInstance.getProcessDefinitionId());
        
        List<HistoricActivityInstance> highLightedActivitList =
            historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).list();
        //高亮环节id集合
        List<String> highLightedActivitis = new ArrayList<>();
        
        //高亮线路id集合
        List<String> highLightedFlows = getHighLightedFlows(definitionEntity, highLightedActivitList);
        
        for (HistoricActivityInstance tempActivity : highLightedActivitList) {
            String activityId = tempActivity.getActivityId();
            highLightedActivitis.add(activityId);
        }
        
        //中文显示的是口口口，设置字体就好了
        InputStream imageStream = diagramGenerator
            .generateDiagram(bpmnModel, "png", highLightedActivitis, highLightedFlows, "宋体", "宋体", "宋体", null, 1.0);
        //        单独返回流程图，不高亮显示
        //        imageStream = diagramGenerator.generatePngDiagram(bpmnModel);
        return imageStream;
    }
    
    /**
     * 获取需要高亮的线
     * @param processDefinitionEntity
     * @param historicActivityInstances
     * @return
     */
    private List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity,
        List<HistoricActivityInstance> historicActivityInstances) {
        // 用以保存高亮的线flowId
        List<String> highFlows = new ArrayList<String>();
        // 对历史流程节点进行遍历
        for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
            // 得到节点定义的详细信息
            ActivityImpl activityImpl =
                processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());
            // 用以保存后需开始时间相同的节点
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();
            ActivityImpl sameActivityImpl1 =
                processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());
            // 将后面第一个节点放在时间相同节点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                // 后续第一个节点
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
                // 后续第二个节点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
                    // 如果第一个节点和第二个节点开始时间相同保存
                    ActivityImpl sameActivityImpl2 =
                        processDefinitionEntity.findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {
                    // 有不相同跳出循环
                    break;
                }
            }
            // 取出节点的所有出去的线
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
            for (PvmTransition pvmTransition : pvmTransitions) {
                // 对所有的线进行遍历
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
                // 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }
    
    @Override
    public ResultMsg queryTaskList(String accessToken, String taskType, String taskStatus, String keywords,
        PageInfo pageInfo) {
        if (EmptyUtils.isAnyEmpty(accessToken, taskType)) {
            return new ResultMsg(false, "accessToken、taskType 参数必需");
        }
        String userId = stringRedisTemplate.opsForValue().get(accessToken);
        if (EmptyUtils.isEmpty(userId)) {
            return new ResultMsg(false, "accessToken无效");
        }
        
        taskType = taskType.trim();
        if (EmptyUtils.isNotEmpty(taskStatus)) {
            taskStatus = taskStatus.trim();
        }
        if (null == pageInfo) {
            pageInfo = new PageInfo();
        }
        List<WorkflowTaskQueryEntity> resultSets = null;
        int total = 0;
        //我的待办
        if (WorkflowTaskTypeEnums.TASK_TYPE_TODO.getCode().equals(taskType)) {
            resultSets = workflowTaskQueryMapper.queryTodo(userId, CsmActAssigneeObjectTypeEnums.TYPE_USER
                .getCode(), CsmActAssigneeObjectTypeEnums.TYPE_ROLE
                    .getCode(), keywords, pageInfo.getStartRecord(), pageInfo.getEndRecord());
            if (EmptyUtils.isNotEmpty(resultSets)) {
                total = workflowTaskQueryMapper.queryTodoCount(userId, CsmActAssigneeObjectTypeEnums.TYPE_USER
                    .getCode(), CsmActAssigneeObjectTypeEnums.TYPE_ROLE.getCode(), keywords);
            }
        }
        //我参与的(只要是工作流节点涉及到当前用户的，都显示，不一定是只参与了审批)
        else if (WorkflowTaskTypeEnums.TASK_TYPE_PARTICIPATED.getCode().equals(taskType)) {
            resultSets = workflowTaskQueryMapper.queryParticipated(userId, WorkflowVariablesEnums.VARIABLE_EXCUTEUSER
                .getCode(), keywords, taskStatus, pageInfo.getStartRecord(), pageInfo.getEndRecord());
            if (EmptyUtils.isNotEmpty(resultSets)) {
                total =
                    workflowTaskQueryMapper.queryParticipatedCount(userId, WorkflowVariablesEnums.VARIABLE_EXCUTEUSER
                        .getCode(), keywords, taskStatus);
            }
        }
        //我发起的
        else if (WorkflowTaskTypeEnums.TASK_TYPE_STARTED.getCode().equals(taskType)) {
            resultSets = workflowTaskQueryMapper
                .queryStarted(userId, keywords, taskStatus, pageInfo.getStartRecord(), pageInfo.getEndRecord());
            if (EmptyUtils.isNotEmpty(resultSets)) {
                total = workflowTaskQueryMapper.queryStartedCount(userId, keywords, taskStatus);
            }
        } else {
            return new ResultMsg(false, "taskType参数不匹配");
        }
        pageInfo.setResultSets(resultSets);
        pageInfo.setTotal(total);
        return new ResultMsg(true, null, pageInfo);
    }
    
    @Override
    public ResultMsg queryApproveUserInfo(String processInstanceId) {
        if (EmptyUtils.isEmpty(processInstanceId)) {
            return new ResultMsg(false, "缺少参数");
        }
        
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (EmptyUtils.isEmpty(tasks)) {
            logger.error("查询任务当前受理人，未查询到有效task数据 processInstanceId：{}", processInstanceId);
            return new ResultMsg(false, "未查询到有效数据");
        }
        
        List<CsmActAssigneeObject> objects = new ArrayList<>();
        for (Task task : tasks) {
            if (EmptyUtils.isEmpty(task.getAssignee())) {
                logger.error("当前任务无受理人，processInstanceId：{},taskId:{}", processInstanceId, task.getId());
                continue;
            }
            String assigneeId = task.getAssignee();
            CsmActAssignee csmActAssignee = csmActAssigneeRespository.findOne(assigneeId);
            if (csmActAssignee == null) {
                //不存在自定义授权节点表，说明是单一用户
                UserInfo userInfo = this.userInfoRespository.findOne(assigneeId);
                if (userInfo == null) {
                    logger.error("查询任务当前受理人，在用户表未查询到数据，assigneeId：{}", assigneeId);
                    continue;
                }
                CsmActAssigneeObject assigneeObject = new CsmActAssigneeObject();
                assigneeObject.setAssigneeType(CsmActAssigneeObjectTypeEnums.TYPE_USER.getCode());
                assigneeObject.setAssigneeObjectId(assigneeId);
                assigneeObject.setAssigneeObjectName(userInfo.getUserName());
                objects.add(assigneeObject);
            } else {
                //说明是受理人字段存储的是受理节点ID，则向下寻找受理对象
                List<CsmActAssigneeObject> assigneeObjects =
                    this.csmActAssigneeObjectRespository.queryByAssigneeId(assigneeId);
                if (EmptyUtils.isEmpty(assigneeObjects)) {
                    logger.error("查询任务当前受理人，在授权对象表未查询到数据，assigneeId：{}", assigneeId);
                    continue;
                }
                objects.addAll(assigneeObjects);
            }
        }
        return new ResultMsg(true, null, objects);
    }
    
    @Override
    public ResultMsg queryApproveRecords(String processInstanceId) {
        if (EmptyUtils.isEmpty(processInstanceId)) {
            return new ResultMsg(false, "流程实例参数缺失");
        }
        //TODO：这块存在优化空间 
        CsmFlowTask flowTask = this.csmFlowTaskRespository.queryByProcessInstanceId(processInstanceId);
        if (flowTask == null) {
            logger.info("查询审批记录，flowtask 为空");
            return new ResultMsg(true, "数据为空");
        }
        //records
        List<QueryApproveRecordsEntity> results = queryApproveRecordsMapper.queryApproveRecords(processInstanceId);
        JSONObject result = new JSONObject();
        result.put("title", flowTask.getFlowName());
        result.put("results", results);
        return new ResultMsg(true, "", result);
    }
    
}
