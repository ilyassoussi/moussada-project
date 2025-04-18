package org.gov.moussaada.subventions_service.service.inter;

import org.gov.moussaada.subventions_service.dto.SubventionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

public interface ISubvention {
    ResponseEntity<?> Create(SubventionRequest subventionRequest);
    ResponseEntity<?> Update(SubventionRequest subventionRequest);
    ResponseEntity<?> GetAll();
    ResponseEntity<?> GetById(int id);
    ResponseEntity<?> RemoveSubvention(int id);
}
