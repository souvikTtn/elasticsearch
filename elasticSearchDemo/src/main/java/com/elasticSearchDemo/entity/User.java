package com.elasticSearchDemo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ElementCollection
    private Map<String, String> userSettings;

    @Transient
    private Date creationDate = new Date();
}
