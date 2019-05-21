package com.java.filters;

import com.java.utils.EmptyUtils;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * create by:xulu TODO:
 */
@Service
public class WhilteListCheckService {


  private static Logger logger = LoggerFactory.getLogger(RequestHandleFilters.class);

  private static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


  private static HashMap<String, String> WHITE_LIST_MAP = new HashMap<>();

  public boolean check(String ip) {
    return EmptyUtils.isEmpty(ip) ? false : WHITE_LIST_MAP.containsKey(ip);
  }

}
