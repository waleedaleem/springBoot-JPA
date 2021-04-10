package com.example.five9demo.controllers;

import com.example.five9demo.data.Customer;
import com.example.five9demo.data.Organization;
import com.example.five9demo.requests.OrganizationRequest;
import com.example.five9demo.services.OrganizationsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationsController.class)
class OrganizationsControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(
            NON_EMPTY).disable(SerializationFeature.INDENT_OUTPUT);

    public static final String HR = "HR";

    @Value("${service.base.url}")
    String serviceBaseUrl;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationsService organizationsService;

    private static String toJson(Set<Customer> customers) throws JsonProcessingException {
        return objectMapper.writeValueAsString(customers);
    }

    @InjectMocks
    OrganizationsController organizationsController;

    @Test
    void testAddOrganizations() {

        ResponseEntity<?> result = organizationsController.addOrganizations(null, new OrganizationRequest());

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());

    }

    @Test
    void testListOrganizationCustomersValidOrgName() throws Exception {
        Organization organization = new Organization();
        organization.setName("HR");
        Customer customer = new Customer();
        customer.setName("HR Customer");
        customer.setOrganization(organization);
        Set<Customer> organizationCustomers = Collections.singleton(customer);

        String resourceUrl = String.format("/organizations/%s/customers", HR);

        doReturn(organizationCustomers).when(organizationsService).getOrganizationCustomers(
                anyString());

        // @formatter:off
        mockMvc.perform(get(serviceBaseUrl + resourceUrl))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(toJson(organizationCustomers)));
        // @formatter:on
    }

    @Test
    void testListOrganizationCustomersInvalidOrgName() throws Exception {
        Organization organization = new Organization();
        organization.setName("HR");
        Customer customer = new Customer();
        customer.setName("HR Customer");
        customer.setOrganization(organization);
        Set<Customer> organizationCustomers = Collections.singleton(customer);

        String resourceUrl = "/organizations/ /customers";

        // @formatter:off
        doReturn(organizationCustomers)
            .when(organizationsService)
            .getOrganizationCustomers(anyString());
        
        mockMvc.perform(get(serviceBaseUrl + resourceUrl))
            .andDo(print())
            .andExpect(status().isBadRequest());
        // @formatter:on
    }
}

