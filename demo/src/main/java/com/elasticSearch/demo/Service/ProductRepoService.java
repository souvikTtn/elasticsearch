package com.elasticSearch.demo.Service;

import com.elasticSearch.demo.component.ElasticSearchProperties;
import com.elasticSearch.demo.dto.ProductEsDTO;
import com.elasticSearch.demo.dto.ProductEsSearchDTO;
import com.elasticSearch.demo.repository.ProductRepository;
import com.elasticSearch.demo.util.Utils;
import org.apache.commons.collections4.CollectionUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductRepoService {
    @Autowired
    @Qualifier("client")
    private RestHighLevelClient client;

    @Autowired
    private ElasticSearchProperties config;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticSearchRestTemplate;


    public List<ProductEsDTO> getProductsByTags(ProductEsSearchDTO productEsSearchDTO) {
        List<ProductEsDTO> products = new ArrayList<>();
        List<String> tags = productEsSearchDTO.getTags();
        if (CollectionUtils.isNotEmpty(tags)) {
            products = getResponseFromQuery(QueryBuilders.termsQuery("tags.keyword", tags), null, null, "products",
                                            ProductEsDTO.class);
        }
        return products;
    }

    public List<ProductEsDTO> getProductsByDateBetween(ProductEsSearchDTO productEsSearchDTO) {
        List<ProductEsDTO> products;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String from = simpleDateFormat.format(productEsSearchDTO.getFromDate());
        String to = simpleDateFormat.format(productEsSearchDTO.getToDate());
        QueryBuilder query = QueryBuilders.rangeQuery("created")
                                          .format("dd-MM-yyyy")
                                          .from(from)
                                          .to(to);
        products = getResponseFromQuery(query, null, null, "products",
                                        ProductEsDTO.class);
        return products;
    }

    public List<ProductEsDTO> getProductsByIds(ProductEsSearchDTO productEsSearchDTO) {
        List<ProductEsDTO> products = new ArrayList<>();
        List<String> ids = productEsSearchDTO.getIds();
        if (CollectionUtils.isNotEmpty(ids)) {
            products = getResponseFromQuery(QueryBuilders.idsQuery().addIds(ids.toArray(new String[ids.size()])), null,
                                            null,
                                            "products",
                                            ProductEsDTO.class);
        }
        return products;
    }

    public <T> List<T> getResponseFromQuery(QueryBuilder query, Integer from, Integer size, String indexName,
                                            Class<T> tClass) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(query);
        SearchRequest searchRequest = new SearchRequest(indexName);
        if (Objects.nonNull(from) && Objects.nonNull(size)) {
            searchSourceBuilder.from(from).size(size);
        }
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            List<T> list = new ArrayList<>();
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit searchHit : searchHits) {
                T t = Utils.getObjectFromString(tClass, searchHit.getSourceAsString());
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException("some error occurred while getting the result");
        }
    }
}
