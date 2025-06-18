package org.gov.moussaada.paysan_service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.paysan_service.dao.DemandeSubventionDAO;
import org.gov.moussaada.paysan_service.dao.ReclamationDAO;
import org.gov.moussaada.common_dto.KafkaMoussaadaDTO;
import org.gov.moussaada.paysan_service.dto.KafkaUpdateStatusDTO;
import org.gov.moussaada.paysan_service.model.DemandeSubvention;
import org.gov.moussaada.paysan_service.model.Reclamation;
import org.gov.moussaada.paysan_service.model.Status_demande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Slf4j
public class KafakaPaysanService {

    @Autowired
    private ReclamationDAO reclamationDAO;

    @Autowired
    private DemandeSubventionDAO demandeSubventionDAO;

    @Autowired
    private ObjectMapper objectMapper;

    public void UpdateStatusReclamation(int id){
        Reclamation reclamation = reclamationDAO.findById(id).get();
        reclamation.setInTreatment(true);
        reclamation.setId_reclamation(id);
        reclamationDAO.save(reclamation);
        log.info("voila le nv reclamation : {}", reclamation);
    }

    public void updateStatusDemande(KafkaUpdateStatusDTO dto) {
        log.info("ici , {}",dto);
        DemandeSubvention demande = demandeSubventionDAO.findById((long) dto.getId())
                .orElseThrow(() -> new RuntimeException("Demande non trouvée"));

        demande.setStatusDemande(Status_demande.valueOf(dto.getStatus()));
        demandeSubventionDAO.save(demande);

        log.info("Demande mise à jour : {}", demande);
    }

    @KafkaListener( topics = "admin-topic", groupId = "paysan-group" )

    public void handleAdminMessage(KafkaMoussaadaDTO message) {
        log.info("ici : {}",message);
        try {
            log.info("Message reçu : {}", message);
            switch (message.getType()) {
                case "RECLAMATION":
                    int reclamationDto = objectMapper.convertValue(message.getPayload(), Integer.class);
                    UpdateStatusReclamation(reclamationDto);
                    break;
                case "TRAITEMENT":
                    KafkaUpdateStatusDTO traitementDto = objectMapper.convertValue(message.getPayload(), KafkaUpdateStatusDTO.class);
                    updateStatusDemande(traitementDto);
                    break;
                default:
                    log.warn("Type de message inconnu : {}", message.getType());
            }
        } catch (Exception e) {
            log.error("Erreur lors du traitement du message Kafka : {}", e.getMessage(), e);
        }
    }

}
