package com.java.test.service;

import com.java.dto.ResultMsg;
import com.java.test.entity.UserInfo;

/**
 * Created by lu.xu on 2018/7/2. TODO:用户认证
 */
public interface IAuthenticationService {

  ResultMsg login(UserInfo userInfo);
}
