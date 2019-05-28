package com.java;

import com.java.dao.UserInfoMapper;
import com.java.entity.UserInfo;
import com.java.utils.IdGeneratorUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * create by:xulu TODO:测试mybatis-plus without mapper
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationMybatisPlus.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserInfoTest {

  @Autowired
  private UserInfoMapper userInfoMapper;

  @Test
  public void test() {
    //新增
    String id = IdGeneratorUtils.getSerialNo();
    UserInfo userInfo = new UserInfo();
    userInfo.setUserId(id);
    userInfo.setUserName("ahuanglikeyou");
    userInfo.setEmail("1234@qq.com");
    this.userInfoMapper.insert(userInfo);
    UserInfo userInfo1 = this.userInfoMapper.selectById(id);
    Assert.assertTrue(userInfo1.getUserId().equals(id));

    //修改
    String pwd = "123";
    userInfo1.setUserPwd(pwd);
    this.userInfoMapper.updateById(userInfo1);
    UserInfo userInfo2 = this.userInfoMapper.selectById(id);
    Assert.assertEquals(pwd, userInfo2.getUserPwd());

    //删除
    this.userInfoMapper.deleteById(id);
    UserInfo userInfo3 = this.userInfoMapper.selectById(id);
    Assert.assertNull(userInfo3);


  }

}
