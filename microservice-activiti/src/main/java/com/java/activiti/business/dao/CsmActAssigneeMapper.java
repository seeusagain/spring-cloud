package com.java.activiti.business.dao;

import com.java.activiti.business.entity.mybatis.CsmActAssigneeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CsmActAssigneeMapper {
    /**
     * 查询列表
     * @param keywords
     * @param start
     * @param end
     * @return
     */
    List<CsmActAssigneeEntity> queryDistinctList(@Param("keywords") String keywords, @Param("start") int start,
        @Param("end") int end);
    
    /**
     * 列表总数
     * @param keywords
     * @return
     */
    int countDistinctList(@Param("keywords") String keywords);
}