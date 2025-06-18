package org.gov.moussaada.admin_service.service.inter;

import org.gov.moussaada.admin_service.dto.FormationReponseDTO;
import org.gov.moussaada.admin_service.dto.FormationRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IFormation {
    ResponseEntity<?> save(FormationRequestDTO dto);
    List<FormationReponseDTO> findAll(String lang);
    ResponseEntity<?> deleteId(int id);
}
