package com.java.activiti.business.repository;

import com.java.activiti.business.entity.jpa.CsmActAssignee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by lu.xu on 2017/9/11.
 * TODO:定义数据查询接口，继承JpaRepository 指明实体类和主键类型
 */
public interface CsmActAssigneeRespository extends JpaRepository<CsmActAssignee, String> {
    
    List<CsmActAssignee> queryByProcessKeyOrderByElementCode(String processKey);
    
    /**
     * TODO:根据流程编码、节点编码、受理标志(集合) 查询数据
     * @param processKey
     * @param elementCode
     * @param assigneeCode
     * @return
     */
    List<CsmActAssignee> queryByProcessKeyAndElementCodeAndAssigneeCodeIn(String processKey, String elementCode,
        List<String> assigneeCode);
    
    /**
     * TODO:根据流程编码、节点编码  查询数据
     * @param processKey
     * @param elementCode
     * @return
     */
    List<CsmActAssignee> queryByProcessKeyAndElementCode(String processKey, String elementCode);
    
    int countById(String id);
}
