package com.java.dynamicDataSource.service.dynamicDataSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lu.xu on 2017/7/20.
 * TODO:自定义-切换数据源-注解
 * @DataSource既可以加在方法上也可以加在类上-优先级别：方法>实现类
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    String value();
}
