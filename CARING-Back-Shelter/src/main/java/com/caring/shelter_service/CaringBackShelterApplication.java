package com.caring.shelter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CaringBackShelterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaringBackShelterApplication.class, args);
	}

}
