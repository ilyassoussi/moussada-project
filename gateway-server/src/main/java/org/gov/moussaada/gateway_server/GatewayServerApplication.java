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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServerApplication.class, args);
	}

	@Bean
	public CorsWebFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("http://localhost:3000"); // ou "http://localhost:3000" (React)
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return new CorsWebFilter(source);
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
				.route("subventions-service", r -> r
						.path("/subvention/**")
						.uri("lb://subventions-service"))
				.route("terrain-service", r -> r
						.path("/terrain/**")
						.uri("lb://terrain-service"))
				.route("appel-service", r -> r
						.path("/appel/**")
						.uri("lb://appel-service"))
				.route("shared-micro", r -> r
						.path("/shared/**")
						.uri("lb://shared-micro"))
				.build();
	}

	@Bean
	 DiscoveryClientRouteDefinitionLocator DinamicRoutes(ReactiveDiscoveryClient rdc , DiscoveryLocatorProperties dlp){
		return new DiscoveryClientRouteDefinitionLocator(rdc , dlp);
	}
}
