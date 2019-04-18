//package com.java.email;
//
//import com.gy.CustomerLevelApplication;
//import com.gy.entity.CustomerLevel;
//import com.gy.service.ICustomerLevelService;
//import com.gy.util.HttpRequestUtil;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//import javax.annotation.Resource;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
///**
// * create by:xulu TODO:并发跑接口
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = CustomerLevelApplication.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//public class CustomerLeveInterfaceTest {
//
//  @Resource
//  private ICustomerLevelService customerLevelService;
//
//  @Test
//  public void testQeruy() throws Exception {
//    String url = "http://10.41.1.59:8080/vservice-qly-customer-level/customerLevel/queryLevel";
//
//    long startTimeAll = System.currentTimeMillis();
//    // 构造一个线程池
//    ThreadPoolExecutor threadPool = new ThreadPoolExecutor(60, 100, 3, TimeUnit.SECONDS,
//        new ArrayBlockingQueue<Runnable>(3),
//        new ThreadPoolExecutor.DiscardOldestPolicy());
//
//    List<CustomerLevel> customerLevels = customerLevelService.queryList(10000);
//    for(int i=0;i<=10000000;i++){
//      for (CustomerLevel customerLevel : customerLevels) {
//        threadPool.execute(new Runnable() {
//          @Override
//          public void run() {
//            long startTime = System.currentTimeMillis();
//            Map<String, String> map = new HashMap<>();
//            map.put("customerId", customerLevel.getCustomerId().toString());
//            String result = HttpRequestUtil.httpURLConnectionPOST(url, map);
//            System.out.println("查询耗时:" + (System.currentTimeMillis() - startTime) + "毫秒   查询结果：" + result );
//            System.out.println("");
//          }
//        });
//      }
//      if(threadPool.getTaskCount()>10000){
//        Thread.sleep(500);
//      }
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
