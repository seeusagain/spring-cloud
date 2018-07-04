package com.java.test.service.impl;

import com.java.dto.ResultMsg;
import com.java.test.service.IBusinessService;
import com.java.test.utils.AuthenticationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lu.xu on 2018/7/4.
 * TODO:
 */
@Service
public class BusinessServiceImpl implements IBusinessService {
    
    private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
    
    /**
     * 示例：在业务逻辑中获取登录用户
     * @return
     */
    @Override
    public ResultMsg doBusiness() {
        String userid = AuthenticationUtils.getThreadUserId();
        logger.info("service获取用户id：{}", userid);
        return new ResultMsg(true, "userid：" + userid);
    }
}
