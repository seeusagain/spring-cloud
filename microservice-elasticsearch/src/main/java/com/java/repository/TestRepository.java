package com.java.repository;

import com.java.dto.TestEs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * create by:xulu TODO:
 */
public interface TestRepository extends
    ElasticsearchRepository<TestEs, String> {

}
