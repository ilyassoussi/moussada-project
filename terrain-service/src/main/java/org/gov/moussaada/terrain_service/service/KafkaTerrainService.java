package org.gov.moussaada.terrain_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.gov.moussaada.common_dto.KafkaMoussaadaDTO;
import org.gov.moussaada.terrain_service.dao.RapportDAO;
import org.gov.moussaada.terrain_service.dto.KafkaUpdateStatusDTO;
import org.gov.moussaada.terrain_service.model.Rapport;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor

public class KafkaTerrainService {

    private final NewTopic newTopic;

    private final KafkaTemplate<String, KafkaMoussaadaDTO> kafkaTemplateUpdateResponse;

    private RapportDAO rapportDAO;

    private ObjectMapper objectMapper;

    public void SendIdReponseTraitementDemandeSubvention( KafkaMoussaadaDTO kafkaMoussaadaDTO) {
        Message<KafkaMoussaadaDTO> message = MessageBuilder.withPayload(kafkaMoussaadaDTO)
                .setHeader(KafkaHeaders.TOPIC,  newTopic.name())
                .build();
        kafkaTemplateUpdateResponse.send(message);
        log.info("update reclamation status : {}",kafkaTemplateUpdateResponse.send(message));
    }

    public void UpdateStatusRapport(int idRapport , boolean isvalid){
        Optional<Rapport> rapport = rapportDAO.findById(idRapport);
        if(rapport.isPresent()){
            rapport.get().setIsvalid(isvalid);
            rapportDAO.save(rapport.get());
        } else{
            log.info("not found");
        }
    }

    @KafkaListener( topics = "admin-topic", groupId = "terrain-group" )

    public void handleSubventionMessage(KafkaMoussaadaDTO message) {
        try {
            switch (message.getType()) {
                case "TERRAIN":
                    KafkaUpdateStatusDTO traitementDto = objectMapper.convertValue(message.getPayload(), KafkaUpdateStatusDTO.class);
                    log.info("Message reçu kafdakaTraitement : {}", traitementDto);
                    UpdateStatusRapport(traitementDto.getId(), Boolean.parseBoolean(traitementDto.getStatus()));
                    break;
                default:
                    log.warn("Type de message inconnu : {}", message.getType());
            }
        } catch (Exception e) {
            log.error("Erreur lors du traitement du message Kafka : {}", e.getMessage(), e);
        }
    }
}
