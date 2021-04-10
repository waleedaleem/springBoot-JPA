package com.example.five9demo.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "organizations")
@QueryEntity
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private Set<Customer> customers = new HashSet<>();
}
