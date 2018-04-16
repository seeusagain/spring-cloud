package com.java.dynamicDataSource.service.dynamicDataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by lu.xu on 2017/7/20.
 * TODO: 重写spring获取数据源方法，改为使用动态数据源
 * 注意：动态数据源不支持一个事物中使用多个数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDataSource();
    }
}
