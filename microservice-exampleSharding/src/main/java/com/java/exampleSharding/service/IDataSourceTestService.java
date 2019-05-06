package com.java.exampleSharding.service;

import com.java.dto.ResultMsg;

/**
 * Created by lu.xu on 2018/3/28. TODO:测试默认数据源以及数据源切换
 */
public interface IDataSourceTestService {

  /**
   * 测试默认数据源
   */
  ResultMsg defaultDataSourceTest();

  /**
   * 测试切换数据源
   */
  ResultMsg switchDataSourceTest();
}
