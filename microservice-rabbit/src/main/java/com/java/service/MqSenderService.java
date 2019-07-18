package com.java.service;

import com.java.conf.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xulu
 * @date 2019/7/18 9:52
 * @description: TODO
 */
@Component
public class MqSenderService {

  private static final Logger logger = LoggerFactory.getLogger(MqSenderService.class);
  @Autowired
  private AmqpTemplate rabbitTemplate;


  /**
   * 发送订单消息
   */
  public void sendOrderMsg(String msg) {
    this.rabbitTemplate
        .convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.ORDER_QUEUE_ROUTING_KEY, msg);
  }

  /**
   * 发送合同消息
   */
  public void sendContractMsg(String msg) {
    this.rabbitTemplate
        .convertAndSend(RabbitConfig.EXCHANGE_NAME, RabbitConfig.CONTRACT_QUEUE_ROUTING_KEY, msg);
  }
}
