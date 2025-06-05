package org.gov.moussaada.subventions_service.service;

import org.gov.moussaada.subventions_service.dao.DemandeTechniqueDAO;
import org.gov.moussaada.subventions_service.dto.DemandeTechniqueRequestDTO;
import org.gov.moussaada.subventions_service.dto.DemandeTechniqueResponseDTO;
import org.gov.moussaada.subventions_service.feign.SharedFeign;
import org.gov.moussaada.subventions_service.model.Demande_technique;
import org.gov.moussaada.subventions_service.model.Status_demande_technique;
import org.gov.moussaada.subventions_service.response.ErrorResponse;
import org.gov.moussaada.subventions_service.response.SuccessResponse;
import org.gov.moussaada.subventions_service.service.inter.IDemandeTechnique;
import org.gov.moussaada.subventions_service.utils.utile;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeTechniqueService implements IDemandeTechnique {

    private DemandeTechniqueDAO demandeTechniqueDAO;

    private ModelMapper modelMapper;

    private SharedFeign sharedFeign;

    public DemandeTechniqueService(DemandeTechniqueDAO demandeTechniqueDAO, ModelMapper modelMapper, SharedFeign sharedFeign) {
        this.demandeTechniqueDAO = demandeTechniqueDAO;
        this.modelMapper = modelMapper;
        this.sharedFeign = sharedFeign;
    }

    @Override
    public ResponseEntity<?> create(DemandeTechniqueRequestDTO demandeTechniqueRequestDTO) {
        Demande_technique demandeTechnique = Demande_technique.builder()
                .id_traitent_demande(demandeTechniqueRequestDTO.getId_traitent_demande())
                .statusDemande(Status_demande_technique.EN_ATTENTE)
                .date_creation(utile.CurentDate())
                .date_de_sortie(utile.ReformulateDate(demandeTechniqueRequestDTO.getDate_de_sortie()))
                .titre(demandeTechniqueRequestDTO.getTitre())
                .description(demandeTechniqueRequestDTO.getDescription())
                .Date_update(utile.CurentDate())
                .build();
        try {
            demandeTechniqueDAO.save(demandeTechnique);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("created with success",201,modelMapper.map(demandeTechnique,DemandeTechniqueResponseDTO.class)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> update(int id, @RequestBody DemandeTechniqueRequestDTO demandeTechniqueRequestDTO) {
        Demande_technique demandeTechnique = demandeTechniqueDAO.findById(id).get();
        if(demandeTechniqueRequestDTO.getTitre() != null || !demandeTechniqueRequestDTO.getTitre().isEmpty()){
            demandeTechnique.setTitre(demandeTechniqueRequestDTO.getTitre());
        }
        if(demandeTechniqueRequestDTO.getDescription() != null || !demandeTechniqueRequestDTO.getDescription().isEmpty()){
            demandeTechnique.setDescription(demandeTechniqueRequestDTO.getDescription());
        }
        if(demandeTechniqueRequestDTO.getDate_de_sortie() != null || !demandeTechniqueRequestDTO.getDate_de_sortie().isEmpty()){
            demandeTechnique.setDate_de_sortie(utile.ReformulateDate(demandeTechniqueRequestDTO.getDate_de_sortie()));
        }
        demandeTechnique.setDate_update(utile.CurentDate());
        try{
            demandeTechniqueDAO.save(demandeTechnique);
            return ResponseEntity.ok().body(new SuccessResponse<>("updated with success",200,modelMapper.map(demandeTechnique,DemandeTechniqueResponseDTO.class)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<Demande_technique> all = demandeTechniqueDAO.findAll();
        if(all.isEmpty()){
            return ResponseEntity.ok().body(new ErrorResponse("aucune demande existe"));
        }else {
            return ResponseEntity.ok().body(new SuccessResponse<>("All demande existe",200,all));
        }
    }

    @Override
    public ResponseEntity<?> getById(int id) {
        Demande_technique demandeTechnique = demandeTechniqueDAO.findById(id).get();
        if(demandeTechnique == null){
            return ResponseEntity.ok().body(new ErrorResponse("aucune demande existe"));
        }else {
            return ResponseEntity.ok().body(new SuccessResponse<>("All demande existe",200,demandeTechnique));
        }
    }

    @Override
    public ResponseEntity<?> Delete(int id) {
        demandeTechniqueDAO.deleteById(id);
        Optional<Demande_technique> demandeTechnique = demandeTechniqueDAO.findById(id);
        if(!demandeTechnique.isPresent()){
            return ResponseEntity.ok().body(new SuccessResponse<>("Delete success",200,null));
        }else {
            return ResponseEntity.ok().body(new ErrorResponse("All demande existe"));
        }
    }

    @Override
    public ResponseEntity<?> getAllNotFinished() {
        List<Demande_technique> AllNOtfinished = demandeTechniqueDAO.NotFinished();
        if(AllNOtfinished.isEmpty()) {
            return ResponseEntity.ok().body(new SuccessResponse<>("tout les demande sont traite par le service technique",200,AllNOtfinished));
        } else {
            return ResponseEntity.ok().body(new SuccessResponse<>("voila la liste",200,AllNOtfinished));
        }
    }

    @Override
    public ResponseEntity<?> getResponseById(int id) {
        ResponseEntity<?> reponse = sharedFeign.getResponseById(id);
        if(reponse.getStatusCode().is2xxSuccessful()){
            return reponse;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("n'existe pas"));
        }
    }

    @Override
    public ResponseEntity<?> getAllRapport() {
        ResponseEntity<?> getAllRapport = sharedFeign.getRapport();
        if(getAllRapport.getStatusCode().is2xxSuccessful()){
            return getAllRapport;
        } else {
            return getAllRapport;
        }
    }
}
