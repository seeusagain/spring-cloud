# spring-cloud
spring-cloud示例

## 组件说明：
- eureka  
注册中心
- zuul  
网关
- common  
基础包，被各个服务依赖，主要是公共类或工具类
- activiti  
工作流服务组件
- email  
邮件发送
- file  
文件上传下载
- basic  
测试组件，详见swagger界面
- example  
测试组件，详见swagger界面
- example2  
测试组件，详见swagger界面
- dynamicDataSource  
动态数据源
- exampleSharding  
数据库分片测试  

## 如果需要打WAR包:
spring boot 可以使用jar包运行，系统中maven打包默认是打jar；  
同时系统中配置了war包打包方式，如需打war包请按照以下步骤修改：

这里以microservice-eureka为例讲述  
-  修改程序启动类：ApplicationEureka.java  
将以下代码注释
```java
//public class ApplicationEureka {
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationEureka.class, args);
//    }
//}
```
开启以代码注释
```java
/**
 * 不使用内置tomcat运行（war包运行），需SpringBootServletInitializer加载
 */
public class ApplicationEureka extends SpringBootServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationEureka.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        logger.info(">> Eureka系统初始化...");
        return application.sources(ApplicationEureka.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationEureka.class, args);
    }
}
```
- 修改pom.xml  
修改打包方式
```
<packaging>jar</packaging>
改为
<packaging>war</packaging>
```
开启以下注释，告诉maven打war包时排除tomcat相关包
```
<!-- jar运行情况下注释，这里指定打war包的时候不再需要tomcat相关的包 -->
<!--<dependency>-->
<!--<groupId>org.springframework.boot</groupId>-->
<!--<artifactId>spring-boot-starter-tomcat</artifactId>-->
<!--<scope>provided</scope>-->
<!--</dependency>-->
```
开启以下注释，告诉maven打war包的时候告诉maven不需要web.xml
```
<!-- war包开启，maven打war包的时候告诉maven不需要web.xml,否刚会报找不到web.xml错误 -->
<!--<plugin>-->
<!--<groupId>org.apache.maven.plugins</groupId>-->
<!--<artifactId>maven-war-plugin</artifactId>-->
<!--<version>2.4</version>-->
<!--<configuration>-->
<!--<failOnMissingWebXml>false</failOnMissingWebXml>-->
<!--</configuration>-->
<!--</plugin>-->
```

然后就打包即可

    
