package com.example.five9demo.repositories;

import com.example.five9demo.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Set<Customer> findByOrganizationName(String organizationName);

    Customer findByNameAndOrganizationName(String name, String organizationName);
}
