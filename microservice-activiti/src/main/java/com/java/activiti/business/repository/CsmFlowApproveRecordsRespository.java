package com.java.activiti.business.repository;

import java.util.List;

import com.java.activiti.business.entity.jpa.CsmFlowApproveRecords;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * todo:自定义审批记录表
 */
public interface CsmFlowApproveRecordsRespository extends JpaRepository<CsmFlowApproveRecords, String> {
    
}
