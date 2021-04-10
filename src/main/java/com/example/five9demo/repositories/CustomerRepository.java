package com.example.five9demo.repositories;

import com.example.five9demo.data.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Set<Customer> findAllByOrganizationName(String organizationName);
}
