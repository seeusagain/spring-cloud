package com.java.service;

import com.java.constanst.KafkaConstanst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author xulu
 * @date 2019/6/6 16:57
 * @description: TODO
 */
@Component
public class ProducerService {

  private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

  @Autowired
  private KafkaTemplate<Integer, String> kafkaTemplate;


  public void sendMessage(String jsonData) {
    logger.info("向kafka发送数据：{}", jsonData);
    ListenableFuture<SendResult<Integer, String>> future = kafkaTemplate
        .send(KafkaConstanst.KAFKA_TOPIC, jsonData);
    future.addCallback(new ListenableFutureCallback<SendResult<Integer, String>>() {
      @Override
      public void onFailure(Throwable ex) {
        logger
            .error("向kafka发送数据-异常, ex = {}, topic = {}, data = {}", ex, KafkaConstanst.KAFKA_TOPIC,
                jsonData);
      }

      @Override
      public void onSuccess(SendResult<Integer, String> result) {
        logger.info("向kafka发送数据-成功 topic = {}, data = {}", KafkaConstanst.KAFKA_TOPIC, jsonData);
      }
    });
    logger.info("kafka sendMessage end");
  }
}
