package org.gov.moussaada.gateway_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public RouteLocator customRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("admin-service", r -> r
						.path("/admin/**")
						.uri("lb://admin-service"))
				.route("utilisateur-service", r -> r
						.path("/utilisateur/**")
						.uri("lb://utilisateur-service"))
				.route("paysan-service", r -> r
						.path("/paysan/**")
						.uri("lb://paysan-service"))
				.route("subvention-service", r -> r
						.path("/subvention/**")
						.uri("lb://subvention-service"))
				.route("terrain-service", r -> r
						.path("/terrain/**")
						.uri("lb://terrain-service"))
				.route("appel-service", r -> r
						.path("/appel/**")
						.uri("lb://appel-service"))
				.build();
	}

	@Bean
	 DiscoveryClientRouteDefinitionLocator DinamicRoutes(ReactiveDiscoveryClient rdc , DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc , dlp);
	}
}
