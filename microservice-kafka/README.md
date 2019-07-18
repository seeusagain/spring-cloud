# spring boot kafka
- 注意，如果向kafka发送消息未成功但是不报错，请开启DEBUG日志查看kafka日志
- service.kafkaTest 是java客户端直接使用kafka的例子，可以直接run main方法实现发送和接收
- service.kafkaTestTemplate是通过kafka与spring boot集成，通过使用kafkaTemplate实现发送和接收

