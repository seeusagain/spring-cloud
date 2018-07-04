package com.java.test.service.impl;

import com.java.dto.LogonUser;
import com.java.test.entity.UserInfo;
import com.java.test.interceptor.AuthenticationHolder;
import com.java.test.service.IAuthenticationService;
import com.java.utils.IdGeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.java.dto.ResultMsg;
import com.java.utils.EmptyUtils;

/**
 * Created by lu.xu on 2018/7/2.
 * TODO:
 */
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);
    
    /**
     * 登录
     * @param userInfo
     * @return
     */
    @Override
    public ResultMsg login(UserInfo userInfo) {
        if (EmptyUtils.isAnyEmpty(userInfo.getUserId(), userInfo.getUserPwd())) {
            return new ResultMsg(false, "登录失败，用户名密码不能为空");
        }
        return this.dbLogin(userInfo.getUserId(), userInfo.getUserPwd());
    }
    
    private ResultMsg dbLogin(String userId, String password) {
        logger.info("用户登录：{}", userId);
        //模拟个用户，不访问DB
        LogonUser logonUser = new LogonUser();
        logonUser.setUserId(userId);
        logonUser.setDisplayName("临时用户" + IdGeneratorUtils.getSerialNo());
        AuthenticationHolder.setUser(logonUser);
        return new ResultMsg(true, "登录成功");
    }
}
