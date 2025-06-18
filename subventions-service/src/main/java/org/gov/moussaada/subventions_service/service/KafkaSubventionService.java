package org.gov.moussaada.subventions_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.gov.moussaada.common_dto.KafkaMoussaadaDTO;
import org.gov.moussaada.subventions_service.dao.DemandeTechniqueDAO;
import org.gov.moussaada.subventions_service.dto.KafkaUpdateStatusTerrain;
import org.gov.moussaada.subventions_service.model.Demande_technique;
import org.gov.moussaada.subventions_service.model.Status_demande_technique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    public void UpdateDemandeReclamation(int id, String status, Date dateSortie, int idReponse){
        Demande_technique demandeTechnique = demandeTechniqueDAO.findByIdTraitementDemande(id);
        log.info("voila le nv demandeTechnique : {}",  demandeTechnique );
        demandeTechnique.setId_reponse_technique(idReponse);
        demandeTechnique.setStatusDemande(Status_demande_technique.valueOf(status));
        demandeTechnique.setDate_de_sortie(dateSortie);
        demandeTechniqueDAO.save(demandeTechnique);
        log.info("voila le nv reclamation : {}", demandeTechnique);
    }

    @KafkaListener( topics = "admin-topic", groupId = "subventions-group" )

    public void handleSubventionMessage(KafkaMoussaadaDTO message) {
        log.info("ici : {}",message);
        try {
            log.info("Message reçu : {}", message);
            switch (message.getType()) {
                case "TERRAIN":
                    KafkaUpdateStatusTerrain traitementDto = objectMapper.convertValue(message.getPayload(), KafkaUpdateStatusTerrain.class);
                    log.info("Message reçu kafdakaTraitement : {}", traitementDto);
                    UpdateDemandeReclamation(traitementDto.getId_demande_technique(),traitementDto.getStatus(),traitementDto.getDate_sortie(),traitementDto.getId_reponse());
                    break;
                default:
                    log.warn("Type de message inconnu : {}", message.getType());
            }
        } catch (Exception e) {
            log.error("Erreur lors du traitement du message Kafka : {}", e.getMessage(), e);
        }
    }

}
