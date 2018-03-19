# zuul 网关

## zuul拦截会话
- 拦截会话
http://localhost:8080/microservice-zuul/microservice-basic/microservice-basic/example/test
- 登录接口不拦截
http://localhost:8080/microservice-zuul/microservice-basic/microservice-basic/authentication/login  
由于服务器是post方法，所以报错，但是不拦截
- 通过swagger界面登录
http://localhost:8282/microservice-basic/swagger-ui.html#!/authentication-controller/loginUsingPOST
- 带token访问
http://localhost:8080/microservice-zuul/microservice-basic/microservice-basic/example/test?accessToken=201803171058020744016081100000

## 通过在注册中心注册的microserverName路由到微服务
http://localhost:8080/microservice-zuul/microservice-basic/microservice-basic/example/test?accessToken=201803171058020744016081100000
http://localhost:8080/microservice-zuul/microservice-example/microservice-example/example/test?accessToken=201803171058020744016081100000


