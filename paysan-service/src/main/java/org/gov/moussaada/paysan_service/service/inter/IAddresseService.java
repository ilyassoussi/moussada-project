package org.gov.moussaada.paysan_service.service.inter;

import org.gov.moussaada.paysan_service.dto.AddresseRequestDTO;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

public interface IAddresseService {
    ResponseEntity<?> ValideAddresse(AddresseRequestDTO adreesRq) throws URISyntaxException;

    ResponseEntity<?> getAdress();
}
