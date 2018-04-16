#spring-boot mybatis 动态数据源
## 核心代码
```java
com.java.dynamicDataSource.service.dynamicDataSource
```  
通过重写mybatis的SqlSessionFactoryBean，使mybatis数据源来源为我们自定义的动态数据源；  
动态数据源方法中，使用threadlocal技术保存线程数据源，通过get方法获取线程数据源key，并提供put方法更改数据源key，以达到切换数据源目的。  
系统中同时提供根据注解自动切换数据源方法，使用的是自定义注解+springAOP方式，拦截带有数据源标记的注解，通过AOP自动切换。  
- DataSourceConfiguration.java 配置数据源核心代码
- DynamicDataSource.java 动态数据源
- DynamicDataSourceHolder.java 数据源使用的ThreadLocal
- DataSource.java 自动切换_数据源注解
- DataSourceAOP.java 自动切换_AOP拦截器
- DataSourceEnums.java 数据源枚举类  
---
## 核心代码详解
### 手动切换数据源实现
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
  
**更改mybatis使用数据源为动态数据源**  
```java
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
```
**配置事务**  
```java
  @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
```    
- 动态数据源+ThreadLocal  
DynamicDataSource.java作为动态数据源，返回数据源是从DynamicDataSourceHolder.java中ThreadLocal中获取的。  
同时DynamicDataSourceHolder.java提供put方法更改当前线程的数据源。    
**其实写到这里，已经满足了手动切换数据源，使用方法：**
```java  
 业务代码... 
 DynamicDataSourceHolder.setDataSource(DataSourceEnums.ORACLEDATASOURCE_KEY.getCode());
 业务代码...
```   

### 使用注解自动切换数据源  
- 自定义注解DataSource.java
- 加入AOP方法：DataSource.java  
使用方法：在类或者方法上，加入@DataSource("datasourceName")注解即可  
注解优先级：方法>类  
****
## 使用方法
- 拷贝service层中的dynamicDataSource文件夹内文件至项目
- application中定义数据源
- 修改DataSourceConfiguration.java 中数据源配置
- 修改DataSourceEnums.java 中数据源枚举
- 修改AOP拦截切面包名  
- 业务代码中使用有两种方式
手动切换： DynamicDataSourceHolder.setDataSource(DataSourceEnums.ORACLEDATASOURCE_KEY.getCode());  
注解切换：@DataSource("datasourceName")

## 注意事项
由于spring不建议AOP拦截方法内调用的方法，所以在使用注解时候这样写是无效的
```java  
    public void changeDataSource(){
        //使用默认数据源
        List<MysqlQueryEntity> mysqlQueryEntities = this.mysqlQueryMapper.queryList();
        this.changeDataSourceToOracle();
    }
    
    @DataSource("oracleDatasource")
    public void changeDataSourceToOracle() {
        //使用Oracle数据源
        List<OracleQueryEntity> oracleQueryEntities = this.oracleQueryMapper.queryList();
    }.
```   
遇到这样的需求，建议使用手动切换数据源方法  

## 测试代码
swagger: http://10.83.14.187:8888/microservice-dynamicDataSource/swagger-ui.html  
java: IDynamicTestService.java  

