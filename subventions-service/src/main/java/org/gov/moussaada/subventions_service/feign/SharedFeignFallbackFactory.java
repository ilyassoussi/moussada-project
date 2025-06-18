package org.gov.moussaada.subventions_service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;

public class SharedFeignFallbackFactory implements FallbackFactory<SharedFeign> {

    @Override
    public SharedFeign create(Throwable cause) {
        return new SharedFeign() {

            @Override
            public ResponseEntity<?> getInfoDemande(Long id) {
                return null;
            }

            @Override
            public ResponseEntity<?> getAllDemande() {
                return null;
            }

            @Override
            public ResponseEntity<?> getResponseById(int id) {
                return null;
            }

            @Override
            public ResponseEntity<?> getRapport() {
                return null;
            }
        };
    }
}
