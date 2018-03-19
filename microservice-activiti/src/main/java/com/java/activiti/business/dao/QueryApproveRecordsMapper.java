package com.java.activiti.business.dao;

import com.java.activiti.business.entity.mybatis.QueryApproveRecordsEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lu.xu on 2018/2/4.
 * TODO:查询审批记录
 */
public interface QueryApproveRecordsMapper {
    /**
     * 查询审批记录
     * @param processInstanceId 流程实例名称
     * @return
     */
    List<QueryApproveRecordsEntity> queryApproveRecords(@Param("processInstanceId") String processInstanceId);
}
