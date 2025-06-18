package org.gov.moussaada.paysan_service.service.inter;


import org.gov.moussaada.paysan_service.dto.ReclamationRequestDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReclamationService {

    ResponseEntity<?> CreateReclamation(ReclamationRequestDTO reclamationRequestDTO);

    ResponseEntity<?> ReclamaTiondejatraite(int id);
    ResponseEntity<?> GetAll();

    ResponseEntity<?> GetReclamationById(int id);

    ResponseEntity<?> GetEncours();

    ResponseEntity<?> GetById(int id);

    ResponseEntity<?> updateReclamation(int id);
}
