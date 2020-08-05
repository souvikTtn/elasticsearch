package com.elasticSearch.demo.index;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;


import java.util.Date;
import java.util.List;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Document(indexName = "product_index")
@Mapping(mappingPath = "/elastic/productIndex.json")
@Setting(settingPath = "/elastic/auto-complete-analyzer.json")
public class ProductIndex {
    @Id
    String id;
    /*@MultiField(mainField = @Field(type = FieldType.Text), otherFields = @InnerField(suffix = "keyword", type =
            FieldType.Keyword))*/
    String name;
    /*@Field(type = FieldType.Double)*/
    Double price;
    /*@Field(type = FieldType.Long)*/
    Long in_stock;
    /*@Field(type = FieldType.Long)*/
    Long sold;
    /*@Field(type = FieldType.Text)*/
    List<String> tags;
    /*@Field(type = FieldType.Text)*/
    String description;
    /*@Field(type = FieldType.Boolean)*/
    Boolean is_active;
    @JsonFormat(pattern = "yyyy/MM/dd")
    Date created = new Date();
}
