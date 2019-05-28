package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
/**
 * 本地jar直接运行
 */
public class ApplicationEureka {

  public static void main(String[] args) {
    SpringApplication.run(ApplicationEureka.class, args);

    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println("----microservice-eureka started-----");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
  }
}

/**
 * 不使用内置tomcat运行（war包运行），需SpringBootServletInitializer加载
 */
//public class ApplicationEureka extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationEureka.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> Eureka系统初始化...");
//        return application.sources(ApplicationEureka.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationEureka.class, args);
//    }
//}
