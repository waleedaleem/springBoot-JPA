package com.example.five9demo.services;

import com.example.five9demo.data.Customer;
import com.example.five9demo.data.Organization;
import com.example.five9demo.repositories.CustomerRepository;
import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Service
public class OrganizationsService implements Serializable {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void saveAllOrganizations(OrganizationRequest organizationRequest) throws Exception {

        if (null == organizationRequest
                || CollectionUtils.isEmpty(organizationRequest.getOrganizations())) {
            throw new Exception("Invalid request");
        }
        List<Organization> organizations = organizationRequest.getOrganizations();
        organizationRepository.saveAll(organizations);
    }

    /**
     * @param organizationName
     *            organization name
     * @return customers of a specific organization
     */
    public Set<Customer> getOrganizationCustomers(String organizationName) {
        if (!StringUtils.hasText(organizationName)) {
            throw new IllegalArgumentException("organization name can not be blank");
        }

        return customerRepository.findAllByOrganizationName(organizationName);
    }
}
