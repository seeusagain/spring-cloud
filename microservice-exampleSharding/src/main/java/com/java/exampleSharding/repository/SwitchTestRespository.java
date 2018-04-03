package com.java.exampleSharding.repository;

import com.java.exampleSharding.entity.jpa.SwitchTest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.java.exampleSharding.entity.jpa.UserOrders;

public interface SwitchTestRespository extends JpaRepository<SwitchTest, String> {
    
}
