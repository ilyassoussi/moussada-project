package org.gov.moussada.shared_micro.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
@Slf4j
public class ConfigurationFileShared {

    
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                String token = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
                log.info("ici : {}",token);
                requestTemplate.header("Authorization", "Bearer " + token);
            }
        };
    }
}
