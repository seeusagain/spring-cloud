package com.java.example.repository;

import com.java.example.entity.jpa.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserInfoRespository extends JpaRepository<UserInfo, String> {
    
}
