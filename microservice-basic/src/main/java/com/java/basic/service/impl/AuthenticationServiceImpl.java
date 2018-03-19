package com.java.basic.service.impl;

import com.java.basic.service.IAuthenticationService;
import com.java.dto.ResultMsg;
import com.java.utils.IdGeneratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Created by lu.xu on 2018/3/16.
 * TODO:
 */
@Service
public class AuthenticationServiceImpl implements IAuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResultMsg login(String userName, String password) {
        logger.info("user login ;userName：{}，password:{}", userName, password);
        //过程省略..
        String accessToken = IdGeneratorUtils.getSerialNo();
        stringRedisTemplate.opsForValue().set(accessToken, userName, 60 * 10, TimeUnit.SECONDS);
        return new ResultMsg(true, "登录成功,accessToken：" + accessToken);
    }

    @Override
    public ResultMsg logout(String accessToken) {
        logger.info("user logout ;accessToken：", accessToken);
        stringRedisTemplate.delete(accessToken);
        return new ResultMsg(true, "登出成功! ");
    }
}
