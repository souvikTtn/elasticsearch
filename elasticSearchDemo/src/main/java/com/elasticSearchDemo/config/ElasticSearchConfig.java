package com.elasticSearchDemo.config;

import lombok.Getter;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@Getter
public class ElasticSearchConfig {
    @Value("${elasticsearch.host:localhost}")
    private String host;
    @Value("${elasticsearch.port:9300}")
    private int port;

    @Bean
    public Client client() {
        TransportClient client = null;
        try {
            System.out.println("host:" + host + "port:" + port);
            client = new PreBuiltTransportClient(Settings.EMPTY)
                    .addTransportAddress(
                            new TransportAddress(InetAddress.getByName(host), port));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return client;
    }

}
