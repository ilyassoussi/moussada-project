package org.gov.moussaada.admin_service.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
@AllArgsConstructor

public class KafkaAdminService {

    private final NewTopic newTopic;

    private final KafkaTemplate<String, ReclamationTraite> kafkaTemplate;

    private final KafkaTemplate<String, Integer> kafkaTemplateUpdateResponse;

    public void UpdateStatusReclmation(int id) {
        Message<Integer> message = MessageBuilder.withPayload(id)
                .setHeader(KafkaHeaders.TOPIC,  newTopic.name())
                .build();
        kafkaTemplateUpdateResponse.send(message);
        log.info("update reclamation status : {}",kafkaTemplateUpdateResponse.send(message));
    }
}
