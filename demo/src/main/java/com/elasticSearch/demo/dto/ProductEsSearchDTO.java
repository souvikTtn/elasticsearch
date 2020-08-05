package com.elasticSearch.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEsSearchDTO {
    List<String> ids;
    List<String> name;
    List<Double> price;
    List<Long> in_stock;
    List<Long> sold;
    List<String> tags;
    List<String> description;
    List<Boolean> is_active;
    @JsonFormat(pattern = "yyyy/MM/dd")
    List<Date> created;
    @JsonFormat(pattern = "yyyy/MM/dd")
    Date fromDate;
    @JsonFormat(pattern = "yyyy/MM/dd")
    Date toDate;
}
