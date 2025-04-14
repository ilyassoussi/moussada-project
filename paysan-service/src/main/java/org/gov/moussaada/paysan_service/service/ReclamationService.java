package org.gov.moussaada.paysan_service.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//import org.gov.moussaada.admin_service.model.TraitmentReclamation;
import org.gov.moussaada.paysan_service.dao.ReclamationDAO;
import org.gov.moussaada.paysan_service.dto.ReclamationReponseDTO;
import org.gov.moussaada.paysan_service.dto.ReclamationRequestDTO;
//import org.gov.moussaada.paysan_service.feign.AdminFeign;
import org.gov.moussaada.paysan_service.model.Reclamation;
import org.gov.moussaada.paysan_service.response.ErrorResponse;
import org.gov.moussaada.paysan_service.response.SuccessResponse;
import org.gov.moussaada.paysan_service.service.inter.IReclamationService;
import org.gov.moussaada.paysan_service.utils.utile;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Service
public class ReclamationService implements IReclamationService {

    @Autowired
    private ReclamationDAO reclamationDAO;
    @Autowired
    private ModelMapper modelMapper;

//    @Autowired
//    AdminFeign adminFeign;

    @Override
    public ResponseEntity<?> CreateReclamation(ReclamationRequestDTO reclamationRequestDTO) {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Reclamation reclamation = modelMapper.map(reclamationRequestDTO,Reclamation.class);
        reclamation.setDate_creation(utile.CurentDate());
        reclamation.setId_user((long) utilisateur.getId());
        reclamation.setInTreatment(false);
        if(utile.isValidEmail(reclamation.getEmail())){
            Reclamation SaveReclamation = reclamationDAO.save(reclamation);
            if(SaveReclamation != null){
                ReclamationReponseDTO reclamationReponseDTO = modelMapper.map(reclamation,ReclamationReponseDTO.class);
                return ResponseEntity.ok().body(new SuccessResponse<>("create with success" , 201 , reclamationReponseDTO));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error lors de creation"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("email est incorrect"));
        }
    }

    @Override
    public ResponseEntity<?> ReclamaTiondejatraite(int id) {
//        try{
//            TraitmentReclamation reponse = adminFeign.getReponse(id);
//            return ResponseEntity.ok().body(new SuccessResponse<>("voila la reponse",200,reponse));
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body("Error: "+e);
//        }
        return null;
    }

    @Override
    public ResponseEntity<?> GetAll() {
        Utilisateur utilisateur = (Utilisateur) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Reclamation> reclamation =  reclamationDAO.findByUser(utilisateur.getId());
        if (reclamation != null){
            return ResponseEntity.ok().body(new SuccessResponse<>("exist",200,reclamation.stream().collect(Collectors.toList())));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("aucune reclamation"));
        }
    }

    @Override
    public ResponseEntity<?> GetReclamationById(int id) {
        Optional<Reclamation> reclamation =  reclamationDAO.findById(id);
        if (reclamation.isPresent()){
            ReclamationReponseDTO reclamationReponseDTO = modelMapper.map(reclamation.get(),ReclamationReponseDTO.class);
            return ResponseEntity.ok().body(new SuccessResponse<>("exist",200,reclamationReponseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("aucune reclamation"));
        }
    }

    @Override
    public List<Reclamation> GetEncours() {
        List<Reclamation> reclamation =  reclamationDAO.findAllByInTreatment();
        if (reclamation != null){
            return reclamation;
        } else {
            return null;
        }
    }

    @Override
    public Reclamation GetById(int id) {
        return reclamationDAO.findById(id).get();
    }

    @Override
    public Reclamation updateReclamation(int id) {
        Reclamation reclamation = reclamationDAO.findById(id).get();
        reclamation.setInTreatment(true);
        reclamation.setId_reclamation(id);
        reclamationDAO.save(reclamation);
        return reclamation;
    }

}
