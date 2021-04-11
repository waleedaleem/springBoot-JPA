package com.example.five9demo.controllers;

import com.example.five9demo.DTO.CustomerDTO;
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
import org.springframework.test.web.servlet.ResultMatcher;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationsController.class)
class OrganizationsControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(
            NON_EMPTY).disable(SerializationFeature.INDENT_OUTPUT);

    public static final String HR = "HR";
    public static final String FINANCE = "FINANCE";
    public static final String CUSTOMER_1 = "CUSTOMER1";
    public static final String CUSTOMER_100 = "CUSTOMER100";

    private static final String updateCustomerUrlTemplate = "/organizations/%s/customers/%s";

    @Value("${service.base.url}")
    String serviceBaseUrl;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationsService organizationsService;

    private static String toJson(CustomerDTO customer) throws JsonProcessingException {
        return objectMapper.writeValueAsString(customer);
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
    void testUpdateOrganizationCustomerOrganizationChange() throws Exception {
        testUpdateOrganizationCustomerRequest(HR, CUSTOMER_1, FINANCE, CUSTOMER_1, status().isOk());
    }

    @Test
    void testUpdateOrganizationCustomerNameChange() throws Exception {
        testUpdateOrganizationCustomerRequest(HR, CUSTOMER_1, HR, CUSTOMER_100, status().isOk());
    }

    @Test
    void testUpdateOrganizationCustomerReplacementChange() throws Exception {
        testUpdateOrganizationCustomerRequest(
                HR, CUSTOMER_1, FINANCE, CUSTOMER_100, status().isOk());
    }

    @Test
    void testUpdateOrganizationCustomerMissingOldOrg() throws Exception {
        testUpdateOrganizationCustomerRequest(
                "  ", CUSTOMER_1, FINANCE, CUSTOMER_100, status().isBadRequest());
    }

    @Test
    void testUpdateOrganizationCustomerMissingNewOrg() throws Exception {
        testUpdateOrganizationCustomerRequest(
                HR, CUSTOMER_1, "", CUSTOMER_100, status().isBadRequest());
    }

    @Test
    void testUpdateOrganizationCustomerMissingOldName() throws Exception {
        testUpdateOrganizationCustomerRequest(
                HR, " ", FINANCE, CUSTOMER_100, status().isBadRequest());
    }

    @Test
    void testUpdateOrganizationCustomerMissingNewName() throws Exception {
        testUpdateOrganizationCustomerRequest(
                HR, CUSTOMER_1, FINANCE, null, status().isBadRequest());
    }

    private void testUpdateOrganizationCustomerRequest(String oldOrganization,
                                                       String oldName,
                                                       String newOrganization,
                                                       String newName,
                                                       ResultMatcher expectedStatus)
            throws Exception {
        CustomerDTO customer = new CustomerDTO();
        customer.setOrganizationName(newOrganization);
        customer.setName(newName);

        doNothing().when(organizationsService).updateOrganizationCustomer(
                anyString(), anyString(), any());

        String requestUrl = String.format(updateCustomerUrlTemplate, oldOrganization, oldName);
        // @formatter:off
        mockMvc.perform(put(serviceBaseUrl+requestUrl)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(toJson(customer)))
            .andDo(print())
            .andExpect(expectedStatus);
        // @formatter:on
    }
}

