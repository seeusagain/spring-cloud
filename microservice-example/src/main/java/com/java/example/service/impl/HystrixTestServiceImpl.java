package com.java.example.service.impl;

import com.java.dto.ResultMsg;
import com.java.example.remoteInterface.microserverBasic.AuthenticationController;
import com.java.example.remoteInterface.microserverExample2.ApplicationController;
import com.java.example.service.IHystrixTestService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HystrixTestServiceImpl implements IHystrixTestService {

  private static final Logger logger = LoggerFactory.getLogger(HystrixTestServiceImpl.class);

  @Resource
  private AuthenticationController authenticationControllerRemote;

  @Resource
  private ApplicationController example2ApplicationControllerRemote;

  /**
   * hystrix当服务不可达时（sever停止或者 404路径），启用降级策略，调用本地方法 注意，配置文件开启： feign:     hystrix:     enabled: true
   */
  @Override
  public Object fallback() {
    logger.info("测试hystrix 降级");
    return this.authenticationControllerRemote.loginForFallback();
  }

  @Override
  public Object circuitBreakerTest() throws Exception {
    logger.info("测试hystrix 熔断开始");
    Runnable runnable = new Runnable() {
      @Override
      public void run() {
        //子线程调用具体方法测试
        try {
          doCircuitBreakerTest();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
    Thread t = new Thread(runnable);
    t.start();

    return ResultMsg.ok("测试成功，请看服务器日志..");
  }

  /**
   * 熔断实现 注意 1.我们在配置文件中配置hystrix线程池为5，默认是10：hystrix:threadpool:default:coreSize: 5
   * 2.配置hystrix的超时时间：timeoutInMilliseconds,让被调用的组件线程等待市场小于熔断市场，保证测试
   *
   *
   * 结果： 通过观察日志可以得出，当basic的借口睡眠3s情况下（小雨熔断配置6s） 前5个线程（线程池配置，并发量=线程访问时间*线程池）访问basic接口后处于等待状态，同时线程池未释放，
   * 接着后五个线程由于获取不到空闲线程，访问了降级方法。 降级方法访问5个后，此时触发熔断，报错了。 断路器开条件： 1.10s内请求失败数量达到20个，断路器开。
   * 2.出错百分比阈值，当达到此阈值后，开始短路。默认50%
   *
   * 资源隔离： 当basic调用处于断路情况下，调用了example2组件，发现可以正常调用，说明资源进行了隔离 同一个组件之间的调用会被熔断，但是不同的不会
   */
  private void doCircuitBreakerTest() throws Exception {
    logger.info("熔断测试子线程开始>>");
    logger.info("开始多线程调用basic 睡眠3s方法>>");
    //当循环完成，多线程调用后，线程池将会被用完，则触发熔断
    for (int i = 0; i < 20; i++) {
      Runnable runnable = new Runnable() {
        @Override
        public void run() {
          logger.info(Thread.currentThread().getName() + " 开始调用basic 睡眠3s方法");
          String str = authenticationControllerRemote.sleep();
          logger.info(Thread.currentThread().getName() + " 调用结果：" + str);
        }
      };
      Thread t = new Thread(runnable);
      t.start();
    }
    /**
     * 当处于熔断条件下，调用其他组件，观察不同的组件熔断是否会相互影响
     */
    Thread.sleep(1000);
    logger.info(Thread.currentThread().getName() + " 开始调用example2 方法");
    String str = this.example2ApplicationControllerRemote.test();
    logger.info(Thread.currentThread().getName() + " 调用结果：" + str);

  }

}
