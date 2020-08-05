package com.elasticSearch.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEsDTO {
    String name;
    Double price;
    Long in_stock;
    Long sold;
    List<String> tags;
    String description;
    Boolean is_active;
    //@DateTimeFormat(pattern ="yyyy/MM/dd" )
    @JsonFormat(pattern = "yyyy/MM/dd")
    Date created;
}
