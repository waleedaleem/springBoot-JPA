package com.example.five9demo.requests;

import com.example.five9demo.data.Organization;
import lombok.Data;

import java.util.List;

@Data
public class OrganizationRequest {
    List<Organization> organizations;
}
