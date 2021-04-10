package com.example.five9demo.services;

import com.example.five9demo.data.Customer;
import com.example.five9demo.data.Organization;
import com.example.five9demo.repositories.CustomerRepository;
import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class OrganizationsServiceTest {

    public static final String HR = "hr";

    @Mock
    OrganizationRepository organizationRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    OrganizationsService organizationsService;

    private final Organization organization = new Organization();
    private final List<Organization> organizations = new ArrayList<>();
    private final OrganizationRequest organizationRequest = new OrganizationRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.organization.setName(HR);
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
            assertEquals("Invalid request", e.getMessage());
        }
    }

    @Test
    void testSaveAllOrganizationsInvalidOrgName() {
        try{
            this.organization.setName("");
            this.organizationRequest.setOrganizations(organizations);
            organizationsService.saveAllOrganizations(organizationRequest);
        } catch (Exception e){
            assertEquals("Organization name is invalid", e.getMessage());
        }
    }

    @Test
    void testGetOrganizationCustomersValidSearchKey() {
        Customer customer = new Customer();
        customer.setOrganization(this.organization);
        customer.setName("hr customer");
        Set<Customer> repoCustomers = Collections.singleton(customer);
        when(customerRepository.findAllByOrganizationName(eq(HR))).thenReturn(repoCustomers);

        Set<Customer> hrCustomers = organizationsService.getOrganizationCustomers(HR);

        assertEquals(repoCustomers, hrCustomers);
    }

    @Test
    void testGetOrganizationCustomersInvalidSearchKey() {
        try {
            Set<Customer> organizationCustomers = organizationsService.getOrganizationCustomers(
                    null);
        } catch (Exception e) {
            assertEquals("organization name can not be blank", e.getMessage());
        }
    }
}

