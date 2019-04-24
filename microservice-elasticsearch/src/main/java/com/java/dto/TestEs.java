package com.java.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
/**
 * indexName 索引库的名称，建议以项目的名称命名
 * type 建议以实体的名称命名
 */
@Document(indexName = "test_es_search", type = "contract_info", refreshInterval = "-1")
public class TestEs implements Serializable {

  @Id
  private String id;

  /**
   * JsonProperty ES字段与实体属性的映射关系
   */
  @JsonProperty("USER_NAME")
  private String userName;
  @JsonProperty("CONT_NUMBER")
  private String contNumber;
}
