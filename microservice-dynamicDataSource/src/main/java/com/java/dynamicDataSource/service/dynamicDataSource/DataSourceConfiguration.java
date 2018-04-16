package com.java.dynamicDataSource.service.dynamicDataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lu.xu on 2018/4/2.
 * TODO: 多数据源配置-读取配置文件转换成数据源
 */
@Component
@Configuration
public class DataSourceConfiguration {
    
    @Bean("defaultDatasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource defaultDatasource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean("oracleDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.oracle_ds")
    public DataSource oracleDatasource() {
        return DataSourceBuilder.create().build();
    }
    
    /**
     *TODO:设置动态数据源的默认数据源、和可选数据源
     * @return
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicRoutingDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        //可选数据源列表
        dataSourceMap.put(DataSourceEnums.DEFAULT_DATASOURCE_KEY.getCode(), defaultDatasource());
        dataSourceMap.put(DataSourceEnums.ORACLEDATASOURCE_KEY.getCode(), oracleDatasource());
        //默认数据源
        dynamicRoutingDataSource.setDefaultTargetDataSource(defaultDatasource());
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);
        return dynamicRoutingDataSource;
    }
    
    /**
     * TODO:配置mybatis数据源-使用动态数据源，而不是默认数据源
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
    
}
