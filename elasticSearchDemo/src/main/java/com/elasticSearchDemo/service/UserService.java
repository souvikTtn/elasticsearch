package com.elasticSearchDemo.service;

import com.elasticSearchDemo.entity.User;
import com.elasticSearchDemo.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public User addUser(User user) {
        return userRepo.save(user);
    }
}
