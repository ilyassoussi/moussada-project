package org.gov.moussaada.subventions_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class SubventionsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubventionsServiceApplication.class, args);
	}

}
