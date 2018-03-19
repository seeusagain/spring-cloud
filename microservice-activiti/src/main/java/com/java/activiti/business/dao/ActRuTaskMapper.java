package com.java.activiti.business.dao;

import com.java.activiti.business.entity.mybatis.ActRuTask;

import java.util.List;

public interface ActRuTaskMapper {
    ActRuTask selectByPrimaryKey(String id);
    
    List<ActRuTask> selectByProcInstId(String proceInstId);
    
}