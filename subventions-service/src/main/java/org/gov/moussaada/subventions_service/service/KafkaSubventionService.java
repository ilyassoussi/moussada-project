package org.gov.moussaada.subventions_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.gov.moussaada.common_dto.KafkaMoussaadaDTO;
import org.gov.moussaada.subventions_service.dao.DemandeTechniqueDAO;
import org.gov.moussaada.subventions_service.dto.KafkaUpdateStatusDTO;
import org.gov.moussaada.subventions_service.model.Demande_technique;
import org.gov.moussaada.subventions_service.model.Status_demande_technique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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

    @Autowired
    private DemandeTechniqueDAO demandeTechniqueDAO;

    @Autowired
    private ObjectMapper objectMapper;

    private final KafkaTemplate<String, KafkaMoussaadaDTO> kafkaTemplateUpdateResponse;

    public void UpdateStatusDemande(KafkaMoussaadaDTO kafkaMoussaadaDTO) {
        Message<KafkaMoussaadaDTO> message = MessageBuilder.withPayload(kafkaMoussaadaDTO)
                .setHeader(KafkaHeaders.TOPIC,  newTopic.name())
                .build();
        kafkaTemplateUpdateResponse.send(message);
        log.info("ok : {}",message.getPayload());
     }

    public void UpdateDemandeReclamation(int id,String status){
        Demande_technique demandeTechnique = demandeTechniqueDAO.findById(id).get();
        demandeTechnique.setId_reponse_technique(id);
        demandeTechnique.setStatusDemande(Status_demande_technique.valueOf(status));
        demandeTechniqueDAO.save(demandeTechnique);
        log.info("voila le nv reclamation : {}", demandeTechnique);
    }

    @KafkaListener( topics = "admin-topic", groupId = "Subvention-group" )

    public void handleAdminMessage(KafkaMoussaadaDTO message) {
        log.info("ici : {}",message);
        try {
            log.info("Message reçu : {}", message);
            switch (message.getType()) {
                case "TERRAIN":
                    KafkaUpdateStatusDTO traitementDto = objectMapper.convertValue(message.getPayload(), KafkaUpdateStatusDTO.class);
                    UpdateDemandeReclamation(traitementDto.getId(),traitementDto.getStatus());
                    break;
                default:
                    log.warn("Type de message inconnu : {}", message.getType());
            }
        } catch (Exception e) {
            log.error("Erreur lors du traitement du message Kafka : {}", e.getMessage(), e);
        }
    }

}
