package com.java.exampleSharding.service.impl;

import com.java.dto.ResultMsg;
import com.java.exampleSharding.dao.GoodsInfoQueryMapper;
import com.java.exampleSharding.dao.UserOrdersQueryMapper;
import com.java.exampleSharding.entity.jpa.GoodsInfo;
import com.java.exampleSharding.entity.jpa.GoodsInfoDetails;
import com.java.exampleSharding.entity.jpa.UserInfo;
import com.java.exampleSharding.entity.jpa.UserOrders;
import com.java.exampleSharding.entity.mybatis.GoodsInfoQuery;
import com.java.exampleSharding.repository.GoodsInfoDetailsRespository;
import com.java.exampleSharding.repository.GoodsInfoRespository;
import com.java.exampleSharding.repository.UserInfoRespository;
import com.java.exampleSharding.repository.UserOrdersRespository;
import com.java.exampleSharding.service.IShardingTestService;
import io.shardingjdbc.core.keygen.DefaultKeyGenerator;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by lu.xu on 2018/3/19. TODO:
 */
@Service
public class ShardingTestServiceImpl implements IShardingTestService {

  private static final Logger logger = LoggerFactory.getLogger(ShardingTestServiceImpl.class);

  @Resource
  private UserOrdersQueryMapper userOrdersQueryMapper;

  @Resource
  private GoodsInfoQueryMapper goodsInfoQueryMapper;

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
    return ResultMsg.ok("操作成功，请查看日志和DB");
  }

  @Override
  public List<GoodsInfoQuery> queryGoodsById(long goodsId) {
    return this.goodsInfoQueryMapper.queryGoodsList(goodsId, null);
  }

  @Override
  public List<GoodsInfoQuery> queryGoodsByName(String goodsName) {
    return this.goodsInfoQueryMapper.queryGoodsList(0, goodsName);
  }

  /**
   * 分页查询 根据结果可以看出，是路由到第一个db，然后查询
   */
  @Override
  public List<GoodsInfoQuery> queryGoodsForPage() {
    return this.goodsInfoQueryMapper.queryGoodsForPage();
  }

  /**
   * 分页排序查询 根据结果可以看出，路由到了各个DB并且返回了数据
   */
  @Override
  public List<GoodsInfoQuery> queryGoodsOrderByForPage() {
    return this.goodsInfoQueryMapper.queryGoodsOrderByForPage();
  }

  /**
   * 如果不使用柔性事务，也会自动包含弱XA事务支持，有以下几点说明： 完全支持非跨库事务，例如：仅分表，或分库但是路由的结果在单库中。
   * 完全支持因逻辑异常导致的跨库事务。例如：同一事务中，跨两个库更新。更新完毕后，抛出空指针，则两个库的内容都能回滚。 不支持因网络、硬件异常导致的跨库事务。例如：同一事务中，跨两个库更新，更新完毕后、未提交之前，第一个库死机，则只有第二个库数据提交。
   * doc: http://shardingjdbc.io/docs_cn/02-guide/transaction/
   *
   * TODO:本方法测试，同一事物中，跨库插入，抛异常，观察回滚情况
   */
  @Override
  @Transactional
  public ResultMsg transcationTest() throws Exception {
    logger.info(">>测试普通事务开始..");
    Runnable runnable0 = new Runnable() {
      @Override
      public void run() {
        /**
         * 由于线程不属于spring管控，所以线程可以不受本方法事务的控制
         * 目的是为了清空数据，而不受本次事务测试影响而回滚
         */
        deleteDataForTranscationTest();
      }
    };
    Thread thread0 = new Thread(runnable0);
    thread0.start();
    thread0.join();

    logger.info("开始添加数据...");
    List<GoodsInfo> goodsInfos = new ArrayList<>();
    List<GoodsInfoDetails> details = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      GoodsInfo goodsInfo = new GoodsInfo();
      goodsInfo.setGoodsId(Long.valueOf(i));
      goodsInfo.setName("goods_name_transcation_" + i);
      goodsInfos.add(goodsInfo);

      GoodsInfoDetails goodsInfoDetails = new GoodsInfoDetails();
      goodsInfoDetails.setGoodsId(goodsInfo.getGoodsId());
      goodsInfoDetails.setPrice((int) (Math.random() * 1000000));
      details.add(goodsInfoDetails);
    }
    goodsInfoRespository.save(goodsInfos);
    goodsInfoDetailsRespository.save(details);
    logger.info("数据添加完毕...");
    long goodsCount = goodsInfoRespository.count();
    long goodsDetailCount = goodsInfoDetailsRespository.count();
    logger.info("数据量大小：goods:{},goodsDetails:{}", goodsCount, goodsDetailCount);
    logger.info("主线程开始抛出异常,由子线程统计数据");
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        try {
          logger.info("统计线程启动，开始睡眠1s..");
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
        long goodsCount = goodsInfoRespository.count();
        long goodsDetailCount = goodsInfoDetailsRespository.count();
        logger.info("抛出异常后数据量大小：goods:{},goodsDetails:{}", goodsCount, goodsDetailCount);
      }
    };
    Thread thread = new Thread(runnable);
    thread.start();
    /**
     * 模拟程序出错
     */
    throw new NullPointerException();
    //        return null;
  }

  /**
   * 清理数据
   */
  private void deleteDataForTranscationTest() {
    logger.info("开始清理数据...");
    long goodsCount = goodsInfoRespository.count();
    long goodsDetailCount = goodsInfoDetailsRespository.count();
    logger.info("清理前数据量大小：goods:{},goodsDetails:{}", goodsCount, goodsDetailCount);
    goodsInfoRespository.deleteAll();
    goodsInfoDetailsRespository.deleteAll();
    goodsCount = goodsInfoRespository.count();
    goodsDetailCount = goodsInfoDetailsRespository.count();
    logger.info("清理后数据量大小：goods:{},goodsDetails:{}", goodsCount, goodsDetailCount);
  }
}
