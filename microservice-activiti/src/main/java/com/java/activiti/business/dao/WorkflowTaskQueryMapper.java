package com.java.activiti.business.dao;

import com.java.activiti.business.entity.mybatis.WorkflowTaskQueryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lu.xu on 2017/12/26.
 * TODO:工作流任务查询
 */
public interface WorkflowTaskQueryMapper {
    
    /**
     *TODO:我的待办查询
     * @param userId  当前用户ID
     * @param userTypeEnumCode  {@link com.java.activiti.business.enums.CsmActAssigneeObjectTypeEnums}
     * @param roleTypeEnumCode  {@link com.java.activiti.business.enums.CsmActAssigneeObjectTypeEnums}
     * @param keywords 模糊查询关键字
     * @param startRecord 开始记录
     * @param endRecord 结束记录
     * @return
     */
    List<WorkflowTaskQueryEntity> queryTodo(@Param("userId") String userId,
        @Param("userTypeEnumCode") String userTypeEnumCode, @Param("roleTypeEnumCode") String roleTypeEnumCode,
        @Param("keywords") String keywords, @Param("startRecord") int startRecord, @Param("endRecord") int endRecord);
    
    int queryTodoCount(@Param("userId") String userId, @Param("userTypeEnumCode") String userTypeEnumCode,
        @Param("roleTypeEnumCode") String roleTypeEnumCode, @Param("keywords") String keywords);
    
    /**
     *TODO:查询我参与的
     * @param userId
     * @param excuteEnumCode @{@link com.java.enums.WorkflowVariablesEnums} EXCUTEUSER 执行工作流用户
     * @param keywords 模糊查询关键字
     * @param taskStatus @{@link com.java.activiti.business.enums.CsmFlowTaskStatusEnums}
     * @param startRecord 开始记录
     * @param endRecord 结束记录
     * @return
     */
    List<WorkflowTaskQueryEntity> queryParticipated(@Param("userId") String userId,
        @Param("excuteEnumCode") String excuteEnumCode, @Param("keywords") String keywords,
        @Param("taskStatus") String taskStatus, @Param("startRecord") int startRecord,
        @Param("endRecord") int endRecord);
    
    int queryParticipatedCount(@Param("userId") String userId, @Param("excuteEnumCode") String excuteEnumCode,
        @Param("keywords") String keywords, @Param("taskStatus") String taskStatus);
    
    /**
     * TODO:查询我发起的
     * @param userId 用户ID
     * @param taskStatus @{@link com.java.activiti.business.enums.CsmFlowTaskStatusEnums}
     * @param keywords 模糊查询关键字
     * @param startRecord 开始记录
     * @param endRecord 结束记录
     * @return
     */
    List<WorkflowTaskQueryEntity> queryStarted(@Param("userId") String userId, @Param("keywords") String keywords,
        @Param("taskStatus") String taskStatus, @Param("startRecord") int startRecord,
        @Param("endRecord") int endRecord);
    
    int queryStartedCount(@Param("userId") String userId, @Param("keywords") String keywords,
        @Param("taskStatus") String taskStatus);
    
    /**
     * 查询正在运行的任务信息
     * @param keywords
     * @param startRecord
     * @param endRecord
     * @return
     */
    List<WorkflowTaskQueryEntity> queryRunTask(@Param("keywords") String keywords,
        @Param("startRecord") int startRecord, @Param("endRecord") int endRecord);
    
    int queryRunTaskCount(@Param("keywords") String keywords);
}
