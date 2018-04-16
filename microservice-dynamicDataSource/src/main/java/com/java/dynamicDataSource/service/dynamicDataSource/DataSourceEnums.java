package com.java.dynamicDataSource.service.dynamicDataSource;

/**
 * Created by lu.xu on 2017/7/24.
 * TODO:
 */
public enum DataSourceEnums {
    DEFAULT_DATASOURCE_KEY("defaultDatasource", "默认的数据源-mysql"), ORACLEDATASOURCE_KEY("oracleDatasource", "Oracle数据源");
    
    private String code;
    
    private String name;
    
    DataSourceEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getName() {
        return name;
    }
}
