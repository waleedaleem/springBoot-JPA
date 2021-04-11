package com.example.five9demo.services;

import com.example.five9demo.entities.Organization;
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

class OrganizationServiceTest {
    @Mock
    OrganizationRepository organizationRepository;
    @InjectMocks
    OrganizationsService organizationsService;

    private Organization organization = new Organization();
    private List<Organization> organizations = new ArrayList<>();
    private OrganizationRequest organizationRequest = new OrganizationRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.organization.setName("hr");
        this.organizations.add(organization);
        this.organizationRequest.setOrganizations(organizations);
    }

    @Test
    void testSaveAllOrganizationsValidRequest() {

        organizationsService.saveAllOrganizations(organizationRequest);

    }

    @Test
    void testSaveAllOrganizationsNullRequest() {
        try{
            organizationsService.saveAllOrganizations(null);
        } catch (Exception e){
            Assertions.assertEquals(e.getMessage(),"Invalid request");
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
            Assertions.assertEquals(e.getMessage(),"Organization name is invalid");
        }
    }
}

