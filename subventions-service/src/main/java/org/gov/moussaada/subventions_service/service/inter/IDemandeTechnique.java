package org.gov.moussaada.subventions_service.service.inter;

import org.gov.moussaada.subventions_service.dto.DemandeTechniqueRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IDemandeTechnique {
    ResponseEntity<?> create(DemandeTechniqueRequestDTO demandeTechniqueRequestDTO);
    ResponseEntity<?> update(int id, @RequestBody DemandeTechniqueRequestDTO demandeTechniqueRequestDTO);
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(int id);
    ResponseEntity<?> Delete(int id);
    ResponseEntity<?> getAllNotFinished();

}
