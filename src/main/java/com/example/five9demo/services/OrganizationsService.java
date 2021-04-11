package com.example.five9demo.services;

import com.example.five9demo.entities.Organization;
import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

@Service
public class OrganizationsService implements Serializable {

    @Autowired
    private OrganizationRepository organizationRepository;

    public void saveAllOrganizations(OrganizationRequest organizationRequest)
            throws IllegalArgumentException {

        if(null == organizationRequest ||
                CollectionUtils.isEmpty(organizationRequest.getOrganizations())) {
            throw new IllegalArgumentException("Invalid request");
        }
        List<Organization> organizations = organizationRequest.getOrganizations();
        organizationRepository.saveAll(organizations);
    }
}
