package com.java.service;

import com.java.dto.ResultMsg;

/**
 * create by:xulu TODO:
 */
public interface ITestService {

  ResultMsg queryById(String esId);

  ResultMsg query(String userName, String contractNumber);

}
