package com.java.activiti.business.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by lu.xu on 2018/2/2.
 * TODO:
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    
    private static ApplicationContext applicationContext = null;
    
    @Override
    
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }
    
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
    
    /**
     * 根据name获取bean
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
    
    /**
     * 通过class获取Bean.
      * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }
    
}
