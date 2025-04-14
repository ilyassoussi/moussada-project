package org.gov.moussaada.appel_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class AppelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppelServiceApplication.class, args);
	}

}
