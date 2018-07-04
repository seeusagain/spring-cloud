# spring-boot 会话拦截器

## 此程序中代码是按照单体架构进行设计，如果在微服务架构中，稍作改动即可
- 实现web会话拦截器，对URL访问进行拦截  
    目前是拦截访问会话，判断有没有session，并设置了白名单
- 实现web会话拦截器，拦截访问参数  
    目前是拦截访问会话，并给访问线程的threadLocal变量设置用户信息  
    其目的是:可以在service层直接获取线程中的登录用户信息，而不是依靠controller传递  
- 自定义spring AOP拦截访问，校验参数  
    基于上面的threadLocal存储信息，自定义了注解@Authentication  
    使用AOP拦截注解，校验访问线程是否存储了相关信息，相当于是上面的threadLocal的内部校验机制  
    
## 测试  
- 主页  
    http://localhost:8888/microservice-intercepter/  
    首次访问会拦截，并跳转至登录界面  
    登录后可以在主页中进行业务测试，获取线程中的用户信息  
     
    
    