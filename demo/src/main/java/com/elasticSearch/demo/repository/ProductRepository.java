package com.elasticSearch.demo.repository;

import com.elasticSearch.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
