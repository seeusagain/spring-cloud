package com.java.service.kafkaTest;

import com.java.constanst.KafkaConstanst;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author xulu
 * @date 2019/7/15 18:13
 * @description: TODO
 */
public class Consumer {

  public static void main(String[] args) {
    System.out.println("kafka消费启动");
    Properties props = new Properties();
    props.setProperty("bootstrap.servers", KafkaConstanst.KAFKA_BROKER_LIST);
    props.setProperty("group.id", KafkaConstanst.KAFKA_GROUP_ID);
    //关闭自动提交偏移量
    props.setProperty("enable.auto.commit", "false");
    props.setProperty("auto.offset.reset", "earliest");
    props.setProperty("heartbeat.interval.ms", "1000");
    props.put("buffer.memory", 33554432);
    props.setProperty("value.deserializer",
        "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("key.deserializer",
        "org.apache.kafka.common.serialization.StringDeserializer");
    org.apache.kafka.clients.consumer.Consumer<String, String> consumer = new KafkaConsumer<String, String>(
        props);
    //指定topic
    consumer.subscribe(Collections.singletonList(KafkaConstanst.KAFKA_TOPIC));
    while (true) {
      //接收消息，poll参数为连接超时时间
      ConsumerRecords<String, String> records = consumer.poll(1000);
      for (ConsumerRecord<String, String> record : records) {
        System.out.println("kafka消费：" + record.value());
        //手动提交偏移量
        consumer.commitAsync();
      }
    }

  }
}
