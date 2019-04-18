//package com.java.email;
//
//import com.gy.dto.ResultMsg;
//import com.gy.entity.CustomerLevel;
//import com.gy.service.ICustomerLevelService;
//import java.util.List;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import javax.annotation.Resource;
//import org.junit.Test;
//
///**
// * create by:xulu TODO:
// */
////@RunWith(SpringJUnit4ClassRunner.class)
////@SpringBootTest(classes = CustomerLevelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//public class CustomerLeveTest {
//
//  @Resource
//  private ICustomerLevelService customerLevelService;
//
//
//  @Test
//  public void testQeruy() throws Exception {
//
//    long startTimeAll = System.currentTimeMillis();
//    // 构造一个线程池
//    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(20, 100, 3, TimeUnit.SECONDS,
//        new ArrayBlockingQueue<Runnable>(3),
//        new ThreadPoolExecutor.DiscardOldestPolicy());
//
//    List<CustomerLevel> customerLevels = customerLevelService.queryList(100);
//    for (CustomerLevel customerLevel : customerLevels) {
//      threadPool.execute(new Runnable() {
//        @Override
//        public void run() {
//          long startTime = System.currentTimeMillis();
//          ResultMsg resultMsg = customerLevelService
//              .queryLevel(customerLevel.getCustomerId(), null, null, null);
//          System.out.println("查询耗时:" + (System.currentTimeMillis() - startTime) + "毫秒");
//        }
//      });
//    }
//
//    threadPool.shutdown();
//    while (true) {
//      if (threadPool.isTerminated()) {
//        System.out
//            .println("线程池执行完毕！查询总耗时：" + (System.currentTimeMillis() - startTimeAll) / 1000 + "秒");
//        break;
//      }
//      Thread.sleep(200);
//    }
//  }
//
//}
