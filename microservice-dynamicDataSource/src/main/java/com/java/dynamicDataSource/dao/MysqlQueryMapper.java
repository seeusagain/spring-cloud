package com.java.dynamicDataSource.dao;

import com.java.dynamicDataSource.entity.mybatis.MysqlQueryEntity;

import java.util.List;

public interface MysqlQueryMapper {

    List<MysqlQueryEntity> queryList();
    
}