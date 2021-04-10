package com.example.five9demo.services;

import com.example.five9demo.data.Organization;
import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class OrganizationsServiceTest {
    @Mock
    OrganizationRepository organizationRepository;
    @InjectMocks
    OrganizationsService organizationsService;

    private final Organization organization = new Organization();
    private final List<Organization> organizations = new ArrayList<>();
    private final OrganizationRequest organizationRequest = new OrganizationRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.organization.setName("hr");
        this.organizations.add(organization);
        this.organizationRequest.setOrganizations(organizations);
    }

    @Test
    void testSaveAllOrganizationsValidRequest() throws Exception {

        organizationsService.saveAllOrganizations(organizationRequest);

    }

    @Test
    void testSaveAllOrganizationsNullRequest() {
        try{
            organizationsService.saveAllOrganizations(null);
        } catch (Exception e){
            Assertions.assertEquals("Invalid request", e.getMessage());
        }
    }

    @Test
    void testSaveAllOrganizationsInvalidOrgName() {
        try{
            this.organization.setName("");
            this.organizations.add(organization);
            this.organizationRequest.setOrganizations(organizations);
            organizationsService.saveAllOrganizations(organizationRequest);
        } catch (Exception e){
            Assertions.assertEquals("Organization name is invalid", e.getMessage());
        }
    }
}

