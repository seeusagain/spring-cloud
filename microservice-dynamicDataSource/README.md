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
**定义数据源**   
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
由于系统使用了枚举，所以这里的@Bean中的名称，要和DataSourceEnums.java中的保持一致，以免切换数据源的时候匹配不上。  
<br/>  
**定义动态数据源**
指定动态数据源的可选数据源列表，和默认数据源  
```java
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
```  
<br/>    
**啊**   

## 使用方法

## 注意事项