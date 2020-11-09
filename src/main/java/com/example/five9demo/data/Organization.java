package com.example.five9demo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "organizations")
@QueryEntity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;
    private String name;


}
