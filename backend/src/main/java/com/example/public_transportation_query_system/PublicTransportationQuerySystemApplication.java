package com.example.public_transportation_query_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SecurityScheme(name = "api_token", type = SecuritySchemeType.HTTP, scheme = "Bearer")
@SpringBootApplication
public class PublicTransportationQuerySystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicTransportationQuerySystemApplication.class, args);
	}

}
