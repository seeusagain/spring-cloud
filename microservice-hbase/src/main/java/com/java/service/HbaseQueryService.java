package com.java.service;

import com.java.dto.ResultMsg;
import java.util.HashMap;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.stereotype.Component;

/**
 * @author xulu
 * @date 2019/7/18 9:52
 * @description: TODO
 */
@Component
public class HbaseQueryService {

  private static final Logger logger = LoggerFactory.getLogger(HbaseQueryService.class);


  @Autowired
  private HbaseTemplate hbaseTemplate;

  private static final String TABLE_NAME = "TABLE_NAME";

  /**
   * 根据rowkey查询
   */
  public ResultMsg query(String rowKey) {
    Result result = hbaseTemplate
        .execute(TABLE_NAME, new TableCallback<Result>() {
          @Override
          public Result doInTable(HTableInterface table) throws Throwable {
            Get get = new Get(rowKey.getBytes());
            return table.get(get);
          }
        });
    if (result == null || result.listCells() == null || result.listCells().size() == 0) {
      return ResultMsg.ok("查询成功");
    }
    HashMap<String, String> resutMap = new HashMap<>();
    for (Cell cell : result.listCells()) {
      String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
      String value = Bytes.toString(CellUtil.cloneValue(cell));
      resutMap.put(qualifier, value);
    }
    return ResultMsg.ok("查询成功", resutMap);
  }
}
