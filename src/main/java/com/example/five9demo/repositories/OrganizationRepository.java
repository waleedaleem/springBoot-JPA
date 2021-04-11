package com.example.five9demo.repositories;

import com.example.five9demo.entities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,Integer> {

    public Organization findByName(String name);
}
