package org.gov.moussaada.subventions_service.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.subventions_service.dao.SubventionDAO;
import org.gov.moussaada.subventions_service.dto.SubventionReponse;
import org.gov.moussaada.subventions_service.dto.SubventionRequest;
import org.gov.moussaada.subventions_service.model.Subvention;
import org.gov.moussaada.subventions_service.model.Type_subv;
import org.gov.moussaada.subventions_service.service.inter.ISubvention;
import org.gov.moussaada.subventions_service.utils.utile;
import org.gov.moussaada.utilisateur_service.response.ErrorResponse;
import org.gov.moussaada.utilisateur_service.response.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor

public class SubventionService implements ISubvention {

    @Autowired
    private SubventionDAO subventionDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> Create(SubventionRequest subventionRequest) {

        if (subventionRequest.getCategorie() == null || subventionRequest.getCategorie().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'categorie' est requis.");
        }
        if (subventionRequest.getDescription() == null || subventionRequest.getDescription().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'description' est requis.");
        }
        if (subventionRequest.getMontantMaximum() == 0) {
            return ResponseEntity.badRequest().body("Le champ 'montantMaximum' est requis.");
        }
        if (subventionRequest.getPourcentageSubvention() == 0) {
            return ResponseEntity.badRequest().body("Le champ 'pourcentageSubvention' est requis.");
        }
        if (subventionRequest.getDateDebut() == null || subventionRequest.getDateDebut().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'dateDebut' est requis.");
        }
        if (subventionRequest.getDateFin() == null || subventionRequest.getDateFin().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'dateFin' est requis.");
        }
        if (subventionRequest.getConditionsEligibilite() == null || subventionRequest.getConditionsEligibilite().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'conditionsEligibilite' est requis.");
        }
        if (subventionRequest.getId_region() == null || subventionRequest.getId_region().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'id_region' est requis.");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebut;
        Date dateFin;

        try {
            dateDebut = formatter.parse(subventionRequest.getDateDebut());
            dateFin = formatter.parse(subventionRequest.getDateFin());
        } catch (ParseException e) {
            return ResponseEntity.badRequest().body("Format de date invalide. Utilisez yyyy-MM-dd.");
        }

        Subvention subvention = Subvention.builder()
                .dateCreation(utile.CurentDate())
                .categorie(subventionRequest.getCategorie())
                .dateFin(dateFin)
                .dateDebut(dateDebut)
                .pourcentageSubvention(subventionRequest.getPourcentageSubvention())
                .description(subventionRequest.getDescription())
                .id_region(subventionRequest.getId_region())
                .conditionsEligibilite(subventionRequest.getConditionsEligibilite())
                .montantMaximum(subventionRequest.getMontantMaximum())
                .piecesRequises(subventionRequest.getPiecesRequises())
                .build();

        subventionDAO.save(subvention);
        SubventionReponse subventionReponse = modelMapper.map(subvention, SubventionReponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("created with success",201,subventionReponse));
    }

    @Override
    public ResponseEntity<?> Update(Long id  , SubventionRequest subventionRequest) {
        Optional<Subvention> subventionOld = subventionDAO.findById(id);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date dateDebut;
        Date dateFin;

        if (!subventionRequest.getCategorie().trim().isEmpty()) {
            subventionOld.get().setCategorie(subventionRequest.getCategorie());
        }
        if (!subventionRequest.getDescription().trim().isEmpty()) {
            subventionOld.get().setDescription(subventionRequest.getDescription());
        }
        if (subventionRequest.getMontantMaximum() != 0) {
            subventionOld.get().setMontantMaximum(subventionRequest.getMontantMaximum());
        }
        if (subventionRequest.getPourcentageSubvention() != 0) {
            subventionOld.get().setPourcentageSubvention(subventionRequest.getPourcentageSubvention());
        }
        if (!subventionRequest.getDateDebut().trim().isEmpty()) {
            try {
                dateDebut = formatter.parse(subventionRequest.getDateDebut());
            } catch (ParseException e) {
                return ResponseEntity.badRequest().body("Format de date invalide. Utilisez yyyy-MM-dd.");
            }
            subventionOld.get().setDateDebut(dateDebut);
        }
        if (!subventionRequest.getDateFin().trim().isEmpty()) {
            try {
                dateFin = formatter.parse(subventionRequest.getDateFin());
            } catch (ParseException e) {
                return ResponseEntity.badRequest().body("Format de date invalide. Utilisez yyyy-MM-dd.");
            }
            subventionOld.get().setDateFin(dateFin);
        }
        if (!subventionRequest.getConditionsEligibilite().trim().isEmpty()) {
            subventionOld.get().setConditionsEligibilite(subventionRequest.getConditionsEligibilite());
        }
        if (!subventionRequest.getId_region().isEmpty()) {
            subventionOld.get().setId_region(subventionRequest.getId_region());
        }


        subventionDAO.save(subventionOld.get());
        SubventionReponse subventionReponse = modelMapper.map(subventionOld.get(), SubventionReponse.class);
        return ResponseEntity.ok().body(new SuccessResponse<>("Updated with success",201,subventionReponse));
    }

    @Override
    public ResponseEntity<?> GetAll() {
        try {
            List<Subvention> subvention = subventionDAO.findAll();
            return ResponseEntity.ok().body(new SuccessResponse<>("all subvenmtion",200,subvention));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse("erreur d'exception : "+ e));
        }
    }

    @Override
    public ResponseEntity<?> GetById(Long id) {
        Optional<Subvention> subvention = subventionDAO.findById(id);
        if(subvention.isPresent()){
            return ResponseEntity.ok().body(new SuccessResponse<>("subvenmtion de id = "+id ,200,subvention.get()));
        }else {
            return ResponseEntity.badRequest().body(new ErrorResponse("subvention de id = "+id+" n'existe pas"));
        }
    }

    @Override
    public ResponseEntity<?> RemoveSubvention(Long id) {
        try {
            subventionDAO.deleteById(id);
            return ResponseEntity.ok().body("deleted with success");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse("erreur d'exception : "+ e));
        }
    }
}
