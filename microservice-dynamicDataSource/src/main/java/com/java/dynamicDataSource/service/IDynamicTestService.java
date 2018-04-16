package com.java.dynamicDataSource.service;

import com.java.dto.ResultMsg;

/**
 * Created by lu.xu on 2018/4/4.
 * TODO:
 */
public interface IDynamicTestService {
    /**
     * 手动切换数据源
     * @return
     */
    ResultMsg manualChangeDataSource();
    
    /**
     * 使用注解自动切换数据源
     * @return
     */
    ResultMsg autoChangeDataSource();
    
}
