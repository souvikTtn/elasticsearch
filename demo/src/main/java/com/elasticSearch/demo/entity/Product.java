package com.elasticSearch.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;


@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    Double price;
    Long in_stock;
    Long sold;
    @ElementCollection
    List<String> tags;
    String description;
    Boolean is_active;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy/MM/dd")
    Date created = new Date();
}
