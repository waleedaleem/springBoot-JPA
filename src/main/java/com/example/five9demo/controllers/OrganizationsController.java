package com.example.five9demo.controllers;

import com.example.five9demo.DTO.CustomerDTO;
import com.example.five9demo.requests.OrganizationRequest;
import com.example.five9demo.responses.OrganizationResponse;
import com.example.five9demo.services.OrganizationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.Context;

@RestController
@RequestMapping(value = {"/testapp/test.com/organization-service/v1"})
@Api(tags = {"Organization Services"})
@Validated
public class OrganizationsController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationsController.class);

    @Autowired
    private OrganizationsService organizationsService;

    @PostMapping(
            path = "/organizations",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(tags = "Organization Services", value ="Add organizations", notes = "This API will create/add organizations")
    public ResponseEntity<?> addOrganizations(@Context HttpServletRequest request, @RequestBody OrganizationRequest organizationRequest){

        OrganizationResponse organizationResponse = new OrganizationResponse();
        try{
            organizationsService.saveAllOrganizations(organizationRequest);
        } catch(Exception e){
            organizationResponse.setMessage(e.getMessage());
        }
        organizationResponse.setInformation("organizations saved");
        return ResponseEntity.ok(organizationResponse);
    }

    @PutMapping(
            path = "/organizations/{organizationName}/customers/{customerName}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(
            tags = "Organization Services",
            value = "Updates an organization customer",
            notes = "This API will update an existing organization customer")
    public ResponseEntity<OrganizationResponse> updateOrganizationCustomer(@Context HttpServletRequest request,
                                                                           @PathVariable @NotBlank String organizationName,
                                                                           @PathVariable @NotBlank String customerName,
                                                                           @RequestBody @Valid CustomerDTO updatedCustomer) {
        if (logger.isDebugEnabled()) {
            logger.debug(
                    "Received updateOrganizationCustomer request. Path: {}, Body: {}",
                    request.getRequestURI(), updatedCustomer.toJson());
        }

        // provide an idempotent interface by skipping redundant requests processing
        if (!upToDate(
                organizationName, customerName, updatedCustomer.getOrganizationName(),
                updatedCustomer.getName())) {
            organizationsService.updateOrganizationCustomer(
                    organizationName, customerName, updatedCustomer);
        }

        OrganizationResponse organizationResponse = new OrganizationResponse(
                HttpStatus.OK.getReasonPhrase(), String.valueOf(HttpStatus.OK.value()),
                "customer updated");
        ResponseEntity<OrganizationResponse> response = ResponseEntity.ok(organizationResponse);

        if (logger.isDebugEnabled()) {
            logger.debug(
                    "Processed updateOrganizationCustomer request successfully. Response: {}",
                    organizationResponse.toJson());
        }
        return response;
    }

    private boolean upToDate(String oldOrganizationName,
                             String oldCustomerName,
                             String newOrganizationName,
                             String newCustomerName) {
        return StringUtils.hasText(oldOrganizationName)
                && oldOrganizationName.equalsIgnoreCase(newOrganizationName)
                && StringUtils.hasText(oldCustomerName)
                && oldCustomerName.equalsIgnoreCase(newCustomerName);
    }
}
