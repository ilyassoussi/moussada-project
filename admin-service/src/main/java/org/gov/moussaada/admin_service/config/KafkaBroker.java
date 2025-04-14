package org.gov.moussaada.admin_service.config;

import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaBroker {

    private final KafkaTemplate<String, ReclamationTraite> kafkaTemplate;

    public KafkaBroker(KafkaTemplate<String, ReclamationTraite> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void envoyerTraiteReclamtionAuPaysan(ReclamationTraite traitmentReclamation) {
        kafkaTemplate.send("paysan-topic", traitmentReclamation);
    }
}
