package org.gov.moussaada.admin_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.gov.moussaada.common_dto.KafkaMoussaadaDTO;
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

    private final KafkaTemplate<String, KafkaMoussaadaDTO> kafkaTemplateUpdateResponse;

    public void UpdateStatusReclmation(int id) {
        KafkaMoussaadaDTO kafkaMoussaadaDTO = new KafkaMoussaadaDTO("RECLAMATION",id);
        Message<KafkaMoussaadaDTO> message = MessageBuilder.withPayload(kafkaMoussaadaDTO)
                .setHeader(KafkaHeaders.TOPIC,  newTopic.name())
                .build();
        kafkaTemplateUpdateResponse.send(message);
        log.info("update reclamation status : {}",kafkaTemplateUpdateResponse.send(message));
    }
}
