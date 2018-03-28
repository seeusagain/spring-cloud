# 测试代码组件 
- 不使用用junit测试，而是通过swagger UI
- zuul不提供静态资源转发，所以通过访问组件地址访问swagger
- swagger URL
http://localhost:8686/microservice-example/swagger-ui.html

## 本demo测试以下功能
- feign远程调用其他组件
- 熔断测试
- 降级测试
- spring事务