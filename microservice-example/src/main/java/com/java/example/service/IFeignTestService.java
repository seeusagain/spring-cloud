package com.java.example.service;

/**
 * Created by lu.xu on 2018/3/17.
 * TODO:
 */
public interface IFeignTestService {
    Object feignRemoteTest(String name,String pwd);
}
