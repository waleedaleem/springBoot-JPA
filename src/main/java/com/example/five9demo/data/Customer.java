package com.example.five9demo.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "customers")
@QueryEntity
public class Customer implements Serializable {

    private static final long serialVersionUID = -6433721069248439325L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_name", nullable = false, referencedColumnName = "name")
    private Organization organization;

    public void setName(String name) {
        this.name = name.toUpperCase();
    }
}
