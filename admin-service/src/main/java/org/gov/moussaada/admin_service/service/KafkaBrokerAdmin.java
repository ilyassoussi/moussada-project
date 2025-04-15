package org.gov.moussaada.admin_service.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaBrokerAdmin {

    private final NewTopic newTopic;

    private final KafkaTemplate<String, ReclamationTraite> kafkaTemplate;

    public KafkaBrokerAdmin(NewTopic newTopic, KafkaTemplate<String, ReclamationTraite> kafkaTemplate) {
        this.newTopic = newTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void envoyerTraiteReclamtionAuPaysan(ReclamationTraite traitmentReclamation) {
        Message<ReclamationTraite> message = MessageBuilder.withPayload(traitmentReclamation)
                                            .setHeader(KafkaHeaders.TOPIC,  newTopic.name())
                                            .build();
        log.info("ici : {}",message);
        kafkaTemplate.send(message);
    }
}
