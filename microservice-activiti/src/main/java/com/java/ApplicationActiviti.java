package com.java;

import com.java.activiti.modeler.JsonpCallbackFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        /**
         * 注释掉以免调用activiti需要验证
         */
        org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.ManagementWebSecurityAutoConfiguration.class
})

@EnableAsync
@EnableFeignClients
/**mybatis 扫描mapper接口文件*/
@MapperScan("com.java.activiti.business.dao")
public class ApplicationActiviti {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationActiviti.class, args);
    }

    @Bean
    public JsonpCallbackFilter filter() {
        return new JsonpCallbackFilter();
    }
}


/**
 * 不使用内置tomcat运行，需SpringBootServletInitializer加载
 */
//public class ApplicationActiviti  extends SpringBootServletInitializer {
//    private static final Logger logger = LoggerFactory.getLogger(ApplicationActiviti.class);
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        logger.info(">> activiti系统初始化...");
//        return application.sources(ApplicationActiviti.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ApplicationActiviti.class, args);
//    }
//}
