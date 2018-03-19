package com.java.example.remoteInterface.microserverBasic;

import com.java.dto.ResultMsg;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * basic接口
 */
@FeignClient(name = "microservice-basic", fallback = AuthenticationHystrixImpl.class)
public interface AuthenticationController {
    String workFlowContentPath = "/microservice-basic";
    
    /**
     * basic 远程登录接口
     *
     * @param userName
     * @param password
     * @return
     */
    @PostMapping(value = workFlowContentPath + "/authentication/login")
    ResultMsg login(@RequestParam(value = "userName") String userName,
        @RequestParam(value = "password") String password);
    
    /**
     * 降级，feign上加入： fallback = AuthenticationHystrixImpl.class，此处调用不存在的地址
     * @return
     */
    @PostMapping(value = workFlowContentPath + "/authentication/loginXXX")
    ResultMsg loginForFallback();
    
    /**
     * basic 睡眠3秒方法
     * @return
     */
    @GetMapping(value = workFlowContentPath + "/authentication/sleep")
    String sleep();
}
