package org.gov.moussaada.terrain_service.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.gov.moussaada.subventions_service.dto.KafkaMoussaadaDTO;
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
