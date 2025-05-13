package org.gov.moussaada.subventions_service.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.subventions_service.dao.ValidateSubventionDAO;
import org.gov.moussaada.subventions_service.dto.KafkaMoussaadaDTO;
import org.gov.moussaada.subventions_service.dto.KafkaUpdateStatusDTO;
import org.gov.moussaada.subventions_service.dto.TraitementSubventionRequest;
import org.gov.moussaada.subventions_service.feign.SharedFeign;
import org.gov.moussaada.subventions_service.model.TraitementSubvention;
import org.gov.moussaada.subventions_service.service.inter.ITraitementSubvention;
import org.gov.moussaada.subventions_service.utils.utile;
import org.gov.moussaada.utilisateur_service.response.ErrorResponse;
import org.gov.moussaada.utilisateur_service.response.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Getter
@Slf4j
@Setter
public class TraitementSubventionService implements ITraitementSubvention {

    @Autowired
    private ValidateSubventionDAO validateSubvention;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private KafkaSubventionService kafkaSubventionService;

    @Autowired
    private SharedFeign sharedFeign;

    @Override
    public ResponseEntity<?> CreateTraitement(TraitementSubventionRequest traitementSubventionRequest) {
        TraitementSubvention traitementSubvention = modelMapper.map(traitementSubventionRequest,TraitementSubvention.class);
        traitementSubvention.setDate_traitement(utile.CurentDate());
        traitementSubvention.setDate_update(utile.CurentDate());

        try{
            KafkaMoussaadaDTO kafkaMoussaadaDTO = new KafkaMoussaadaDTO("TRAITEMENT", new KafkaUpdateStatusDTO(traitementSubvention.getId_demande(), traitementSubvention.getStatus()));
            kafkaSubventionService.UpdateStatusDemande(kafkaMoussaadaDTO);
            /*
            * @TODO Avoir un appel avec KAFKA au service de Terrain
            */
            if(traitementSubvention.getStatus().equals("EN_ATTENTE_EVALUATION_TERRAIN")){
                System.out.println("ici le traitement d'envoyer la demande au service Technique (Terrain)");
            }
            TraitementSubvention saved = validateSubvention.save(traitementSubvention);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("created",201,saved));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> UpdateTraitement(int id , TraitementSubventionRequest traitementSubventionRequest) {
        TraitementSubvention traitementSubvention = validateSubvention.findById(id).get();
        if(traitementSubventionRequest.getMontantSubvention() != null) {
            traitementSubvention.setMontantSubvention(traitementSubventionRequest.getMontantSubvention());
        }

        if(traitementSubventionRequest.getDescription() != null && !traitementSubventionRequest.getDescription().isEmpty()) {
            traitementSubvention.setDescription(traitementSubventionRequest.getDescription());
        }

        if(traitementSubventionRequest.getStatus() != null && !traitementSubventionRequest.getStatus().isEmpty()) {
            traitementSubvention.setStatus(traitementSubventionRequest.getStatus());
            KafkaMoussaadaDTO kafkaMoussaadaDTO = new KafkaMoussaadaDTO("TRAITEMENT", new KafkaUpdateStatusDTO(traitementSubvention.getId_demande(), traitementSubvention.getStatus()));
            kafkaSubventionService.UpdateStatusDemande(kafkaMoussaadaDTO);
        }

        if(traitementSubventionRequest.getNombre_de_plan() != null) {
            traitementSubvention.setNombre_de_plan(traitementSubventionRequest.getNombre_de_plan());
        }
        traitementSubvention.setDate_update(utile.CurentDate());
        try{
            TraitementSubvention saved = validateSubvention.save(traitementSubvention);
            /*
             * @TODO Avoir un appel avec KAFKA au service de Terrain
             */
            if(traitementSubvention.getStatus().equals("EN_ATTENTE_EVALUATION_TERRAIN")){
                System.out.println("ici le traitement d'envoyer la demande au service Technique (Terrain)");
            }
            return ResponseEntity.ok().body(new SuccessResponse<>("updated",200,saved));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> GetAllTraitement() {
        return ResponseEntity.ok().body(new SuccessResponse<>("all traitement",200,validateSubvention.findAll()));
    }

    @Override
    public ResponseEntity<?> GetByIdDemande(int id) {
//        Optional<TraitementSubvention> traitementSubvention = validateSubvention.findById(id);
        TraitementSubvention traitementSubvention = validateSubvention.findByIDemanade(id);
        log.info("ici : {}",traitementSubvention);
        if(traitementSubvention == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Aucun traitement por cette demande"));
        } else {
            return ResponseEntity.ok().body(new SuccessResponse<>("voila les traitements par demandes de paysan",200,traitementSubvention));
        }
    }

    @Override
    public ResponseEntity<?> GetById(int id) {
        Optional<TraitementSubvention> traitementSubvention = validateSubvention.findById(id);
        if(!traitementSubvention.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Aucun traitement trouver"));
        } else {
            return ResponseEntity.ok().body(new SuccessResponse<>("voila le traitements ",200,traitementSubvention));
        }
    }

    @Override
    public ResponseEntity<?> Delete(int id) {
        try{
            validateSubvention.deleteById(id);
            return ResponseEntity.ok().body(new SuccessResponse<>("deleted with success",200,null));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> GetInfoDemande(Long id) {
        ResponseEntity<?> demande_paysan = sharedFeign.getInfoDemande(id);
        if(demande_paysan.getStatusCode().is2xxSuccessful()){
            return demande_paysan;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erreur lors de get info de demande"));
        }
    }
}
