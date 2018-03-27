package com.java.exampleSharding.repository;

import com.java.exampleSharding.entity.jpa.GoodsInfoDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import com.java.exampleSharding.entity.jpa.GoodsInfo;

public interface GoodsInfoDetailsRespository extends JpaRepository<GoodsInfoDetails, String> {
    
}
