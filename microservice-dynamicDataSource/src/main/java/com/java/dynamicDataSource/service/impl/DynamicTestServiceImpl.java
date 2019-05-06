package com.java.dynamicDataSource.service.impl;

import com.java.dto.ResultMsg;
import com.java.dynamicDataSource.dao.MysqlQueryMapper;
import com.java.dynamicDataSource.dao.OracleQueryMapper;
import com.java.dynamicDataSource.entity.mybatis.MysqlQueryEntity;
import com.java.dynamicDataSource.entity.mybatis.OracleQueryEntity;
import com.java.dynamicDataSource.service.IDynamicTestService;
import com.java.dynamicDataSource.service.dynamicDataSource.DataSource;
import com.java.dynamicDataSource.service.dynamicDataSource.DataSourceEnums;
import com.java.dynamicDataSource.service.dynamicDataSource.DynamicDataSourceHolder;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lu.xu on 2018/4/4. TODO:
 */
@Service
public class DynamicTestServiceImpl implements IDynamicTestService {

  private static final Logger logger = LoggerFactory.getLogger(DynamicTestServiceImpl.class);

  @Resource
  private MysqlQueryMapper mysqlQueryMapper;

  @Resource
  private OracleQueryMapper oracleQueryMapper;

  /**
   * 手动切换数据源，log 显示mybatis执行情况
   */
  @Override
  public ResultMsg manualChangeDataSource() {
    logger.info(">>手动切换数据源方法开始...");
    logger.info("使用默认数据源查询");
    List<MysqlQueryEntity> mysqlQueryEntities = this.mysqlQueryMapper.queryList();
    logger.info("手动切换到oracle数据源");
    DynamicDataSourceHolder.setDataSource(DataSourceEnums.ORACLEDATASOURCE_KEY.getCode());
    List<OracleQueryEntity> oracleQueryEntities = this.oracleQueryMapper.queryList();
    logger.info("手动切换到默认数据源");
    DynamicDataSourceHolder.setDataSource(DataSourceEnums.DEFAULT_DATASOURCE_KEY.getCode());
    List<MysqlQueryEntity> mysqlQueryEntities2 = this.mysqlQueryMapper.queryList();
    return   ResultMsg.ok( "请查看执行log");
  }

  /**
   * 自动切换数据源，使用注解
   */
  @Override
  @DataSource("oracleDatasource")
  public ResultMsg autoChangeDataSource() {
    logger.info("使用注解切换数据源开始>>");
    List<OracleQueryEntity> oracleQueryEntities = this.oracleQueryMapper.queryList();
    return ResultMsg.ok("请查看执行log");
  }

}
