package com.java.exampleSharding.repository;

import com.java.exampleSharding.entity.jpa.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrdersRespository extends JpaRepository<UserOrders, String> {
    
}
