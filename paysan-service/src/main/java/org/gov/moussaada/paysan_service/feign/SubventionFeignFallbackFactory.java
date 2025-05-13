package org.gov.moussaada.paysan_service.feign;

import org.gov.moussaada.subventions_service.model.TraitementSubvention;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;

public class SubventionFeignFallbackFactory implements FallbackFactory<SubventionFeign> {
    @Override
    public SubventionFeign create(Throwable cause) {
        return new SubventionFeign() {

            @Override
            public ResponseEntity<?> getByIdDemande(int id) {
                return null;
            }
        };
    }
}

