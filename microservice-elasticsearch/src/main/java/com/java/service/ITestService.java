package com.java.service;

import com.java.dto.ResultMsg2;

/**
 * create by:xulu TODO:
 */
public interface ITestService {

  ResultMsg2 queryById(String esId);

  ResultMsg2 query(String userName, String contractNumber);

}
