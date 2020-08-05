package com.elasticSearch.demo.config;

import com.elasticSearch.demo.component.ElasticSearchProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ElasticSearchConfig {
    @Autowired
    private ElasticSearchProperties config;

    @Bean
    @Qualifier("client")
    public RestHighLevelClient getClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(config.getEsIpAddress(), Integer.parseInt(config.getEsPortNumber()), "http")
                ));
    }

}
