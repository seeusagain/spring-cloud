package com.java.activiti.business.repository;

import java.util.List;

import com.java.activiti.business.entity.jpa.CsmActAssigneeObject;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by lu.xu on 2017/9/11.
 * TODO:定义数据查询接口，继承JpaRepository 指明实体类和主键类型
 */
public interface CsmActAssigneeObjectRespository extends JpaRepository<CsmActAssigneeObject, String> {
    
    /**
     * @param assigneeId 节点代理配置ID
     * @return
     */
    List<CsmActAssigneeObject> queryByAssigneeId(String assigneeId);

    /**
     * 根据节点删除授权对象
     * @param assigneeId
     * @return
     */
    int deleteByAssigneeId(String assigneeId);
}
