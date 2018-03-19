package com.java.activiti.business.repository;

import java.util.Date;

import javax.transaction.Transactional;

import com.java.activiti.business.entity.jpa.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.activiti.business.entity.jpa.CsmFlowTask;

public interface UserInfoRespository extends JpaRepository<UserInfo, String> {

}
