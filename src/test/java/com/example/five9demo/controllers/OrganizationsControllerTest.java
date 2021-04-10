package com.example.five9demo.controllers;

import com.example.five9demo.data.Organization;
import com.example.five9demo.requests.OrganizationRequest;
import com.example.five9demo.services.OrganizationsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrganizationsController.class)
class OrganizationsControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper().setSerializationInclusion(
            NON_EMPTY).disable(SerializationFeature.INDENT_OUTPUT);

    @Value("${service.base.url}")
    String serviceBaseUrl;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizationsService organizationsService;

    private static String toJson(OrganizationRequest organizationRequest)
            throws JsonProcessingException {
        return objectMapper.writeValueAsString(organizationRequest);
    }

    @Test
    void testAddOrganizationsValidRequest() throws Exception {
        OrganizationRequest request = new OrganizationRequest();
        Organization organization = new Organization();
        organization.setName("HR");
        request.setOrganizations(Collections.singletonList(organization));
        doNothing().when(organizationsService).saveAllOrganizations(request);

        // @formatter:off
        mockMvc.perform(post(serviceBaseUrl+"/organizations")
            .contentType(APPLICATION_JSON)
            .content(toJson(request)))
            .andDo(print())
            .andExpect(status().isOk());
        // @formatter:on
    }

    @Test
    void testAddOrganizationsEmptyRequest() throws Exception {
        OrganizationRequest emptyRequest = new OrganizationRequest();
        emptyRequest.setOrganizations(null);
        testAddOrganizationsInvalidRequest(emptyRequest);
    }

    @Test
    void testAddOrganizationsInvalidOrgName() throws Exception {
        OrganizationRequest invalidNameRequest = new OrganizationRequest();
        Organization organization = new Organization();
        organization.setName(null);
        invalidNameRequest.setOrganizations(Collections.singletonList(organization));
        testAddOrganizationsInvalidRequest(invalidNameRequest);
    }

    void testAddOrganizationsInvalidRequest(OrganizationRequest invalidRequest) throws Exception {
        doNothing().when(organizationsService).saveAllOrganizations(invalidRequest);

        // @formatter:off
        mockMvc.perform(post(serviceBaseUrl+"/organizations")
            .contentType(APPLICATION_JSON)
            .content(toJson(invalidRequest)))
            .andDo(print())
            .andExpect(status().isBadRequest());
        // @formatter:on
    }
}

