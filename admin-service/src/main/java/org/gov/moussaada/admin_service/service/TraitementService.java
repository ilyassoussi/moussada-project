package org.gov.moussaada.admin_service.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.admin_service.dao.TraitementDAO;
import org.gov.moussaada.admin_service.dto.TraitementRequestDTO;
//import org.gov.moussaada.admin_service.feign.PaysanFeign;
import org.gov.moussaada.admin_service.feign.PaysanFeign;
import org.gov.moussaada.admin_service.model.TraitmentReclamation;
import org.gov.moussaada.admin_service.response.ErrorResponse;
import org.gov.moussaada.admin_service.response.SuccessResponse;
import org.gov.moussaada.admin_service.service.inter.ITraitementService;
//import org.gov.moussaada.admin_service.utils.utile;
//import org.gov.moussaada.paysan_service.model.Reclamation;
import org.gov.moussaada.admin_service.utils.utile;
import org.gov.moussaada.paysan_service.model.Reclamation;
import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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


    @Override
    public ResponseEntity<?> CreateTraitement(int id , TraitementRequestDTO traitementRequestDTO) {
        Reclamation reclamation = paysanFeign.geById(id);
        TraitmentReclamation traitmentReclamation = TraitmentReclamation.builder()
                .date_creation_reclamation(utile.CurentDate())
                .id_reclamation(reclamation.getId_reclamation())
                .reponse(traitementRequestDTO.getReponse())
                .build();
        TraitmentReclamation savedTra = traitementDAO.save(traitmentReclamation);
        if(savedTra != null) {
            paysanFeign.updateReclamationById(id);
            return ResponseEntity.ok().body(new SuccessResponse<>("traitement est effectue",201,paysanFeign.updateReclamationById(id)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error"));
        }
    }

    @Override
    public ResponseEntity<?> GetAll() {
        List<Reclamation> reclamation =  paysanFeign.getAll();
        if (!reclamation.isEmpty()){
            return ResponseEntity.ok().body(new SuccessResponse<>("exist",200,reclamation.stream().collect(Collectors.toList())));
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
    public ReclamationTraite GetByIdByReclamation(int id) {
        try{
            TraitmentReclamation traitmentReclamation = traitementDAO.gettraitementByReclamation(id);
            ReclamationTraite reclamationTraite = ReclamationTraite.builder()
                    .id_reclamation(traitmentReclamation.getId_reclamation())
                    .reponse(traitmentReclamation.getReponse())
                    .build();
            return reclamationTraite;
        } catch (Exception e){
            return null;
        }
    }
}
