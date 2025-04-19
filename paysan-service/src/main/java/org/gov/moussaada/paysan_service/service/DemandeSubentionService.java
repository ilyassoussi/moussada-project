package org.gov.moussaada.paysan_service.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gov.moussaada.paysan_service.dao.DemandeSubventionDAO;
import org.gov.moussaada.paysan_service.model.DemandeSubvention;
import org.gov.moussaada.paysan_service.model.Status_demande;
import org.gov.moussaada.paysan_service.service.inter.IDemandeSubventionService;
import org.gov.moussaada.utilisateur_service.response.SuccessResponse;
import org.gov.moussaada.utilisateur_service.utils.utile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data

public class DemandeSubentionService implements IDemandeSubventionService {

    @Autowired
    private DemandeSubventionDAO demandeSubventionDAO;

    @Override
    public ResponseEntity<?> create(Long idSubvention, String numeroTitre, String description, String devisFilename) {
        DemandeSubvention demandeSubvention = DemandeSubvention.builder()
                .id_subvention(idSubvention)
                .numero_titre(numeroTitre)
                .description(description)
                .devis_fournisseur(devisFilename)
                .statusDemande(Status_demande.EN_ATTENTE)
                .dateDepot(utile.CurentDate())
                .build();

        DemandeSubvention saveDemande =  demandeSubventionDAO.save(demandeSubvention);

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("created with success",201,saveDemande));
    }

    @Override
    public ResponseEntity<?> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<?> update(Long idSubvention, String numeroTitre, String description, String pdfFilename) {
        return null;
    }
}
