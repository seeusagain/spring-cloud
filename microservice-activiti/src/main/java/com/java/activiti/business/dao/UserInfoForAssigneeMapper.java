package com.java.activiti.business.dao;

import com.java.activiti.business.entity.mybatis.UserInfoForAssigneeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lu.xu on 2017/12/28.
 * TODO:自定义查询受理人信息
 */
public interface UserInfoForAssigneeMapper {
    
    /**
     * 根据流程实例id，查询当前流程所属的用户
     * @param processInstanceId
     * @return
     */
    List<UserInfoForAssigneeEntity>
        queryUserInfoByprocessInstanceId(@Param("processInstanceId") String processInstanceId);
    
}
