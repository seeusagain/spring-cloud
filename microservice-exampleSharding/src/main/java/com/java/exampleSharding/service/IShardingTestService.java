package com.java.exampleSharding.service;

import com.java.dto.ResultMsg;
import com.java.exampleSharding.entity.jpa.GoodsInfo;
import com.java.exampleSharding.entity.mybatis.GoodsInfoQuery;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lu.xu on 2018/3/19.
 * TODO:
 */
public interface IShardingTestService {
    ResultMsg add();
    
    /**
     * 按照路由键查询
     * @param goodsId
     * @return
     */
    List<GoodsInfoQuery> queryGoodsById(long goodsId);
    
    /**
     * 不按照路由键查询
     * @param goodsName
     * @return
     */
    List<GoodsInfoQuery> queryGoodsByName(String goodsName);
    
    /**
     * 不根据分片键，分页查询
     * @return
     */
    List<GoodsInfoQuery> queryGoodsForPage();
    
    /**
     * 不根据分片键，分页查询，并排序
     * @return
     */
    List<GoodsInfoQuery> queryGoodsOrderByForPage();
    
    /**
     *分库事务测试
     * @return
     */
    ResultMsg transcationTest()throws  Exception;
}
