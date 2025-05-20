package org.gov.moussaada.paysan_service.config;

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
public class ConfigurationFilePaysan {

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(topicName).build();
    }


    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            var authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getCredentials() != null) {
                String token = (String) authentication.getCredentials();
                log.info("ici : {}", token);
                requestTemplate.header("Authorization", "Bearer " + token);
            } else {
                log.warn("Aucun token JWT trouvé dans le SecurityContext");
            }
        };
    }

}
