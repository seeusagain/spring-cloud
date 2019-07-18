package com.java;

import com.alibaba.fastjson.JSONObject;
import com.java.dto.ResultMsg;
import com.java.service.HbaseQueryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationHbase.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class HbaseTest {

  @Autowired
  private HbaseQueryService hbaseQueryService;

  @Test
  public void test() {
    String rowKey = "000000019780F44CCEF50B8B37C565FA";
    ResultMsg resultMsg = this.hbaseQueryService.query(rowKey);
    System.out.println(JSONObject.toJSONString(resultMsg));
  }

}

