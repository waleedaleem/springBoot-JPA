package com.example.five9demo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "organizations")
@QueryEntity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization {

    public static final String INVALID_NAME_ERROR = "Organization name is invalid";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @NotBlank(message = INVALID_NAME_ERROR)
    private String name;


}
