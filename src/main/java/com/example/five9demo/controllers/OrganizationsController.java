package com.example.five9demo.controllers;

import com.example.five9demo.data.Customer;
import com.example.five9demo.requests.OrganizationRequest;
import com.example.five9demo.responses.OrganizationResponse;
import com.example.five9demo.services.OrganizationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.Context;
import java.util.Set;

@RestController
@RequestMapping(value = {"/testapp/test.com/organization-service/v1"})
@Api(tags = {"Organization Services"})
@Validated
public class OrganizationsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationsController.class);

    @Autowired
    private OrganizationsService organizationsService;

    @PostMapping(
            path = "/organizations",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Organization Services", value ="Add organizations", notes = "This API will create/add organizations")
    public ResponseEntity<OrganizationResponse> addOrganizations(@Context HttpServletRequest request,
                                                                 @RequestBody OrganizationRequest organizationRequest) {

        OrganizationResponse organizationResponse = new OrganizationResponse();
        try{
            organizationsService.saveAllOrganizations(organizationRequest);
        } catch(Exception e){
            organizationResponse.setMessage(e.getMessage());
        }
        organizationResponse.setInformation("organizations saved");
        return ResponseEntity.ok(organizationResponse);
    }

    @GetMapping(
            path = "/organizations/{organizationName}/customers",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            tags = "Organization Services",
            value = "List organization customers",
            notes = "This API will list organization customers")
    public ResponseEntity<Set<Customer>> listOrganizationCustomers(@Context HttpServletRequest request,
                                                                   @PathVariable @NotBlank String organizationName) {
        Set<Customer> organizationCustomers = organizationsService.getOrganizationCustomers(
                organizationName.toUpperCase());
        return ResponseEntity.ok(organizationCustomers);
    }
}
