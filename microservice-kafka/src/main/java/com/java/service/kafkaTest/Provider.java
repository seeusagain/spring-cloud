package com.java.service.kafkaTest;

import com.alibaba.fastjson.JSONObject;
import com.java.constanst.KafkaConstanst;
import com.java.dto.UserDTO;
import com.java.utils.IdGeneratorUtils;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

/**
 * @author xulu
 * @date 2019/7/15 18:10
 * @description: TODO
 */
public class Provider {

  public static void main(String[] args) {

    Properties props = new Properties();
    props.put("bootstrap.servers", KafkaConstanst.KAFKA_BROKER_LIST);
    //判断是否成功，我们指定了“all”将会阻塞消息
//    props.put("acks", "all");
    props.put("retries", 0);
    props.put("batch.size", 0);
    //延迟1s，1s内数据会缓存进行发送
    props.put("linger.ms", 1);
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    Producer<String, String> producer = new KafkaProducer<String, String>(props);

    System.out.println("向kafka推送日志开始");

    UserDTO userDTO = new UserDTO(IdGeneratorUtils.getSerialNo(), "ahuang", 18);
    String msg = JSONObject.toJSONString(userDTO);
/**
 *  ProducerRecord有一个实现是ProducerRecord（topic,key,value);
 *  这个key的作用是为消息选择存储分区
 *  key可以为空，当指定key且不为空的时候，kafka是根据key的hash值与分区数取模来决定数据存储到那个分区
 */
    ProducerRecord<String, String> record = new ProducerRecord<String, String>(
        KafkaConstanst.KAFKA_TOPIC, msg);
    producer.send(record);
  }

}
