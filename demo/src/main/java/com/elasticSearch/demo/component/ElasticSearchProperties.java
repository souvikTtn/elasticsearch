package com.elasticSearch.demo.component;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElasticSearchProperties {
    @Value("${esIpAddress}")
    String esIpAddress;
    @Value("${esClusterName}")
    String esClusterName;
    @Value("${esPortNumber}")
    String esPortNumber;
    @Value("${esNumberOfShards}")
    String esNumberOfShards;
    @Value("${esNumberOfReplicas}")
    String esNumberOfReplicas;
}
