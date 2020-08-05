package com.elasticSearchDemo.repository;

import com.elasticSearchDemo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,Long> {
}
