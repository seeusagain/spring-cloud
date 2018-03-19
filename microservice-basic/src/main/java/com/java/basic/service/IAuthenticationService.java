package com.java.basic.service;

import com.java.dto.ResultMsg;

/**
 *
 */
public interface IAuthenticationService {

    /**
     * 登录，给redis内放入访问token
     *
     * @param userName
     * @param password
     * @return
     */
    ResultMsg login(String userName, String password);

    /**
     * 退出，清理token
     *
     * @param accessToken
     * @return
     */
    ResultMsg logout(String accessToken);
}
