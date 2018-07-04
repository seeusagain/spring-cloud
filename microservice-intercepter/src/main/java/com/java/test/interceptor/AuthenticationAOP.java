package com.java.test.interceptor;

import java.lang.reflect.Method;

import com.java.dto.LogonUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.java.utils.EmptyUtils;

/**
 * Created by lu.xu on 2018/7/3.
 * TODO:用户认证注解拦截器
 */

@Component
@Aspect
public class AuthenticationAOP {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationAOP.class);
    
    private static final String AOP_EXECUTION = "execution(* com.java.test.service..*.*(..))";
    
    /**
     * 在方法执行之前拦截注解，判断线程中是否有登录用户信息
     * @param joinPoint
     * @throws Throwable
     */
    @Before(AOP_EXECUTION)
    public void invoke(JoinPoint joinPoint) throws Throwable {
        logger.info("--Authentication AOP--");
        /**
         * 获取方法注解
         * 当方法注解为空时，获取类注解
         * 当最终得到的注解不为空，设置当前线程的数据源
         */
        Object object = joinPoint.getTarget();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Authentication authentication = null;
        authentication = AnnotationUtils.findAnnotation(method, Authentication.class);
        if (null == authentication) {
            authentication = AnnotationUtils.findAnnotation(object.getClass(), Authentication.class);
        }
        if (null != authentication) {
            LogonUser userInfo = AuthenticationHolder.getUser();
            if (null == userInfo || EmptyUtils.isEmpty(userInfo.getUserId())) {
                /**
                 * 此处需要自定义捕捉异常..现省略
                 */
                throw new RuntimeException("session user can not be null");
            }
        }
    }
}
