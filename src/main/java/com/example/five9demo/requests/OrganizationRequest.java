package com.example.five9demo.requests;

import com.example.five9demo.entities.Organization;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationRequest {
    List<Organization> organizations;
}
