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
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Service
@Validated
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
                                           @Valid CustomerDTO updatedCustomer) {
        updatedCustomer = updatedCustomer.withOldInstance(
                currentCustomerName, currentOrganizationName);

        // verify customer exists before update
        Customer customerEntity = retrieveCustomer(updatedCustomer.getOldInstance());

        // verify new organization exists before updating customer
        String newOrganizationName = updatedCustomer.getOrganizationName().toUpperCase();
        Organization newOrganizationEntity = organizationRepository.findByName(newOrganizationName);
        if (newOrganizationEntity == null) {
            throw new IllegalStateException(
                    "updateOrganizationCustomer failed. Organization " + newOrganizationName
                            + " should exist first.");
        }

        // verify new customer name is not taken by another existing entry
        String newCustomerName = updatedCustomer.getName().toUpperCase();
        Customer existingCustomerEntity = customerRepository.findByName(newCustomerName);
        if (Objects.nonNull(existingCustomerEntity)
                && !Objects.equals(existingCustomerEntity.getId(), customerEntity.getId())) {
            throw new IllegalStateException(
                    "updateOrganizationCustomer failed. New customer name " + newCustomerName
                            + " already taken.");
        }

        customerEntity.setName(newCustomerName);
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
    private Customer retrieveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findByNameAndOrganizationName(
                customerDTO.getName().toUpperCase(),
                customerDTO.getOrganizationName().toUpperCase());
        if (customer == null) {
            throw new IllegalArgumentException(
                    "Customer " + customerDTO.toJson() + " does not exist");
        }
        return customer;
    }
}
