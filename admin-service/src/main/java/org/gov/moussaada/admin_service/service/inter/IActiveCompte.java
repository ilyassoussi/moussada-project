package org.gov.moussaada.admin_service.service.inter;

import org.springframework.http.ResponseEntity;

public interface IActiveCompte {
    ResponseEntity<?> getAllCompte();
    ResponseEntity<?> getCompteNoActive();
    ResponseEntity<?> getCompteActive();
    ResponseEntity<?> ActiveCompte();

    ResponseEntity<?> confirm(int compte,Boolean isactive);
}
