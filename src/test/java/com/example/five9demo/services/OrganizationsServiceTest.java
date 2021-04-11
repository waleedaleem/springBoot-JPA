package com.example.five9demo.services;

import com.example.five9demo.DTO.CustomerDTO;
import com.example.five9demo.entities.Customer;
import com.example.five9demo.entities.Organization;
import com.example.five9demo.repositories.CustomerRepository;
import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class OrganizationsServiceTest {

    public static final String HR = "HR";
    public static final String FINANCE = "FINANCE";
    public static final String CUSTOMER_1 = "CUSTOMER1";
    public static final String CUSTOMER_2 = "CUSTOMER2";
    public static final String NOT_EXISTS = "NOT_EXISTS";

    @Mock
    Organization organization;

    @Mock
    OrganizationRepository organizationRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    OrganizationsService organizationsService;

    private final OrganizationRequest organizationRequest = new OrganizationRequest();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        prepareRepositories();
    }

    private void prepareRepositories() {
        when(organizationRepository.findByName(eq(HR))).thenReturn(organization);
        when(organizationRepository.findByName(eq(FINANCE))).thenReturn(organization);
        when(organizationRepository.findByName(eq(NOT_EXISTS))).thenReturn(null);

        Customer customer1 = new Customer();
        customer1.setId(1);

        Customer customer2 = new Customer();
        customer2.setId(2);

        when(customerRepository.findByName(CUSTOMER_1)).thenReturn(customer1);
        when(customerRepository.findByName(CUSTOMER_2)).thenReturn(customer2);
        when(customerRepository.findByName(NOT_EXISTS)).thenReturn(null);

        when(
                customerRepository.findByNameAndOrganizationName(
                        eq(CUSTOMER_1), anyString())).thenReturn(customer1);
        when(
                customerRepository.findByNameAndOrganizationName(
                        eq(CUSTOMER_2), anyString())).thenReturn(customer2);
    }

    @Test
    void testSaveAllOrganizationsValidRequest() {
        Organization organization = new Organization();
        organization.setName(HR);
        List<Organization> organizations = Collections.singletonList(organization);
        this.organizationRequest.setOrganizations(organizations);
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
        Organization organization = new Organization();
        try{
            organization.setName("");
            List<Organization> organizations = Collections.singletonList(organization);
            this.organizationRequest.setOrganizations(organizations);
            organizationsService.saveAllOrganizations(organizationRequest);
        } catch (Exception e){
            assertEquals("Organization name is invalid", e.getMessage());
        }
    }

    @Test
    void testUpdateOrganizationCustomerChangeName() {
        CustomerDTO updatedCustomer = new CustomerDTO();
        updatedCustomer.setOrganizationName(HR);
        updatedCustomer.setName(NOT_EXISTS);

        organizationsService.updateOrganizationCustomer(HR, CUSTOMER_1, updatedCustomer);
    }

    @Test
    void testUpdateOrganizationCustomerChangeOrg() {
        CustomerDTO updatedCustomer = new CustomerDTO();
        updatedCustomer.setOrganizationName(FINANCE);
        updatedCustomer.setName(CUSTOMER_1);

        organizationsService.updateOrganizationCustomer(HR, CUSTOMER_1, updatedCustomer);
    }

    @Test
    void testUpdateOrganizationCustomerNewNameTaken() {
        try {
            CustomerDTO updatedCustomer = new CustomerDTO();
            updatedCustomer.setOrganizationName(HR);
            updatedCustomer.setName(CUSTOMER_2);

            organizationsService.updateOrganizationCustomer(HR, CUSTOMER_1, updatedCustomer);

            fail(
                    "Should have not allowed changing customer name to a name taken by another customer");
        } catch (IllegalStateException e) {
            assertEquals(
                    "updateOrganizationCustomer failed. New customer name " + CUSTOMER_2
                            + " already taken.",
                    e.getMessage());
        }
    }

    @Test
    void testUpdateOrganizationCustomerNonExistingOrganization() {
        try {
            CustomerDTO updatedCustomer = new CustomerDTO();
            updatedCustomer.setOrganizationName(NOT_EXISTS);
            updatedCustomer.setName(CUSTOMER_1);

            organizationsService.updateOrganizationCustomer(HR, CUSTOMER_1, updatedCustomer);

            fail("Should have not allowed moving customer to a nonexisting Organization");
        } catch (IllegalStateException e) {
            assertEquals(
                    "updateOrganizationCustomer failed. Organization " + NOT_EXISTS
                            + " should exist first.",
                    e.getMessage());
        }
    }
}

