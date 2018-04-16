#spring-boot mybatis 动态数据源
## 核心代码
```java
com.java.dynamicDataSource.service.dynamicDataSource
```  
通过重写mybatis的SqlSessionFactoryBean，使mybatis数据源来源为我们自定义的动态数据源；  
动态数据源方法中，使用threadlocal技术保存线程数据源，通过get方法获取线程数据源key，并提供put方法更改数据源key，以达到切换数据源目的。
系统中同时提供根据注解自动切换数据源方法，使用的是自定义注解+springAOP方式，拦截带有数据源标记的注解，通过AOP自动切换。  
- DataSourceConfiguration 配置数据源核心代码
- DynamicDataSource 动态数据源
- DynamicDataSourceHolder 数据源使用的ThreadLocal
- DataSource 自动切换_数据源注解
- DataSourceAOP 自动切换_AOP拦截器
- DataSourceEnums 数据源枚举类  
---
## 核心代码详解
- DataSourceConfiguration.java   
 *定义数据源*  
@ConfigurationProperties写配置文件中前缀即可
配置文件：application.yml、application-dataSources.yml
```java
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
```


    

## 使用方法

## 注意事项