package org.gov.moussaada.paysan_service.service;

import lombok.*;
import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KafakaBrokerPaysan {

    private ReclamationTraite lastMessage;

    @KafkaListener(topics = "admin-topic", groupId = "paysan-group")
    public void recevoirMessage(ReclamationTraite message) {
        System.out.println("📩 Message reçu de admin-service : " + message);
        this.lastMessage = message;
    }

    public ReclamationTraite getLastMessage() {
        return lastMessage;
    }
}
