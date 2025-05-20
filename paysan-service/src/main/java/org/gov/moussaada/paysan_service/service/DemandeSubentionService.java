package org.gov.moussaada.paysan_service.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.paysan_service.dao.DemandeSubventionDAO;
import org.gov.moussaada.paysan_service.feign.SubventionFeign;
import org.gov.moussaada.paysan_service.model.DemandeSubvention;
import org.gov.moussaada.paysan_service.model.Status_demande;
import org.gov.moussaada.paysan_service.response.ErrorResponse;
import org.gov.moussaada.paysan_service.response.SuccessResponse;
import org.gov.moussaada.paysan_service.service.inter.IDemandeSubventionService;
import org.gov.moussaada.paysan_service.utils.utile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class DemandeSubentionService implements IDemandeSubventionService {

    @Autowired
    private DemandeSubventionDAO demandeSubventionDAO;

    @Autowired
    private SubventionFeign subventionFeign;

    @Override
    public ResponseEntity<?> create(Long idSubvention, String numeroTitre, String description, String devisFilename) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Map<String, Object> userDetails = (Map<String, Object>) principal;
        Long idUtilisateur = Long.valueOf(userDetails.get("id_utilisateur").toString());

        if (idSubvention == null) {
            return ResponseEntity.badRequest().body("Le champ 'id subvention' est requis.");
        }
        if (numeroTitre == null || numeroTitre.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'numero de titre' est requis.");
        }
        if (description == null || description.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'description' est requis.");
        }

        DemandeSubvention demandeSubvention = DemandeSubvention.builder()
                .id_subvention(idSubvention)
                .numero_titre(numeroTitre)
                .description(description)
                .devis_fournisseur(devisFilename)
                .statusDemande(Status_demande.EN_ATTENTE)
                .dateDepot(utile.CurentDate())
                .id_paysan(Math.toIntExact(idUtilisateur))
                .build();

        DemandeSubvention saveDemande =  demandeSubventionDAO.save(demandeSubvention);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("created with success",201,saveDemande));
    }

    @Override
    public ResponseEntity<?> getAll() {
        List<DemandeSubvention> demandeSubvention = demandeSubventionDAO.findAll();
        if(demandeSubvention.isEmpty()){
            return ResponseEntity.ok().body(new SuccessResponse<>("no demande existe ",200,demandeSubvention));
        }else{
            return ResponseEntity.ok().body(new SuccessResponse<>("all demande ",200,demandeSubvention));
        }
    }

    @Override
    public ResponseEntity<?> update(Long id, Long idSubvention, String numeroTitre, String description, String devisFilename) {
        Optional<DemandeSubvention> demandeSubvention = demandeSubventionDAO.findById(id);
        if(idSubvention != null) {
            demandeSubvention.get().setId_subvention(idSubvention);
        }
        if (!numeroTitre.trim().isEmpty()) {
            demandeSubvention.get().setNumero_titre(numeroTitre);
        }
        if (!description.trim().isEmpty()) {
            demandeSubvention.get().setDescription(description);
        }
        if (devisFilename != null) {
            demandeSubvention.get().setDevis_fournisseur(devisFilename);
        }
        try{
            DemandeSubvention saved = demandeSubventionDAO.save(demandeSubvention.get());
            return ResponseEntity.ok().body(new SuccessResponse<>("updated",200,saved));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse("Error lors de mise a jours "+ e));
        }
    }

    @Override
    public ResponseEntity<?> getById(int id) {
//        Optional<DemandeSubvention> demandeSubvention = demandeSubventionDAO.findById(id);
        ResponseEntity<?> traitementSubvention = subventionFeign.getByIdDemande(id);
        if(traitementSubvention == null) {
            return ResponseEntity.ok().body(new SuccessResponse<>("no message existe ",200,null));
        } else {
            return traitementSubvention;
        }
    }

    @Override
    public ResponseEntity<?> getdemandeById(Long id) {
        Optional<DemandeSubvention> demandeSubvention = demandeSubventionDAO.findById(id);
        if(demandeSubvention.isPresent()){
            return ResponseEntity.ok().body(new SuccessResponse<>("la demande " , 200 , demandeSubvention));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("aucune demande existe"));
        }
    }
}
