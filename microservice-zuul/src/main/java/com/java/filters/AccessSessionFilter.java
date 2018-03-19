package com.java.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.constants.CommonConstant;
import com.java.constants.SystemMsgConstant;
import com.java.dto.ResultMsg;
import com.java.utils.EmptyUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * Created by lu.xu on 2017/12/12.
 * TODO: 会话拦截器
 */
@Component
public class AccessSessionFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(AccessSessionFilter.class);
    
    @Value("${zuul.ignore.url.login}")
    private String ignoreUrlLogin;
    
    @Value("${zuul.ignore.url.fileUpload}")
    private String ignoreUrlUpload;
    
    @Value("${user.token.keep-time}")
    private int userTokenKeepTime;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public String filterType() {
        /**前置过滤器*/
        return FilterConstants.PRE_TYPE;
    }
    
    @Override
    public int filterOrder() {
        /**优先级，数字越大，优先级越低*/
        return 0;
    }
    
    @Override
    public boolean shouldFilter() {
        /**是否执行该过滤器，renturn true代表需要过滤  */
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String accessPath = request.getRequestURL().toString();
        if (EmptyUtils.isNotEmpty(accessPath)) {
            if (accessPath.indexOf(ignoreUrlLogin) > 0) {
                return false;
            }
            if (accessPath.indexOf(ignoreUrlUpload) > 0) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        if (request.getCharacterEncoding() == null) {
            try {
                request.setCharacterEncoding("UTF-8");
            } catch (Exception e) {
                logger.error("zuul access session filter 设置编码异常");
                e.printStackTrace();
            }
        }
        /**
         * 请求的token认证
         */
        if (!checkToken(request)) {
            /**过滤该请求，不往下级服务去转发请求，到此结束  */
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.getResponse().setCharacterEncoding("UTF-8");
            ctx.getResponse().setContentType("text/html;charset=UTF-8");
            ctx.setResponseBody(msg);
            return null;
        }
        return null;
    }
    
    /**
     * 校验token 有效性
     *
     * @param request
     * @return true 校验成功，token有效；false失败
     */
    private boolean checkToken(HttpServletRequest request) {
        String accessToken = request.getParameter(CommonConstant.ACCESSTOKEN_PARAMETER_NAME);
        if (accessToken == null || EmptyUtils.isEmpty(accessToken)) {
            return false;
        }
        //redis 校验
        String userId = stringRedisTemplate.opsForValue().get(accessToken.toString());
        if (EmptyUtils.isNotEmpty(userId)) {
            //更新redis有效时间
            stringRedisTemplate.opsForValue().set(accessToken.toString(), userId, userTokenKeepTime, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }
    
    /**
     * 没有会话时访问提示，由resultMsg对象包装
     */
    private static String msg = SystemMsgConstant.ACCESS_DENIED_NO_SESSION;
    
    static {
        try {
            ObjectMapper mapper = new ObjectMapper();
            msg = mapper.writeValueAsString(new ResultMsg(false, SystemMsgConstant.ACCESS_DENIED_NO_SESSION));
        } catch (Exception e) {
            
        }
    }
    
}
