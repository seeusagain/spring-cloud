package com.java;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@SpringBootApplication
@EnableCaching
@EnableDiscoveryClient
@EnableFeignClients
/**
 * 本地直接运行
 */
/**mybatis 扫描mapper接口文件*/
@MapperScan("com.java.operation.dao")
public class ApplicationExample {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationExample.class, args);
    }
}

/**
 * 不使用内置tomcat运行，需SpringBootServletInitializer加载
 */
//public class ApplicationExample  extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationExample.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> basicOperation apply 系统初始化...");
//        return application.sources(ApplicationExample.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationExample.class, args);
//    }
//}
