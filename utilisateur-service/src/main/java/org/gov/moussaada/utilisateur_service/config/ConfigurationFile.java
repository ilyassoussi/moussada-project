//package org.gov.moussaada.utilisateur_service.config;
//
//import feign.RequestInterceptor;
//import feign.RequestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.context.SecurityContextHolder;
//
//@Configuration
//@Slf4j
//public class ConfigurationFile {
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return template -> {
//            String token = "ton_jeton";  // Récupère le jeton à partir de ton contexte de sécurité
//            log.info("ici token : {}",SecurityContextHolder.getContext().getAuthentication());
//            template.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJub20iOiJBZG1pbiBNb3Vzc2FkYSIsImV4cCI6MTc0NDIxMTU1Nywicm9sZSI6eyJpZCI6MSwidHlwZV9yb2xlIjoiQWRtaW4ifSwic3ViIjoibW91c3NhYWRhLmFkbWluQG1vdXNzYWRhLWFkbWluLmNvbSJ9.SftM0TF93TlDak0PyHPcMTn61ZIJtvUyYuyZCxlvpxw" );
//        };
//    }
//}
