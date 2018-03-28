package com.java.example.service;

import com.java.dto.ResultMsg;

/**
 * Created by lu.xu on 2018/3/28.
 * TODO:
 */
public interface ITranscationService {
    /**
     * 测试事务回滚
     * @return
     */
    ResultMsg testRollback();
}
