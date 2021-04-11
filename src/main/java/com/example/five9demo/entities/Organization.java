package com.example.five9demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "customers")
@Entity
@Table(name = "organizations")
@QueryEntity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Organization implements Serializable {

    private static final long serialVersionUID = -6433721069248439324L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

    public void setName(String name) {
        this.name = name.toUpperCase();
    }
}
