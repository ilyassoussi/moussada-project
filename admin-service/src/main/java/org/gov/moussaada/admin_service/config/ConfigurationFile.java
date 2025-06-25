package org.gov.moussaada.admin_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
@Slf4j
public class ConfigurationFile {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Bean
    public NewTopic topic(){
        log.info("Creating Kafka topic: {}", topicName);
        return TopicBuilder.name(topicName).build();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getCredentials() instanceof String) {
                String token = (String) auth.getCredentials();
                if (token != null && !token.isEmpty()) {
                    requestTemplate.header("Authorization", "Bearer " + token);
                }
            } else {
                log.warn("No JWT token found in SecurityContext");
            }
            requestTemplate.header("X-Internal-Call", "true");
        };
    }


}
