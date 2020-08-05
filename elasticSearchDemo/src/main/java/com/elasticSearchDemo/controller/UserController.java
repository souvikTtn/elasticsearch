package com.elasticSearchDemo.controller;

import com.elasticSearchDemo.entity.User;
import com.elasticSearchDemo.service.UserService;
import com.elasticSearchDemo.util.ElasticSearchUtil;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ElasticSearchUtil elasticSearchUtil;

    @Autowired
    private Client client;

    @PostMapping("/users")
    public String addUser(@RequestBody User user)
    throws IOException {
        User user1 = userService.addUser(user);
        if (Objects.nonNull(user1)) {
            return elasticSearchUtil.pushUserFromDbToIndex(user1);
        }
        return "NOT_CREATED";
    }

    @GetMapping("/users/{id}")
    public Map<String, Object> getUserFromIndex(@PathVariable("id") Long id) {
        GetResponse getResponse = client.prepareGet("users", "employee", id + "").get();
        return getResponse.getSource();
    }
}
