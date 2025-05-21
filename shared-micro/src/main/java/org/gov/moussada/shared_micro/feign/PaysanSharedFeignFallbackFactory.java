package org.gov.moussada.shared_micro.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PaysanSharedFeignFallbackFactory implements FallbackFactory<PaysanFeign> {
    @Override
    public PaysanFeign create(Throwable cause) {
        return new PaysanFeign() {


            @Override
            public ResponseEntity<?> getDemande(Long id) {
                return null;
            }
        };
    }
}

