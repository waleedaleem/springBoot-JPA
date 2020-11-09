package com.example.five9demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Five9DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Five9DemoApplication.class, args);
	}

}
