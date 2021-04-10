package com.example.five9demo.repositories;

import com.example.five9demo.data.Customer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void findByOrganizationName_MatchExists() {
        final String hr = "HR";
        Set<Customer> hrCustomers = customerRepository.findAllByOrganizationName(hr);

        // verify all returned customers are "HR"
        assertFalse(hrCustomers.removeIf(customer -> !hr.equals(customer.getOrganization().getName())));
    }

    @Test
    public void findByOrganizationName_MatchNotExists() {
        final String bogusName = "blah";
        Set<Customer> hrCustomers = customerRepository.findAllByOrganizationName(bogusName);

        // verify no customers are returned
        assertTrue(hrCustomers.isEmpty());
    }
}
