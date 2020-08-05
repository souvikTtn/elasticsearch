package com.elasticSearch.demo.repository.elasticRepository;

import com.elasticSearch.demo.index.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductIndexRepository extends ElasticsearchRepository<ProductIndex, String> {
}
