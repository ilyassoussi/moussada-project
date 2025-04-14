package org.gov.moussaada.terrain_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class TerrainServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerrainServiceApplication.class, args);
	}

}
