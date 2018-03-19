package com.java;

import com.java.activiti.business.service.impl.CustomIdGenerator;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu.xu on 2017/9/28.
 * TODO:activiti 配置
 */
@Configuration
public class ActivitiConfig extends AbstractProcessEngineAutoConfiguration {
    /**
     * 注入数据源
     */
    @Resource
    DataSource activitiDataSource;

    /**
     * 注入配置好的事物管理器
     */
    @Resource
    PlatformTransactionManager activitiTransactionManager;


    /**
     * 配置数据源
     *
     * @param springAsyncExecutor
     * @return
     * @throws IOException
     */
    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration(SpringAsyncExecutor springAsyncExecutor)
            throws IOException {
        return this
                .baseSpringProcessEngineConfiguration(activitiDataSource, activitiTransactionManager, springAsyncExecutor);
    }

    /**
     * 配置全局监听器
     *
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration configuration) throws Exception {
        configuration.setActivityFontName("宋体");
        configuration.setLabelFontName("宋体");
        List<ActivitiEventListener> eventListeners = new ArrayList<ActivitiEventListener>();
        configuration.setEventListeners(eventListeners);
        configuration.setIdGenerator(new CustomIdGenerator());
        return super.springProcessEngineBean(configuration);
    }
}