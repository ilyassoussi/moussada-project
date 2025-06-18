package org.gov.moussada.shared_micro.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;

public class SubventionFeignFallbackFactory implements FallbackFactory<SubventionFeign> {
    @Override
    public SubventionFeign create(Throwable cause) {
        return new SubventionFeign() {

            @Override
            public ResponseEntity<?> getById(int id) {
                return null;
            }

            @Override
            public ResponseEntity<?> getAlldm() {
                return null;
            }

            @Override
            public ResponseEntity<?> getByIdSubventionDemande(int id) {
                return null;
            }

            @Override
            public ResponseEntity<?> getByIdreponse(int id) {
                return null;
            }


        };
    }
}

