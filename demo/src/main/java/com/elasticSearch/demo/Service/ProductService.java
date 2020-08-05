package com.elasticSearch.demo.Service;

import com.elasticSearch.demo.component.ElasticSearchProperties;
import com.elasticSearch.demo.dto.ProductEsDTO;
import com.elasticSearch.demo.dto.ProductEsSearchDTO;
import com.elasticSearch.demo.entity.Product;
import com.elasticSearch.demo.index.ProductIndex;
import com.elasticSearch.demo.repository.ProductRepository;
import com.elasticSearch.demo.repository.elasticRepository.ProductIndexRepository;
import com.elasticSearch.demo.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductIndexRepository productIndexRepository;
    @Autowired
    @Qualifier("client")
    private RestHighLevelClient client;
    @Autowired
    private ElasticSearchProperties config;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticSearchRestTemplate;
    @Autowired
    private ProductRepoService productRepoService;

    public Boolean createProductIndexForElasticSearch()
    throws IOException {


        CreateIndexRequest request = new CreateIndexRequest("products");
        request.settings(Settings.builder()
                                 .put("index.number_of_shards", config.getEsNumberOfShards())
                                 .put("index.number_of_replicas", config.getEsNumberOfReplicas())
        );

        if (!client.indices().exists(new GetIndexRequest("products"), RequestOptions.DEFAULT)) {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        }
        //index already created
        else {
            return false;
        }
    }


    public Boolean createProductIndexForElasticSearchUsingTemplate() {
        Settings settings = Settings.builder()
                                    .put("index.number_of_shards", config.getEsNumberOfShards())
                                    .put("index.number_of_replicas", config.getEsNumberOfReplicas()).build();
        return elasticSearchRestTemplate.createIndex("products", settings);
    }

    public Boolean deleteIndex() {
        return elasticSearchRestTemplate.deleteIndex("products");
    }

    public void saveProduct(Product product)
    throws JsonProcessingException {
        Product savedProduct = productRepository.save(product);
        indexProduct(savedProduct);
    }

    private void indexProduct(Product product)
    throws JsonProcessingException {
        IndexRequest request = new IndexRequest("products").id(product.getId() + "");
        /*Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("name", product.getName());
        jsonMap.put("price", product.getPrice());
        jsonMap.put("inStock", product.getInStock());
        jsonMap.put("sold", product.getSold());
        jsonMap.put("tags", product.getTags());
        jsonMap.put("description", product.getDescription());
        jsonMap.put("isActive", product.getIsActive());
        jsonMap.put("created", product.getCreated());*/
        ProductEsDTO productEsDTO = new ProductEsDTO();
        BeanUtils.copyProperties(product, productEsDTO);
        request.source(Utils.getJsonFromObject(productEsDTO), XContentType.JSON);
        IndexResponse response = null;
        try {
            response = client.index(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            log.error(e.getDetailedMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        log.info("***********" + response + "****************");
    }

    public List<Product> getAllProductsFromEs()
    throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().from(0)
                                                                           .size(5)
                                                                           .query(QueryBuilders.matchAllQuery());
        SearchRequest searchRequest = new SearchRequest("products");
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        List<Product> products = new ArrayList<>();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit searchHit : searchHits) {
            Product product = Utils.getObjectFromString(Product.class, searchHit.getSourceAsString());
            product.setId(Long.parseLong(searchHit.getId()));
            products.add(product);
        }
        return products;
    }

    public List<ProductEsDTO> getProductsByTags(ProductEsSearchDTO productEsSearchDTO) {
        return productRepoService.getProductsByTags(productEsSearchDTO);
    }

    public List<ProductEsDTO> getProductsByIds(ProductEsSearchDTO productEsSearchDTO) {
        return productRepoService.getProductsByIds(productEsSearchDTO);
    }

    public List<ProductEsDTO> getProductsByDateBetween(ProductEsSearchDTO productEsSearchDTO)
    throws ParseException {
        return productRepoService.getProductsByDateBetween(productEsSearchDTO);
    }

    public void indexProductViaRepo(Product product) {
        ProductIndex productIndex = new ProductIndex();
        BeanUtils.copyProperties(product, productIndex);
        productIndexRepository.save(productIndex);
    }
}
