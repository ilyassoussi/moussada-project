package org.gov.moussaada.subventions_service;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients

public class SubventionsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SubventionsServiceApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){return new ModelMapper();}
}
