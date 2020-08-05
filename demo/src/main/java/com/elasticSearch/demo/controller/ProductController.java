package com.elasticSearch.demo.controller;

import com.elasticSearch.demo.Service.ProductService;
import com.elasticSearch.demo.dto.ProductEsSearchDTO;
import com.elasticSearch.demo.entity.Product;
import com.elasticSearch.demo.repository.elasticRepository.ProductIndexRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductIndexRepository productIndexRepository;

    @PostMapping
    public ResponseEntity createProductIndexForElasticSearch()
    throws IOException {
        if (productService.createProductIndexForElasticSearch()) {
            return ResponseEntity.ok("index created successfully");
        }
        return ResponseEntity.ok("index already exists");
    }

    @PostMapping("{flag}")
    public ResponseEntity createProductIndexForElasticSearchUsingTemplate(@PathVariable("flag") Boolean flag)
    throws IOException {
        return ResponseEntity.ok(productService.createProductIndexForElasticSearchUsingTemplate());
    }

    @PostMapping("save")
    public ResponseEntity saveProduct(@RequestBody Product product)
    throws JsonProcessingException {
        productService.saveProduct(product);
        return ResponseEntity.ok("product saved successfull");
    }

    @DeleteMapping
    public ResponseEntity deleteProductIndex() {
        return ResponseEntity.ok(productService.deleteIndex());
    }

    @GetMapping("products")
    public ResponseEntity getAllProductsFromEs()
    throws IOException {
        return ResponseEntity.ok(productService.getAllProductsFromEs());
    }

    @GetMapping("products/tags")
    public ResponseEntity getProductsByTags(@RequestBody ProductEsSearchDTO productEsSearchDTO) {
        return ResponseEntity.ok(productService.getProductsByTags(productEsSearchDTO));
    }

    @GetMapping("products/ids")
    public ResponseEntity getProductsByIds(@RequestBody ProductEsSearchDTO productEsSearchDTO) {
        return ResponseEntity.ok(productService.getProductsByIds(productEsSearchDTO));
    }

    @GetMapping("products/date/between")
    public ResponseEntity getProductsByDateBetween(@RequestBody ProductEsSearchDTO productEsSearchDTO)
    throws ParseException {
        return ResponseEntity.ok(productService.getProductsByDateBetween(productEsSearchDTO));
    }

    @PostMapping("products/index")
    public ResponseEntity indexProduct(@RequestBody Product product) {
        productService.indexProductViaRepo(product);
        return ResponseEntity.ok("product Indexed Successfully");
    }

    @GetMapping("products/{id}")
    public ResponseEntity getProductById(@PathVariable("id") String id) {
        return ResponseEntity.ok(productIndexRepository.findById(id));
    }

}
