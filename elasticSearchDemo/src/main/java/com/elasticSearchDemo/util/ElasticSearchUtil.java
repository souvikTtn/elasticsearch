package com.elasticSearchDemo.util;

import com.elasticSearchDemo.entity.User;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@Component
public class ElasticSearchUtil {
    @Autowired
    Client client;
    public String pushUserFromDbToIndex(User user)
    throws IOException {
        IndexResponse response = client.prepareIndex("users", "employee", user.getId() + "")
                                       .setSource(jsonBuilder()
                                                          .startObject()
                                                          .field("name", user.getName())
                                                          .field("userSettings", user.getUserSettings())
                                                          .endObject()
                                       )
                                       .get();
        System.out.println("response id:" + response.getId());
        return response.getResult().toString();
    }
}
