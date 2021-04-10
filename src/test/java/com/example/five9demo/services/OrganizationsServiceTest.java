package com.example.five9demo.services;

import com.example.five9demo.data.Organization;
import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.example.five9demo.data.Organization.INVALID_NAME_ERROR;
import static org.junit.jupiter.api.Assertions.fail;

class OrganizationServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceTest.class);

    private static Validator validator;

    @Mock
    OrganizationRepository organizationRepository;
    @InjectMocks
    OrganizationsService organizationsService;

    private Organization organization = new Organization();
    private List<Organization> organizations = new ArrayList<>();
    private OrganizationRequest organizationRequest = new OrganizationRequest();

    @BeforeAll
    public static void staticSetUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

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
    void testSaveAllOrganizationsNullRequest() throws Exception {
        try{
            organizationsService.saveAllOrganizations(null);
        } catch (Exception e){
            Assertions.assertEquals(e.getMessage(),"Invalid request");
        }
    }

    static Stream<String> InvalidOrganizationNames() {
        return Stream.of("", "   ", null);
    }

    @ParameterizedTest
    @MethodSource("InvalidOrganizationNames")
    void testSaveAllOrganizationsInvalidOrgName(String name) throws Exception {
        logger.info("testing with name = \"{}\"", name);
        try{
            this.organization.setName(name);
            if (!CollectionUtils.isEmpty(validator.validate(this.organization))) {
                throw new Exception(INVALID_NAME_ERROR);
            }
            this.organizations.add(organization);
            this.organizationRequest.setOrganizations(organizations);
            organizationsService.saveAllOrganizations(organizationRequest);

            fail("Should not have allowed invalid organization names");
        } catch (Exception e){
            Assertions.assertEquals(INVALID_NAME_ERROR, e.getMessage());
        }
    }


}

