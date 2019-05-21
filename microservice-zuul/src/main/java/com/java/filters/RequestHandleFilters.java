package com.java.filters;

import com.java.constant.SystemConstant;
import com.java.utils.HttpRequestUtil;
import com.java.utils.IdGeneratorUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

/**
 * create by:xulu TODO:
 */
@Component
public class RequestHandleFilters extends ZuulFilter {


  private static Logger logger = LoggerFactory.getLogger(RequestHandleFilters.class);

  private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Autowired
  private WhilteListCheckService whilteListCheckService;

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
    /**是否执行该过滤器，renturn true代表需要过滤 ，此处可编写业务判断是否需要过滤*/
    return true;
  }

  @Override
  public Object run() {
    final String request_key = IdGeneratorUtils.getSerialNo();
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    String ip = HttpRequestUtil.getRemortIP(request);
    String accessPath = request.getRequestURL().toString();
    String paramStr = "";
    Enumeration enumeration = request.getParameterNames();
    while (enumeration.hasMoreElements()) {
      String paramName = (String) enumeration.nextElement();
      String paramValue = request.getParameter(paramName);
      paramStr += "," + paramName + "=" + paramValue;
    }
    paramStr = paramStr.replaceFirst(",", "");

    /**校验IP*/
    boolean check = this.whilteListCheckService.check(ip);

    if (!check) {
      /**过滤该请求，不往下级服务去转发请求，到此结束  */
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(401);
      ctx.getResponse().setCharacterEncoding("UTF-8");
      ctx.getResponse().setContentType("text/html;charset=UTF-8");
      ctx.setResponseBody("访问IP未在白名单中");
    } else {
      /**替换参数*/
      try {
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (requestQueryParams == null) {
          requestQueryParams = new HashMap<>();
        }
        ArrayList<String> list = new ArrayList<>();
        list.add(request_key);
        requestQueryParams.put(SystemConstant.REQUEST_ID_KEY, list);
        ctx.setRequestQueryParams(requestQueryParams);
        /**设置编码集*/
        request.setCharacterEncoding("UTF-8");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    logger.info("类型:{} 访问IP:{} 校验结果:{} 日志时间:{} 访问路径:{} 访问参数:{} 访问编号:{}",
        "REQUEST",
        ip, check, sf.format(new Date()),
        accessPath, paramStr, request_key);
    return null;
  }


}
