package com.java.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.entity.UserInfo2;

public interface UserInfo2Mapper extends BaseMapper<UserInfo2> {


  UserInfo2 selectByPrimaryKey(String userId);

}