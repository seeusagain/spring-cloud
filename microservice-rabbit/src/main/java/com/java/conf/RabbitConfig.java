package com.java.conf;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

  /**
   * 交换机名称
   */
  public static final String EXCHANGE_NAME = "A_TO_B_EXCHANGE";

  /**
   * 订单队列名称
   */
  public static final String ORDER_QUEUE = "ORDER_QUEUE";
  /**
   * 订单队列routingkey
   */
  public static final String ORDER_QUEUE_ROUTING_KEY = "ORDER_QUEUE_ROUTING_KEY";


  /**
   * 合同队列名称
   */
  public static final String CONTRACT_QUEUE = "CONTRACT_QUEUE";
  /**
   * 合同队列routingkey
   */
  public static final String CONTRACT_QUEUE_ROUTING_KEY = "CONTRACT_QUEUE_ROUTING_KEY";


  @Bean
  public Queue orderQueue() {
    return new Queue(ORDER_QUEUE);
  }

  @Bean
  public Queue contractQueue() {
    return new Queue(CONTRACT_QUEUE);
  }

  @Bean
  TopicExchange exchange() {
    return new TopicExchange(EXCHANGE_NAME);
  }

  @Bean
  Binding bindingExchangeMessage(Queue orderQueue, TopicExchange exchange) {
    return BindingBuilder.bind(orderQueue).to(exchange).with(ORDER_QUEUE_ROUTING_KEY);
  }

  @Bean
  Binding bindingExchangeMessages(Queue contractQueue, TopicExchange exchange) {
    return BindingBuilder.bind(contractQueue).to(exchange).with(CONTRACT_QUEUE_ROUTING_KEY);
  }
}
