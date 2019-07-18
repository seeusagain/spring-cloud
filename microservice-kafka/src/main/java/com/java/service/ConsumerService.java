package com.java.service;

import com.java.constanst.KafkaConstanst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author xulu
 * @date 2019/6/6 16:58
 * @description: TODO
 */
@Component
public class ConsumerService {

  private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

  @KafkaListener(topics = {KafkaConstanst.KAFKA_TOPIC})
  public void processMessage(String content) {
    logger.info("kafka消息被消费：{}", content);
  }
}
