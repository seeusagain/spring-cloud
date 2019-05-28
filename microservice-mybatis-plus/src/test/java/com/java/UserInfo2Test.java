package com.java;

import com.java.dao.UserInfo2Mapper;
import com.java.entity.UserInfo2;
import com.java.utils.IdGeneratorUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * create by:xulu TODO:测试mybatis-plus+自定义mapper
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationMybatisPlus.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserInfo2Test {

  @Autowired
  private UserInfo2Mapper userInfo2Mapper;

  @Test
  public void test() {
    String id = IdGeneratorUtils.getSerialNo();
    UserInfo2 userInfo = new UserInfo2();
    userInfo.setUserId(id);
    userInfo.setUserName("ahuanglikeyou");
    userInfo.setEmail("1234@qq.com");
    this.userInfo2Mapper.insert(userInfo);
    //使用mapper自定义查询sql
    UserInfo2 userInfo1 = this.userInfo2Mapper.selectByPrimaryKey(id);
    Assert.assertTrue(userInfo1.getUserId().equals(id));

    //删除
    this.userInfo2Mapper.deleteById(id);
    UserInfo2 userInfo2 = this.userInfo2Mapper.selectById(id);
    Assert.assertNull(userInfo2);
  }

}

