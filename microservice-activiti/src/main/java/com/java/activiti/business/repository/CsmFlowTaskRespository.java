package com.java.activiti.business.repository;

import java.util.Date;
import java.util.List;

import com.java.activiti.business.entity.jpa.CsmFlowTask;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.java.activiti.business.entity.jpa.CsmActAssignee;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CsmFlowTaskRespository extends JpaRepository<CsmFlowTask, String> {
    
    /**
     * 根据流程 运行实例id，修改自定义工作流信息
     * @param status @{@link com.java.activiti.business.enums.CsmFlowTaskStatusEnums}
     * @param updateTime 修改时间
     * @param processInstanceId 运行实例ID
     * @return
     */
    @Modifying
    @Transactional
    @Query("update CsmFlowTask  set status=:status,updateTime=:updateTime where processInstanceId = :processInstanceId")
    int updateByInstanceId(@Param(value = "status") String status, @Param(value = "updateTime") Date updateTime,
        @Param(value = "processInstanceId") String processInstanceId);
    
    CsmFlowTask queryByProcessInstanceId(String processInstanceId);
}
