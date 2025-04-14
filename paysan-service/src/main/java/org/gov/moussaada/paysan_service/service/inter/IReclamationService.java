package org.gov.moussaada.paysan_service.service.inter;


//import org.gov.moussaada.admin_service.dto.ReclamationTraitement;
import org.gov.moussaada.paysan_service.dto.ReclamationRequestDTO;
import org.gov.moussaada.paysan_service.model.Reclamation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReclamationService {

    ResponseEntity<?> CreateReclamation(ReclamationRequestDTO reclamationRequestDTO);

    ResponseEntity<?> ReclamaTiondejatraite(int id);
    ResponseEntity<?> GetAll();

    ResponseEntity<?> GetReclamationById(int id);

    List<Reclamation> GetEncours();

    Reclamation GetById(int id);

    Reclamation updateReclamation(int id);
}
