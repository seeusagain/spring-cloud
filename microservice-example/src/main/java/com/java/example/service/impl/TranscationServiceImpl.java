package com.java.example.service.impl;

import com.java.dto.ResultMsg;
import com.java.example.entity.jpa.UserInfo;
import com.java.example.repository.UserInfoRespository;
import com.java.example.service.ITranscationService;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lu.xu on 2018/3/28. TODO:
 */
@Service
public class TranscationServiceImpl implements ITranscationService {

  private static final Logger logger = LoggerFactory.getLogger(TranscationServiceImpl.class);

  @Resource
  private UserInfoRespository userInfoRespository;

  private static long initDataCount = 0;

  @Override
  @Transactional
  public ResultMsg testRollback() {
    logger.info(">>测试事务回滚..");
    initDataCount = userInfoRespository.count();
    logger.info("初始数据量大小：{}", initDataCount);
    logger.info("开始添加数据...");
    for (int i = 0; i < 20; i++) {
      UserInfo userInfo = new UserInfo();
      userInfo.setUserId(Long.valueOf(i));
      userInfo.setName("testRollback_" + i);
      userInfo.setAge("testAge_" + i);
      userInfoRespository.save(userInfo);
    }
    logger.info("数据添加完毕...");
    long count2 = userInfoRespository.count();
    logger.info("添加后数据量大小：{}", count2);
    logger.info("主线程开始抛出异常,由子线程统计数据");
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        try {
          logger.info("统计线程启动，开始睡眠1s..");
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
        long count = userInfoRespository.count();
        logger.info("抛出异常后数据量大小数据量大小：{}", count);
        if (count == initDataCount) {
          logger.info("数据量一致，回滚成功");
        } else {
          logger.info("数据量不一致，回滚失败");
        }
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();
    throw new RuntimeException();
  }

}
