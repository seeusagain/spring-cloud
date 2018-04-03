package com.java.exampleSharding.entity.mybatis;

/**
 * Created by lu.xu on 2018/3/19.
 * TODO:
 */
public class GoodsInfoQuery {
    private long goodsId;
    
    private long goodsDetailId;
    
    private String goodsName;
    
    private int price;
    
    public long getGoodsId() {
        return goodsId;
    }
    
    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
    
    public long getGoodsDetailId() {
        return goodsDetailId;
    }
    
    public void setGoodsDetailId(long goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }
    
    public String getGoodsName() {
        return goodsName;
    }
    
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    
    public int getPrice() {
        return price;
    }
    
    public void setPrice(int price) {
        this.price = price;
    }
}
