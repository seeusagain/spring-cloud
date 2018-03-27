package com.java.exampleSharding.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by lu.xu on 2018/3/19.
 * TODO:
 */
public interface UserOrdersQueryMapper {
    /**
     * 根据用户id和备注查询 分片中的数据
     * @param userId
     * @param remarks
     * @return
     */
    List<UserOrdersQueryMapper> queryList(@Param("userId") String userId, @Param("remarks") String remarks);
}
