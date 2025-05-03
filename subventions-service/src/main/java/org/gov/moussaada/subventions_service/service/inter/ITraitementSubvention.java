package org.gov.moussaada.subventions_service.service.inter;

import org.gov.moussaada.subventions_service.dto.TraitementSubventionRequest;
import org.gov.moussaada.subventions_service.model.TraitementSubvention;
import org.springframework.http.ResponseEntity;

public interface ITraitementSubvention {
    ResponseEntity<?> CreateTraitement(TraitementSubventionRequest traitementSubventionRequest);
    ResponseEntity<?> UpdateTraitement(int id , TraitementSubventionRequest traitementSubventionRequest);
    ResponseEntity<?> GetAllTraitement();
    TraitementSubvention GetByIdDemande(int id);

    ResponseEntity<?> Delete(int id);
}
