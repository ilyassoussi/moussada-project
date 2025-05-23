package org.gov.moussaada.subventions_service.service.inter;

import org.gov.moussaada.subventions_service.dto.TraitementSubventionRequest;
import org.springframework.http.ResponseEntity;

public interface ITraitementSubvention {
    ResponseEntity<?> CreateTraitement(TraitementSubventionRequest traitementSubventionRequest);

    ResponseEntity<?> UpdateTraitement(int id , TraitementSubventionRequest traitementSubventionRequest);

    ResponseEntity<?> GetAllTraitement();

    ResponseEntity<?> GetByIdDemande(int id);

    ResponseEntity<?> GetById(int id);

    ResponseEntity<?> Delete(int id);

    ResponseEntity<?> GetInfoDemande(Long id);

    ResponseEntity<?> GetInfoDemandeAll();

}
