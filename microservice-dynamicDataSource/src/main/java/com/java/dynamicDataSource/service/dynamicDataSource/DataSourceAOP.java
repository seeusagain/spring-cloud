package com.java.dynamicDataSource.service.dynamicDataSource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;


/**
 * Created by lu.xu on 2017/7/20.
 * TODO:定义AOP切面以便拦截所有带@DataSource注解的方法或类
 * 取出注解的值作为数据源标识放到DynamicDataSourceHolder的线程变量中
 * 拦截优先级：方法注解>类注解
 */

@Component
@Aspect
public class DataSourceAOP {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceAOP.class);
    
    private static final String AOP_EXECUTION = "execution(* com.java.dynamicDataSource.service..*.*(..))";
    
    /**
     * 在方法执行之前拦截注解，修改当前线程的数据源
     * @param joinPoint
     * @throws Throwable
     */
    @Before(AOP_EXECUTION)
    public void invoke(JoinPoint joinPoint) throws Throwable {
        logger.info("--Dynamic DataSource AOP--");
        Object object = joinPoint.getTarget();
        DataSource annotation = null;
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        /**
         * 获取方法注解
         * 当方法注解为空时，获取类注解
         * 当最终得到的注解不为空，设置当前线程的数据源
         */
        annotation = AnnotationUtils.findAnnotation(method, DataSource.class);
        if (null == annotation) {
            //获取类注解
            annotation = AnnotationUtils.findAnnotation(object.getClass(), DataSource.class);
        }
        if (null != annotation) {
            logger.info("Target Class:" + object.getClass());
            logger.info("Target Method:" + method.getName());
            String dataSourceValue = annotation.value();
            if (null != dataSourceValue && dataSourceValue.length() > 0) {
                DynamicDataSourceHolder.setDataSource(dataSourceValue);
                logger.info(">>切换当前线程至数据源:" + dataSourceValue);
            }
        }
        
    }
    
    /**
     * 被拦截方法执行完毕后，将数据源改为默认数据源
     * @param joinPoint
     * @throws Throwable
     */
    @After(AOP_EXECUTION)
    public void invokeAfter(JoinPoint joinPoint) throws Throwable {
        DynamicDataSourceHolder.setDataSource(DataSourceEnums.DEFAULT_DATASOURCE_KEY.getCode());
    }

}