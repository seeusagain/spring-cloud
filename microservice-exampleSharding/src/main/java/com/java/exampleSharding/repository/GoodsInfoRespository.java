package com.java.exampleSharding.repository;

import com.java.exampleSharding.entity.jpa.GoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.java.exampleSharding.entity.jpa.UserInfo;

public interface GoodsInfoRespository extends JpaRepository<GoodsInfo, String> {
    
}
