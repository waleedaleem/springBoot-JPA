package com.example.five9demo.services;

import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class OrganizationServiceTest {
    @Mock
    OrganizationRepository organizationRepository;
    @InjectMocks
    OrganizationService organizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSaveAllOrganizations() {
        organizationService.saveAllOrganizations(new OrganizationRequest());
    }
}

