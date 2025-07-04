package org.gov.moussaada.paysan_service.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.paysan_service.dao.ReclamationDAO;
import org.gov.moussaada.paysan_service.dto.ReclamationReponseDTO;
import org.gov.moussaada.paysan_service.dto.ReclamationRequestDTO;
import org.gov.moussaada.paysan_service.model.Reclamation;
import org.gov.moussaada.paysan_service.response.ErrorResponse;
import org.gov.moussaada.paysan_service.response.SuccessResponse;
import org.gov.moussaada.paysan_service.service.inter.IReclamationService;
import org.gov.moussaada.paysan_service.utils.utile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
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

    @Autowired
    private KafakaPaysanService kafakaBrokerPaysan;

    @Override
    public ResponseEntity<?> CreateReclamation(ReclamationRequestDTO reclamationRequestDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> userDetails = (Map<String, Object>) principal;
        Long idUtilisateur = Long.valueOf(userDetails.get("id_utilisateur").toString());

        Reclamation reclamation = modelMapper.map(reclamationRequestDTO, Reclamation.class);
        reclamation.setDate_creation(utile.CurentDate());
        reclamation.setId_paysan(Math.toIntExact(idUtilisateur));
        reclamation.setInTreatment(false);
        if (utile.isValidEmail(reclamation.getEmail())) {
            Reclamation SaveReclamation = reclamationDAO.save(reclamation);
            log.info("here : {}", SaveReclamation);
            if (SaveReclamation != null) {
                ReclamationReponseDTO reclamationReponseDTO = modelMapper.map(reclamation, ReclamationReponseDTO.class);
                return ResponseEntity.ok().body(new SuccessResponse<>("create with success", 201, reclamationReponseDTO));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error lors de creation"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("email est incorrect"));
        }
    }

    /*
    * @TODO
    *   probleme icicicicidcisa!!!!!!!!!!!!
    */
    @Override
    public ResponseEntity<?> ReclamaTiondejatraite(int id) {
        try{
            return ResponseEntity.ok().body(new SuccessResponse<>("voila la reponse",200,null));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+e);
        }
    }

    @Override
    public ResponseEntity<?> GetAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> userDetails = (Map<String, Object>) principal;
        Long idUtilisateur = Long.valueOf(userDetails.get("id_utilisateur").toString());

        List<Reclamation> reclamation =  reclamationDAO.findByUser(Math.toIntExact(idUtilisateur));
        if (!reclamation.isEmpty()){
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
    public ResponseEntity<?> GetEncours() {
        List<Reclamation> reclamation =  reclamationDAO.findAllByInTreatment();
        if (reclamation != null){
            return ResponseEntity.ok().body(new SuccessResponse<>("exist",200,reclamation));
        } else {
            return null;
        }
    }

    @Override
    public ResponseEntity<?> GetById(int id) {
        ReclamationReponseDTO reclamationReponseDTO = modelMapper.map(reclamationDAO.findById(id).get(),ReclamationReponseDTO.class);
        return ResponseEntity.ok().body(new SuccessResponse<>("exist",200,reclamationReponseDTO));

    }

    @Override
    public ResponseEntity<?> updateReclamation(int id) {
        Reclamation reclamation = reclamationDAO.findById(id).get();
        reclamation.setInTreatment(true);
        reclamation.setId_reclamation(id);
        reclamationDAO.save(reclamation);
        return ResponseEntity.ok().body(new SuccessResponse<>("exist",200,reclamation));
    }

}
