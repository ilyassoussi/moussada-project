package org.gov.moussaada.admin_service.service.inter;

import org.gov.moussaada.admin_service.dto.TraitementRequestDTO;
import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.springframework.http.ResponseEntity;

public interface ITraitementService {
    ResponseEntity<?> CreateTraitement(int id , TraitementRequestDTO traitementRequestDTO);
    ResponseEntity<?> GetAll();

    ResponseEntity<?> GetById(int id);

    ResponseEntity<?> GetByIdByReclamation(int id);
}
