package com.example.five9demo.services;

import com.example.five9demo.DTO.CustomerDTO;
import com.example.five9demo.entities.Customer;
import com.example.five9demo.entities.Organization;
import com.example.five9demo.repositories.CustomerRepository;
import com.example.five9demo.repositories.OrganizationRepository;
import com.example.five9demo.requests.OrganizationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Service
public class OrganizationsService implements Serializable {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public void saveAllOrganizations(OrganizationRequest organizationRequest)
            throws IllegalArgumentException {

        if(null == organizationRequest ||
                CollectionUtils.isEmpty(organizationRequest.getOrganizations())) {
            throw new IllegalArgumentException("Invalid request");
        }
        List<Organization> organizations = organizationRequest.getOrganizations();
        organizationRepository.saveAll(organizations);
    }

    public void updateOrganizationCustomer(@NotBlank String currentOrganizationName,
                                           @NotBlank String currentCustomerName,
                                           CustomerDTO updatedCustomer) {
        updatedCustomer = updatedCustomer.withOldInstance(
                currentCustomerName, currentOrganizationName);

        // verify customer exists before update
        Customer customerEntity = retrieveCustomer(updatedCustomer.getOldInstance());

        // verify new organization exists before updating customer
        String newOrganizationName = updatedCustomer.getOrganizationName();
        Organization newOrganizationEntity = organizationRepository.findByName(newOrganizationName);
        if (newOrganizationEntity == null) {
            throw new IllegalStateException(
                    "updateOrganizationCustomer failed. Organization " + newOrganizationName
                            + " should exist first.");
        }

        customerEntity.setName(updatedCustomer.getName().toUpperCase());
        customerEntity.setOrganization(newOrganizationEntity);
        customerRepository.save(customerEntity);
    }

    /**
     * Retrieves a customer entity from the repository
     * 
     * @param customerDTO
     *            customerDTO object
     * @return customer entity
     */
    Customer retrieveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByNameAndOrganizationName(
                customerDTO.getName().toUpperCase(),
                customerDTO.getOrganizationName().toUpperCase());
        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer " + customerDTO.toString() + " does not exist");
        }
        return customer;
    }
}
