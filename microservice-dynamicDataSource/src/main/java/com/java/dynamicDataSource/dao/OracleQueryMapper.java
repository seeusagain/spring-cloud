package com.java.dynamicDataSource.dao;

import com.java.dynamicDataSource.entity.mybatis.OracleQueryEntity;

import java.util.List;

/**
 * Created by lu.xu on 2018/4/2.
 * TODO:
 */
public interface OracleQueryMapper {

    List<OracleQueryEntity> queryList();
}
