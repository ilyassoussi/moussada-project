package org.gov.moussaada.subventions_service.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.gov.moussaada.subventions_service.dto.KafkaMoussaadaDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public Map<String, Object> kafkaConsumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // ou ton adresse Kafka
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "paysan-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return props;
    }

    @Bean
    public ConsumerFactory<String, KafkaMoussaadaDTO> kafkaMoussaadaConsumerFactory() {
        JsonDeserializer<KafkaMoussaadaDTO> deserializer = new JsonDeserializer<>(KafkaMoussaadaDTO.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return new DefaultKafkaConsumerFactory<>(
                kafkaConsumerConfigs(),
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean(name = "kafkaMoussaadaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, KafkaMoussaadaDTO> kafkaMoussaadaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, KafkaMoussaadaDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(kafkaMoussaadaConsumerFactory());
        return factory;
    }
}
