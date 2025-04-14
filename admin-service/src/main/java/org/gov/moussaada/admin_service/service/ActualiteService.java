package org.gov.moussaada.admin_service.service;


import org.gov.moussaada.admin_service.dao.ActualiteDAO;
import org.gov.moussaada.admin_service.dto.ActualiteReponseDTO;
import org.gov.moussaada.admin_service.dto.ActualiteRequestDTO;
import org.gov.moussaada.admin_service.model.Actualite;
import org.gov.moussaada.admin_service.response.ErrorResponse;
import org.gov.moussaada.admin_service.response.SuccessResponse;
import org.gov.moussaada.admin_service.service.inter.IActualiteService;
import org.gov.moussaada.admin_service.utils.utile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActualiteService implements IActualiteService {

    @Autowired
    private ActualiteDAO actualitedao;
    @Autowired
    private ModelMapper modelmapper;

    @Override
    public ResponseEntity<?> save(ActualiteRequestDTO actualiteRQ) {
        actualiteRQ.setDate_creation(utile.CurentDate());
        Actualite actualite = modelmapper.map(actualiteRQ, Actualite.class); // convertir actualiteRQ >> Actualite.class
        Actualite saved = actualitedao.save(actualite);
        if (saved != null) {
            ActualiteReponseDTO responseDTO = modelmapper.map(saved, ActualiteReponseDTO.class);
            return ResponseEntity.created(URI.create(String.format("/actualite/%d", responseDTO.getId())))
                    .body(new SuccessResponse<>("Actualite Added successfully",201,responseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actualite does not add"));
        }
    }

    @Override
    public ResponseEntity<?> findById(Integer id) {
        Optional<Actualite> actualiteOptional = actualitedao.findById(id);

        if (actualiteOptional.isPresent()) {
            ActualiteReponseDTO responseDTO = modelmapper.map(actualiteOptional.get(), ActualiteReponseDTO.class);
            return ResponseEntity.ok(new SuccessResponse<>("Actualite found successfully",200,responseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actualite with id " + id + " not found"));
        }
    }

    @Override
    public ResponseEntity<?> findByTitre(String titre) {
        Actualite actualite = actualitedao.findByTitre(titre);
        if(actualite!=null){
            ActualiteReponseDTO responseDTO = modelmapper.map(actualite, ActualiteReponseDTO.class);
            return ResponseEntity.ok(new SuccessResponse<>("Actualite found successfully",200,responseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actualite with title " + titre + " not found"));
        }
    }

    @Override
    public ResponseEntity<?> update(ActualiteRequestDTO actualiteRQ, Integer id) {
        actualiteRQ.setDate_creation(utile.CurentDate());
        Optional<Actualite> existingActualite = actualitedao.findById(id);
        if(existingActualite.isPresent()){

            Actualite updatedActu = modelmapper.map(actualiteRQ, Actualite.class);
            updatedActu.setId(existingActualite.get().getId());
            if(updatedActu.getPdf() == null){
                updatedActu.setPdf(existingActualite.get().getPdf());
            }
            Actualite updated = actualitedao.save(updatedActu);
            ActualiteReponseDTO responseDTO = modelmapper.map(updated,ActualiteReponseDTO.class);
            return ResponseEntity.accepted().body(new SuccessResponse<>("Actualite Updated successfully",202,responseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actualite with id " + id + " not found"));
        }
    }

    @Override
    public ResponseEntity<?> Delete(Integer id) {
        Optional<Actualite> existingActualite = actualitedao.findById(id);
        if(existingActualite.isPresent()){
            actualitedao.deleteById(id);
            ActualiteReponseDTO responseDTO = modelmapper.map(existingActualite,ActualiteReponseDTO.class);
            return ResponseEntity.ok(new SuccessResponse<>("Actualite Deleted successfully",200,responseDTO));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Actualite with id " + id + " not found"));
        }
    }

    @Override
    public List<ActualiteReponseDTO> findAll() {
        return actualitedao.findAllActualite()
                .stream()
                .map(el->modelmapper.map(el,ActualiteReponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> DeleteAll() {
        List<ActualiteReponseDTO> allActualite = this.findAll();
        if(allActualite!=null){
            for (ActualiteReponseDTO actuali : allActualite){
                actualitedao.deleteById(actuali.getId());
            }
            return ResponseEntity.ok(new SuccessResponse<>("All Actualite are Deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Error....."));
        }
    }
}
