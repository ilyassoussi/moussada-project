package org.gov.moussaada.terrain_service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients

public class TerrainServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TerrainServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){return new ModelMapper();}
}
