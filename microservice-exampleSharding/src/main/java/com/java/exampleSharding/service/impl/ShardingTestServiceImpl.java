package com.java.exampleSharding.service.impl;

import com.java.dto.ResultMsg;
import com.java.exampleSharding.ctrl.ShardingTestController;
import com.java.exampleSharding.dao.UserOrdersQueryMapper;
import com.java.exampleSharding.entity.jpa.GoodsInfo;
import com.java.exampleSharding.entity.jpa.GoodsInfoDetails;
import com.java.exampleSharding.entity.jpa.UserInfo;
import com.java.exampleSharding.entity.jpa.UserOrders;
import com.java.exampleSharding.repository.GoodsInfoDetailsRespository;
import com.java.exampleSharding.repository.GoodsInfoRespository;
import com.java.exampleSharding.repository.UserInfoRespository;
import com.java.exampleSharding.repository.UserOrdersRespository;
import com.java.exampleSharding.service.IShardingTestService;
import com.java.utils.IdGeneratorUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import io.shardingjdbc.core.keygen.DefaultKeyGenerator;
import io.shardingjdbc.core.keygen.KeyGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu.xu on 2018/3/19.
 * TODO:
 */
@Service
public class ShardingTestServiceImpl implements IShardingTestService {
    
    private static final Logger logger = LoggerFactory.getLogger(ShardingTestServiceImpl.class);
    
    //    @Resource
    //    private UserOrdersQueryMapper userOrdersQueryMapper;
    
    @Resource
    private UserInfoRespository userInfoRespository;
    
    @Resource
    private UserOrdersRespository userOrdersRespository;
    
    @Resource
    private GoodsInfoRespository goodsInfoRespository;
    
    @Resource
    private GoodsInfoDetailsRespository goodsInfoDetailsRespository;
    
    @Override
    public ResultMsg add() {
        logger.info(">>分片数据添加开始..");
        
        logger.info("清除数据开始..");
        userInfoRespository.deleteAll();
        userOrdersRespository.deleteAll();
        goodsInfoRespository.deleteAll();
        goodsInfoDetailsRespository.deleteAll();
        logger.info("清除数据完成..");
        
        /**
         * GoodsInfo 主表不使用分布式主键
         * GoodsInfoDetails 使用分布式主键，外键使用GoodsInfo .id
         * 且两个表不使用系统表默认的路由规则，配置：algorithm-expression
         *
         * 分布式主键使用需要注意：
         *  1.对象的GeneratedValue 属性
         *  2.配置中指定：key-generator-column-name=id
         */
        
        List<GoodsInfo> goodsInfos = new ArrayList<>();
        List<GoodsInfoDetails> details = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setGoodsId(Long.valueOf(i));
            goodsInfo.setName("goods_name_" + i);
            goodsInfos.add(goodsInfo);
            
            GoodsInfoDetails goodsInfoDetails = new GoodsInfoDetails();
            goodsInfoDetails.setGoodsId(goodsInfo.getGoodsId());
            goodsInfoDetails.setPrice((int) (Math.random() * 1000000));
            details.add(goodsInfoDetails);
        }
        goodsInfoRespository.save(goodsInfos);
        goodsInfoDetailsRespository.save(details);
        
        /**
         * UserInfo、UserOrders 都使用分布式主键，且使用默认路由字段
         */

        List<UserOrders> orders = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(new DefaultKeyGenerator().generateKey().longValue());
            userInfo.setName("testName_" + i);
            userInfo.setAge("testAge_" + i);

            long userId = userInfoRespository.save(userInfo).getUserId();

            UserOrders userOrders = new UserOrders();
            userOrders.setUserId(userId);
            userOrders.setPrice((int) (Math.random() * 100));
            userOrders.setRemarks("remarks_" + i);
            orders.add(userOrders);
        }
        userOrdersRespository.save(orders);
        
        logger.info(">>分片数据添加完毕，请查看DB");
        return new ResultMsg(true, "操作成功，请查看日志和DB");
    }
}
