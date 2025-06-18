package org.gov.moussaada.admin_service.service.inter;

import org.gov.moussaada.admin_service.dto.ActualiteReponseDTO;
import org.gov.moussaada.admin_service.dto.ActualiteRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IActualiteService {
    ResponseEntity<?> save(ActualiteRequestDTO actualiteRQ);
    ResponseEntity<?> findByTitre(String titre);
    ResponseEntity<?> update(ActualiteRequestDTO actualiteRQ , Integer id);
    ResponseEntity<?> Delete(Integer id);
    List<ActualiteReponseDTO> findAll(String lang);
    ResponseEntity<?> DeleteAll();
    ResponseEntity<?> getByIdAndLang(int id, String lang);

    ResponseEntity<?> getById(int id);
}
