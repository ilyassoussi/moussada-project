package org.gov.moussaada.paysan_service.service.inter;

import org.gov.moussaada.paysan_service.dto.AddresseRequestDTO;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface IDemandeSubventionService{

    ResponseEntity<?> create(Long idSubvention, String numeroTitre, String description, String pdfFilename);

    ResponseEntity<?> getAll();

    ResponseEntity<?> update(Long idSubvention, String numeroTitre, String description, String pdfFilename);
}
