#spring-boot 会话拦截器
- 实现web会话拦截器，目前实现的是拦截session中的用户信息
- 使用threadLocal+自定义注解+spring AOP实现从当前线程获取session信息，而不是controller传递