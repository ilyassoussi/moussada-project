package org.gov.moussaada.subventions_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityAdmin implements WebMvcConfigurer {
    @Autowired
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityAdmin(@Lazy JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/paysan/**")
                .allowedOrigins("http://localhost:3000") // Origine autorisée
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(authori -> authori
                                .requestMatchers("/actuator/health").permitAll()
                                .requestMatchers("/subvention/create","/subvention/update/{id}","/subvention/delete/{id}", "/subvention/traitement/create").hasAuthority("ROLE_Subvention")
                                .requestMatchers("/subvention/traitement/{id}").hasAnyAuthority("ROLE_Subvention","ROLE_Paysan")
                                .requestMatchers("/subvention/{id}","/subvention/getall").permitAll()
                                .anyRequest().authenticated()
                        ).sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)
                        .build();
    }
}
