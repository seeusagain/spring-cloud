package com.java.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lu.xu on 2017/8/17.
 * TODO: 配置文件读取工具类
 */
public class PropertiesLoadUtil {

    public static Properties load(String fileName) throws Exception {
        if (EmptyUtils.isEmpty(fileName) || EmptyUtils.isTrimBlank(fileName)) {
            return null;
        }
        Properties properties = new Properties();
        InputStream is = PropertiesLoadUtil.class.getClassLoader().getResourceAsStream(fileName);
        properties.load(is);
        return properties;
    }

}
