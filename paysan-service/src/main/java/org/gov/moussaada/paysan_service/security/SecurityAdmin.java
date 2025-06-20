package org.gov.moussaada.paysan_service.security;

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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(authori -> authori
                                .requestMatchers("/actuator/*").permitAll()
                                .requestMatchers("/paysan/reclamation/create","/paysan/reclamation").hasAuthority("ROLE_Paysan")
                                .requestMatchers("/paysan/reclamation/{id}","/paysan/reclamation/update/{id}").hasAnyAuthority("ROLE_Paysan","ROLE_Admin")
                                .requestMatchers("/paysan/demande/{id}","/paysan/demande").hasAnyAuthority("ROLE_Paysan","ROLE_Subvention","ROLE_Service_terrain")
                                .requestMatchers("/paysan/demande/paysan","/paysan/demande/create","/paysan/demande/update/{id}").hasAuthority("ROLE_Paysan")
                                .requestMatchers("/paysan/addresse/**").hasAuthority("ROLE_Paysan")
                                .requestMatchers("/paysan/reclamation/encours").hasAuthority("ROLE_Admin")
                                .anyRequest().authenticated()
                        ).sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)
                        .build();
    }
}
