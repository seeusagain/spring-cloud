package com.java.activiti.business.service;

import java.io.InputStream;

import com.java.dto.PageInfo;
import com.java.dto.ResultMsg;

/**
 * 工作流执行接口
 */
public interface ITaskQueryService {
    /**
     * 查看流程图，并红色高亮显示当前流程节点
     * @param processInstanceId 流程实例id（act_hi_procinst）表ID
     * @return
     * @throws Exception
     */
    InputStream queryProcess(String processInstanceId) throws Exception;
    
    /**
     * TODO:工作流任务查询
     * @param accessToken 令牌
     * @param taskType  {@link com.java.activiti.business.enums.WorkflowTaskTypeEnums}
     * @param taskStatus {@link com.java.activiti.business.enums.CsmFlowTaskStatusEnums}
     * @param keywords 模糊查询关键字
     * @param pageInfo  分页信息
     * @return
     */
    ResultMsg queryTaskList(String accessToken, String taskType, String taskStatus, String keywords, PageInfo pageInfo);
    
    /**
     * 查询审批人
     * @param processInstanceId 流程实例id
     * @return
     */
    ResultMsg queryApproveUserInfo(String processInstanceId);
    
    /**
     *TODO:审批记录
     * @param processInstanceId 流程ID
     * @return
     */
    ResultMsg queryApproveRecords(String processInstanceId);
    
}
