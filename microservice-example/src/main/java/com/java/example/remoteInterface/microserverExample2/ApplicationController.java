package com.java.example.remoteInterface.microserverExample2;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * example2接口
 */

@FeignClient(name = "microservice-example2")
public interface ApplicationController {
    String workFlowContentPath = "/microservice-example2";
    
    /**
     * example2的测试接口
     * @return
     */
    @GetMapping(value = workFlowContentPath + "/example/test")
    String test();
    
}
