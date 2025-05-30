package org.gov.moussaada.utilisateur_service.security;


import org.gov.moussaada.utilisateur_service.service.UtilisateurSevice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class ConfigWebSecurity {

    private UtilisateurSevice utilisateurSevice;
    private final JwtFilter jwtFilter;


    public ConfigWebSecurity(@Lazy UtilisateurSevice utilisateurSevice ,@Lazy JwtFilter jwtFilter) {
        this.utilisateurSevice = utilisateurSevice;
        this.jwtFilter = jwtFilter;
    }

//    /**
//     *
//     * @param http kanakhode la requette mn head o kanshof wash atkone valid 3la hssabe list de controle dyalna
//     * @return katreturni lina wahd list controle li khass ikono bash ikone dakshy valide
//     * @throws Exception de session pour la creation de token
//     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.csrf(AbstractHttpConfigurer::disable)
                        .authorizeHttpRequests(authori -> authori
                                .requestMatchers("/utilisateur/auth/**" , "/utilisateur/auth/pdf/download/**", "/actuator/health").permitAll()
                                .requestMatchers("/utilisateur/compte/**").hasAuthority("ROLE_Admin")
                                .anyRequest().authenticated()
                        ).sessionManagement(httpSecuritySessionManagementConfigurer ->
                                httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                )
                        .addFilterBefore(jwtFilter , UsernamePasswordAuthenticationFilter.class)
                        .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authen) throws Exception {
        return authen.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(utilisateurSevice);
        daoAuthenticationProvider.setPasswordEncoder(this.passwordEncoder());
        return daoAuthenticationProvider;
    }
}
