package com.java.example.service;

/**
 *
 */
public interface IHystrixTestService {
    /**
     * 降级测试
     * @return
     */
    Object fallback();
    
    /**
     * 熔断测试
     * @return
     */
    Object circuitBreakerTest() throws Exception;
}
