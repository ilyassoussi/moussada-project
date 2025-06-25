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
//public class FeignClientConfig {
//
//    @Bean
//    public RequestInterceptor requestInterceptor() {
//        return (RequestTemplate requestTemplate) -> {
//            var authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication != null && authentication.getCredentials() != null) {
//                String token = (String) authentication.getCredentials();
//                log.info("Propagation du token JWT dans utilisateur-service : {}", token);
//                requestTemplate.header("Authorization", "Bearer " + token);
//            } else {
//                log.warn("Aucun token JWT trouvé dans SecurityContext (utilisateur-service)");
//            }
//        };
//    }
//}
