package com.java.example.service.impl;

import com.java.dto.ResultMsg;
import com.java.example.remoteInterface.microserverBasic.AuthenticationController;
import com.java.example.service.IFeignTestService;
import com.java.utils.EmptyUtils;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lu.xu on 2018/3/17. TODO:
 */
@Service
public class FeignTestServiceImpl implements IFeignTestService {

  private static final Logger logger = LoggerFactory.getLogger(FeignTestServiceImpl.class);

  @Resource
  private AuthenticationController authenticationControllerRemote;

  @Override
  public Object feignRemoteTest(String name, String pwd) {
    logger.info("feign 远程测试");
    if (EmptyUtils.isAnyEmpty(name, pwd)) {
      return ResultMsg.error("请填写参数");
    }
    logger.info("feign 远程调用basic开始..");
    ResultMsg resultMsg = this.authenticationControllerRemote.login(name, pwd);
    logger.info("feign 远程调用basic结束..");
    return resultMsg;
  }
}
