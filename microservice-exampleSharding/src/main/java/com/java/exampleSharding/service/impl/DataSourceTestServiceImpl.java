package com.java.exampleSharding.service.impl;

import com.java.dto.ResultMsg;
import com.java.exampleSharding.ctrl.DataSourceTestController;
import com.java.exampleSharding.service.IDataSourceTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lu.xu on 2018/3/28.
 * TODO:
 */
@Service
public class DataSourceTestServiceImpl implements IDataSourceTestService {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceTestServiceImpl.class);
    
    /**
     * 在yml中配置默认数据库： default-data-source-name: ds_0
     * @return
     */
    @Override
    public ResultMsg defaultDataSourceTest() {
        return null;
    }
    
    @Override
    public ResultMsg switchDataSourceTest() {
        return null;
    }
}
