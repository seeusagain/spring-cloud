package com.java;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.java.dynamicDataSource.dao")
public class ApplicationDynamicDataSource {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationDynamicDataSource.class, args);
    }
}

/**
 * 不使用内置tomcat运行，需SpringBootServletInitializer加载
 */
//public class ApplicationDynamicDataSource extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationDynamicDataSource.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(ApplicationDynamicDataSource.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationDynamicDataSource.class, args);
//    }
//}
