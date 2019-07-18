package com.java.service;

import com.alibaba.fastjson.JSONObject;
import com.java.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * create by:xulu TODO:
 */
@Service
@Order(value = 1)
public class MainService implements ApplicationRunner {

  private static final Logger logger = LoggerFactory.getLogger(MainService.class);

  @Autowired
  private ProducerService producerService;

  @Override
  public void run(ApplicationArguments args) throws Exception {

    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          Thread.sleep(2000);
        } catch (Exception e) {
        }

        for (int i = 0; i < 5; i++) {
          UserDTO userDTO = new UserDTO(String.valueOf(i), "test", i * 10);
          producerService.sendMessage(JSONObject.toJSONString(userDTO));
        }
      }
    });
    thread.start();
  }
}
