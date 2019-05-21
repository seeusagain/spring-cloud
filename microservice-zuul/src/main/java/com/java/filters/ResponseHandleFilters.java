package com.java.filters;

import com.java.constant.SystemConstant;
import com.java.utils.EmptyUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

/**
 * create by:xulu TODO:
 */
@Component
public class ResponseHandleFilters extends ZuulFilter {

  private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private static Logger logger = LoggerFactory.getLogger(ResponseHandleFilters.class);

  @Override
  public String filterType() {
    /**后置过滤器*/
    return FilterConstants.POST_TYPE;
  }

  @Override
  public int filterOrder() {
    /**优先级，数字越大，优先级越低*/
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    /**是否执行该过滤器，renturn true代表需要过滤*/
    return true;
  }

  @Override
  public Object run() {
    try {
      RequestContext ctx = RequestContext.getCurrentContext();
      Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
      List<String> requestKeys = requestQueryParams.get(SystemConstant.REQUEST_ID_KEY);
      String requestKey = EmptyUtils.isEmpty(requestKeys) ? "" : requestKeys.get(0);

      InputStream stream = ctx.getResponseDataStream();
      String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
      ctx.setResponseDataStream(new ByteArrayInputStream(body.getBytes()));

      logger.info("类型:{} 日志时间:{}  访问编号:{} 返回参数:{}",
          "RESPONSE", sf.format(new Date()), requestKey, body);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}