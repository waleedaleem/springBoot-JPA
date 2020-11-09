package com.example.five9demo.controllers;

import com.example.five9demo.requests.OrganizationRequest;
import com.example.five9demo.responses.OrganizationResponse;
import com.example.five9demo.services.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@RestController
@RequestMapping(value = {"/testapp/five9.com/organization-service/v1"})
@Api(tags = {"Organization Services"})
public class OrganizationsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrganizationsController.class);

    @Autowired
    private OrganizationService organizationService;

    @PostMapping(path = "/organizations")
    @ApiOperation(tags = "Organization Services", value ="Add organizations", notes = "This API will create/add organizations")
    public ResponseEntity<?> addOrganizations(@Context HttpServletRequest request, @RequestBody OrganizationRequest organizationRequest){

        OrganizationResponse organizationResponse = new OrganizationResponse();
        try{
            organizationService.saveAllOrganizations(organizationRequest);
        } catch(Exception e){
            organizationResponse.setMessage(e.getMessage());
        }
        organizationResponse.setInformation("organizations saved");
        return ResponseEntity.ok(organizationResponse);
    }

}
