package com.java.service;

import com.java.dto.ResultMsg2;
import com.java.dto.TestEs;
import com.java.repository.TestRepository;
import com.java.utils.EmptyUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

/**
 * create by:xulu TODO:
 */
@Service
public class TestServiceImpl implements ITestService {

  @Autowired
  private TestRepository testRepository;

  @Override
  public ResultMsg2 queryById(String esId) {
    TestEs testEs = this.testRepository.findOne(esId);
    return ResultMsg2.ok("查询成功", testEs);
  }

  @Override
  public ResultMsg2 query(String userName, String contractNumber) {
    if (EmptyUtils.isAllEmpty(userName, contractNumber)) {
      throw new RuntimeException("查询属性必须要有");
    }
    FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery();
    if (EmptyUtils.isNotEmpty(userName)) {
      functionScoreQueryBuilder
          .add(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("USER_NAME", userName)),
              ScoreFunctionBuilders.weightFactorFunction(1000));
    }
    if (EmptyUtils.isNotEmpty(contractNumber)) {
      functionScoreQueryBuilder.add(QueryBuilders.boolQuery()
              .should(QueryBuilders.matchQuery("CONTRACT_NUMBER", contractNumber)),
          ScoreFunctionBuilders.weightFactorFunction(1000));
    }

    SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(functionScoreQueryBuilder)
        .build();
    Page<TestEs> searchPageResults = testRepository.search(searchQuery);
    return ResultMsg2.ok("查询成功", searchPageResults);
  }
}
