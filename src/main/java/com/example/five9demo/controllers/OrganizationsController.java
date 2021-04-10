package com.example.five9demo.controllers;

import com.example.five9demo.requests.OrganizationRequest;
import com.example.five9demo.responses.OrganizationResponse;
import com.example.five9demo.services.OrganizationsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.ws.rs.core.Context;

@RestController
@RequestMapping(value = "${service.base.url}")
@Api(tags = {"Organization Services"})
public class OrganizationsController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationsController.class);

    @Autowired
    private OrganizationsService organizationsService;

    @PostMapping(path = "/organizations")
    @ApiOperation(tags = "Organization Services", value ="Add organizations", notes = "This API will create/add organizations")
    public ResponseEntity<OrganizationResponse> addOrganizations(@Context HttpServletRequest request,
                                                                 @RequestBody @Valid OrganizationRequest organizationRequest) {
        if (logger.isDebugEnabled()) {
            logger.debug("Received addOrganizations request {}", organizationRequest.toJson());
        }
        ResponseEntity<OrganizationResponse> response;
        OrganizationResponse organizationResponse;
        try{
            organizationsService.saveAllOrganizations(organizationRequest);
            organizationResponse = new OrganizationResponse(
                    HttpStatus.OK.getReasonPhrase(), String.valueOf(HttpStatus.OK.value()),
                    "organizations saved");
            response = ResponseEntity.ok(organizationResponse);
            logger.debug("Processed addOrganizations request successfully");
        } catch (Exception e) {
            organizationResponse = new OrganizationResponse();
            organizationResponse.setMessage(e.getMessage());

            HttpStatus responseStatus;
            if (e instanceof ConstraintViolationException) {
                responseStatus = HttpStatus.BAD_REQUEST;
            } else {
                responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            organizationResponse.setMessageCode(String.valueOf(responseStatus.value()));

            organizationResponse.setInformation("organizations not saved");
            response = ResponseEntity.status(responseStatus).body(organizationResponse);
            logger.debug("Error processing addOrganizations request successfully");
        }
        return response;
    }
}
