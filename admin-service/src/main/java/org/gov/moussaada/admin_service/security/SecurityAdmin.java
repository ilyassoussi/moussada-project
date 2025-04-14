package org.gov.moussaada.admin_service.security;

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
        registry.addMapping("/admin/**")
                .allowedOrigins("http://localhost:3000") // Origine autorisée
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(authori -> authori
                                .requestMatchers("/admin/actualite/getall" , "/admin/actualite/{id}", "/actuator/health").permitAll()
                                .requestMatchers("/admin/actualite/create").hasAuthority("ROLE_Admin")
                                .requestMatchers("/admin/actualite/delete/**").hasAuthority("ROLE_Admin")
                                .requestMatchers("/admin/actualite/update/**").hasAuthority("ROLE_Admin")
                                .requestMatchers("/admin/reclamation/create","admin/reclamation").hasAuthority("ROLE_Admin")
                                .requestMatchers("/admin/reclamation/{id}").hasAuthority("ROLE_Paysan")
                                .requestMatchers("/admin/reclamation/reponse/{id}").hasAuthority("ROLE_Paysan")
                                .anyRequest().authenticated()
                        ).sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                        )
                        .addFilterBefore(jwtAuthenticationFilter , UsernamePasswordAuthenticationFilter.class)
                        .build();
    }
}
