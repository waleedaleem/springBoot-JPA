package com.example.five9demo.services;

import com.example.five9demo.data.Organization;
import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class OrganizationService implements Serializable {

    @Autowired
    private OrganizationRepository organizationRepository;

    public void saveAllOrganizations(OrganizationRequest organizationRequest){

        List<Organization> organizations = organizationRequest.getOrganizations();
        organizationRepository.saveAll(organizations);


    }

}
