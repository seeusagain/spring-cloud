package com.java.basic.service;

import com.java.dto.ResultMsg;

/**
 *
 */
public interface IAuthenticationService {

  /**
   * 登录，给redis内放入访问token
   */
  ResultMsg login(String userName, String password);

  /**
   * 退出，清理token
   */
  ResultMsg logout(String accessToken);
}
