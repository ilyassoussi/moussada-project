package org.gov.moussaada.subventions_service.service.inter;

import org.gov.moussaada.subventions_service.dto.SubventionRequest;
import org.springframework.http.ResponseEntity;

public interface ISubvention {
    ResponseEntity<?> Create(SubventionRequest subventionRequest);
    ResponseEntity<?> Update(Long id , SubventionRequest subventionRequest);
    ResponseEntity<?> GetAll();
    ResponseEntity<?> GetById(Long id);
    ResponseEntity<?> RemoveSubvention(Long id);
}
