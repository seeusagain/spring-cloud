package com.java.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java.constants.AuthenticationConstats;
import com.java.dto.LogonUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.java.utils.EmptyUtils;

/**
 * Created by lu.xu on 2018/7/2.
 * TODO:会话拦截
 */
public class SessionInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    
    private String contextPath;
    
    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        /**
         * 判断访问地址是否需要拦截
         */
        String accessPath = request.getRequestURL().toString();
        if (EmptyUtils.isEmpty(accessPath)) {
            logger.error("会话拦截器异常：访问地址为空");
            return false;
        } else if (EmptyUtils.isEmpty(contextPath)) {
            throw new RuntimeException("会话拦截器异常： contextPath config can not be null");
        }
        accessPath = accessPath.substring(accessPath.indexOf(contextPath) + contextPath.length());
        logger.info("访问：{}", accessPath);
        boolean isIgrone = SessioninterceptorJudgmentUtil.judgment(accessPath);
        if (isIgrone) {
            return isIgrone;
        }
        /**
         * 如果需要拦截，判断用户是否登录（目前只限制登录即可访问）
         * 如果用户没有登录，直接返回登录界面
         */
        LogonUser logonUser =
            (LogonUser) request.getSession().getAttribute(AuthenticationConstats.USER_INFO_SESSION_KEY);
        if (null == logonUser || EmptyUtils.isAnyEmpty(logonUser.getUserId(), logonUser.getDisplayName())) {
            response.sendRedirect(request.getContextPath() + AuthenticationConstats.AUTHENTICATION_LOGOUT_PAGE_URL);
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
        ModelAndView modelAndView) throws Exception {
        
    }
    
    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
        throws Exception {
        
    }
    
    public String getContextPath() {
        return contextPath;
    }
    
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }
    
    public SessionInterceptor(String contextPath) {
        this.contextPath = contextPath;
    }
    
    public SessionInterceptor() {
    }
}
