package org.gov.moussaada.subventions_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.gov.moussaada.subventions_service.dto.KafkaMoussaadaDTO;
import org.gov.moussaada.subventions_service.dto.KafkaUpdateStatusDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor

public class KafkaSubventionService {

    private final NewTopic newTopic;


    private final KafkaTemplate<String, KafkaMoussaadaDTO> kafkaTemplateUpdateResponse;

    public void UpdateStatusDemande(KafkaMoussaadaDTO kafkaMoussaadaDTO) {
        Message<KafkaMoussaadaDTO> message = MessageBuilder.withPayload(kafkaMoussaadaDTO)
                .setHeader(KafkaHeaders.TOPIC,  newTopic.name())
                .build();
        kafkaTemplateUpdateResponse.send(message);
        log.info("ok : {}",message.getPayload());
     }
}
