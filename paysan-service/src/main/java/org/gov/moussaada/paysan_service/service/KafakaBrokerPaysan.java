package org.gov.moussaada.paysan_service.service;

import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafakaBrokerPaysan {

    @KafkaListener(topics = "paysan-topic", groupId = "paysan-group")
    public void recevoirMessage(ReclamationTraite message) {
            System.out.println("📩 Message reçu de admin-service : " + message);
            // Logique de traitement ici (validation, DB, etc.)
    }
}
