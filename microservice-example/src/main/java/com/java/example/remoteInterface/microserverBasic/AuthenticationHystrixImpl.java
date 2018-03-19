package com.java.example.remoteInterface.microserverBasic;

import com.java.dto.ResultMsg;
import org.springframework.stereotype.Service;

/**
 * Created by lu.xu on 2018/3/17.
 * TODO:远程登录方法的降级实现
 */
@Service
public class AuthenticationHystrixImpl implements AuthenticationController {
    @Override
    public ResultMsg login(String userName, String password) {
        return new ResultMsg(false, "microservice-basic is Unavailable");
    }
    
    /**
     * 降级接口
     * @return
     */
    @Override
    public ResultMsg loginForFallback() {
        return new ResultMsg(false, "loginForFallbackTest:microservice-basic is Unavailable");
    }
    
    @Override
    public String sleep() {
        return "microservice-basic is Unavailable ";
    }
}
