package com.example.five9demo;

import com.example.five9demo.controllers.OrganizationsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class Five9DemoApplicationTests {

    @Autowired
    OrganizationsController organizationsController;

	@Test
	void contextLoads() {
	    assertNotNull(organizationsController);
	}
}
