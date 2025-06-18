package org.gov.moussaada.admin_service.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.admin_service.dao.TraitementDAO;
import org.gov.moussaada.admin_service.dto.ReclamationReponseDTO;
import org.gov.moussaada.admin_service.dto.TraitementRequestDTO;
import org.gov.moussaada.admin_service.feign.PaysanFeign;
import org.gov.moussaada.admin_service.model.TraitmentReclamation;
import org.gov.moussaada.admin_service.response.ErrorResponse;
import org.gov.moussaada.admin_service.response.SuccessResponse;
import org.gov.moussaada.admin_service.service.inter.ITraitementService;
import org.gov.moussaada.admin_service.utils.utile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class TraitementService implements ITraitementService {

    @Autowired
    private PaysanFeign paysanFeign;
    @Autowired
    private TraitementDAO traitementDAO;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private KafkaAdminService kafkaAdminService;


    @Override
    public ResponseEntity<?> CreateTraitement(int id , TraitementRequestDTO traitementRequestDTO) {
        ResponseEntity<?> reclamationResponse = paysanFeign.geById(id);
        if (!reclamationResponse.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Reclamation non trouvée"));
        }
        TraitmentReclamation traitmentReclamation = TraitmentReclamation.builder()
                .date_creation_reclamation(utile.CurentDate())
                .id_reclamation(id)
                .reponse(traitementRequestDTO.getReponse())
                .build();
        TraitmentReclamation savedTra = traitementDAO.save(traitmentReclamation);
        if(savedTra != null) {
            kafkaAdminService.UpdateStatusReclmation(id);
            return paysanFeign.updateReclamationById(id);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error"));
        }
    }

    @Override
    public ResponseEntity<?> GetAll() {
        ResponseEntity<?> reclamation =  paysanFeign.getAll();
        if (reclamation.getStatusCode().is2xxSuccessful()){
            return reclamation;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("aucune reclamation No traite"));
        }
    }

    @Override
    public ResponseEntity<?> GetById(int id) {
        Optional<TraitmentReclamation> byId = traitementDAO.findById(id);
        if(byId.isPresent()){
            return ResponseEntity.ok().body(new SuccessResponse<>("success",200,byId.stream().collect(Collectors.toList())));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("error"));
        }
    }

    @Override
    public ResponseEntity<?> GetByIdByReclamation(int id) {
        try{
            TraitmentReclamation traitmentReclamation = traitementDAO.gettraitementByReclamation(id);
            if(traitmentReclamation == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("reponse n'existe as pour cette reclamation"));
            }
            return ResponseEntity.ok().body(new SuccessResponse<>("reponse",200,traitmentReclamation));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("error , "+ e));
        }
    }
}
