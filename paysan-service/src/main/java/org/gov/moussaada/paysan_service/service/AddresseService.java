package org.gov.moussaada.paysan_service.service;


import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.paysan_service.dao.AddresseDAO;
import org.gov.moussaada.paysan_service.dto.AddresseRequestDTO;
import org.gov.moussaada.paysan_service.dto.AddresseResponseDTO;
import org.gov.moussaada.paysan_service.dto.VilleReponseDTO;
import org.gov.moussaada.paysan_service.model.Addresse;
import org.gov.moussaada.paysan_service.model.Ville;
import org.gov.moussaada.paysan_service.response.ErrorResponse;
import org.gov.moussaada.paysan_service.response.SuccessResponse;
import org.gov.moussaada.paysan_service.service.inter.IAddresseService;
import org.gov.moussaada.paysan_service.utils.utile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class AddresseService implements IAddresseService {

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AddresseDAO addresseDAO;


    @Override
    public ResponseEntity<?> ValideAddresse(AddresseRequestDTO adreesRq) {
        if (adreesRq.getAddresse() == null
                || !utile.isValidEmail(adreesRq.getEmail())
                || adreesRq.getCode_postal() == null
                || adreesRq.getId_ville() == null
                || adreesRq.getGsm() == null
                || adreesRq.getQuartier() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error"));
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idUtilisateur = null;
        if (principal instanceof Map<?, ?>) {
            Map<String, Object> userDetails = (Map<String, Object>) principal;
            idUtilisateur = Long.valueOf(userDetails.get("id_utilisateur").toString());
        } else {
            log.warn("Principal n'est pas une Map : {}", principal.getClass());
        }

        Optional<Addresse> addresseExist = addresseDAO.findByUser(Math.toIntExact(idUtilisateur));
        try {
            if (addresseExist.isPresent()) {
                Addresse adress = modelMapper.map(adreesRq, Addresse.class);
                adress.setId(addresseExist.get().getId());
                adress.setId_paysan(Math.toIntExact(idUtilisateur));
                Addresse saved = addresseDAO.save(adress);
                return ResponseEntity.ok().body(new SuccessResponse<>("adresse updated with success", 200, modelMapper.map(saved, AddresseResponseDTO.class)));
            } else {
//                Addresse adress = modelMapper.map(adreesRq, Addresse.class);
//                adress.setId_paysan(Math.toIntExact(idUtilisateur));
                Addresse addresse = Addresse.builder()
                        .id_ville(adreesRq.getId_ville())
                        .code_postal(adreesRq.getCode_postal())
                        .addresse(adreesRq.getAddresse())
                        .gsm(adreesRq.getGsm())
                        .email(adreesRq.getEmail())
                        .quartier(adreesRq.getQuartier())
                        .id_paysan(Math.toIntExact(idUtilisateur))
                        .telephone_fixe(adreesRq.getTelephone_fixe())
                        .build();
                Addresse save = addresseDAO.save(addresse);

                if (save != null) {
                    AddresseResponseDTO savedResponse = modelMapper.map(save, AddresseResponseDTO.class);
                    return ResponseEntity.created(URI.create("asd")).body(new SuccessResponse<>("adresse Created with success", 201, savedResponse));
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("error"));
                }
            }
        } catch (org.hibernate.StaleObjectStateException e) {
            log.error("Erreur de concurrence, l'entité a été modifiée par une autre transaction", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Conflict: l'entité a été modifiée par une autre transaction"));
        }
    }

    @Override
    public ResponseEntity<?> getAdress() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idUtilisateur = null;
        if (principal instanceof Map<?, ?>) {
            Map<String, Object> userDetails = (Map<String, Object>) principal;
            idUtilisateur = Long.valueOf(userDetails.get("id_utilisateur").toString());
        } else {
            log.warn("Principal n'est pas une Map : {}", principal.getClass());
        }
        Optional<Addresse> addresseExist = addresseDAO.findByUser(Math.toIntExact(idUtilisateur));
        if(addresseExist.isPresent()){
            AddresseResponseDTO addresseResponseDTO = modelMapper.map(addresseExist.get() , AddresseResponseDTO.class);
            return ResponseEntity.ok().body(new SuccessResponse<>("l'addresse est exsit",200,addresseResponseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("N'existe pas");
        }
    }

    public ResponseEntity<?> getVille() {
        List<Ville> villes = addresseDAO.getAllVille();
        List<VilleReponseDTO> villeReponseDTOS = new ArrayList<>();
        for (Ville ville : villes){
            VilleReponseDTO villeReponseDTO = modelMapper.map(ville,VilleReponseDTO.class);
            villeReponseDTOS.add(villeReponseDTO);
        }
        if(villeReponseDTOS.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("error lors de import de ville"));
        } else {
            return ResponseEntity.ok().body(new SuccessResponse<>("success" , 200,villeReponseDTOS));
        }
    }
}
