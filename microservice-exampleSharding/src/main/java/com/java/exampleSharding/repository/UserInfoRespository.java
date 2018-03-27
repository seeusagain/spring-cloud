package com.java.exampleSharding.repository;

import java.util.List;

import com.java.exampleSharding.entity.jpa.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRespository extends JpaRepository<UserInfo, String> {
    
}
