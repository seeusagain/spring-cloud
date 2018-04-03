package com.java.exampleSharding.dao;

import java.util.List;

import com.java.exampleSharding.entity.mybatis.GoodsInfoQuery;
import org.apache.ibatis.annotations.Param;

/**
 * Created by lu.xu on 2018/3/19.
 * TODO:
 */
public interface GoodsInfoQueryMapper {
    /**
     * 查询列表
     * @param goodsId
     * @param goodsName
     * @return
     */
    List<GoodsInfoQuery> queryGoodsList(@Param("goodsId") long goodsId, @Param("goodsName") String goodsName);
    
    /**
     * 分页查询
     * @return
     */
    List<GoodsInfoQuery> queryGoodsForPage();
    
    /**
     * 分页排序查询
     * @return
     */
    List<GoodsInfoQuery> queryGoodsOrderByForPage();
    
}
