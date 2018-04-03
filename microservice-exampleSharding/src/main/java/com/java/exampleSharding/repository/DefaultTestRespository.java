package com.java.exampleSharding.repository;

import com.java.exampleSharding.entity.jpa.DefaultTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.java.exampleSharding.entity.jpa.UserOrders;

public interface DefaultTestRespository extends JpaRepository<DefaultTest, String> {
    
}
