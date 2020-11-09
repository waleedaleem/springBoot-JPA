package com.example.five9demo.controllers;

import com.example.five9demo.requests.OrganizationRequest;
import com.example.five9demo.services.OrganizationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;

class OrganizationsControllerTest {
    @Mock
    Logger LOGGER;
    @Mock
    OrganizationService organizationService;
    @InjectMocks
    OrganizationsController organizationsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddOrganizations() {
        ResponseEntity<?> result = organizationsController.addOrganizations(null, new OrganizationRequest());
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
    }
}

