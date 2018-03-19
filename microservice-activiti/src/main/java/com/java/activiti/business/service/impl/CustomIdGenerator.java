package com.java.activiti.business.service.impl;

import com.java.utils.IdGeneratorUtils;
import org.activiti.engine.impl.cfg.IdGenerator;

/**
 * Created by lu.xu on 2017/12/21.
 * TODO:自定义id生成器，用于替换activiti自动生成的ID
 */
public class CustomIdGenerator implements IdGenerator {

    @Override
    public String getNextId() {
        return IdGeneratorUtils.getSerialNo();
    }
}
