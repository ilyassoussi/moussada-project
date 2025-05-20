package org.gov.moussaada.admin_service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaysanFeignFallbackFactory implements FallbackFactory<PaysanFeign> {
    @Override
    public PaysanFeign create(Throwable cause) {
        return new PaysanFeign() {

            @Override
            public ResponseEntity<?> getAll() {
                return null;
            }

            @Override
            public ResponseEntity<?> geById(int id) {
                return null;
            }

            @Override
            public ResponseEntity<?> updateReclamationById(int id) {
                return null;
            }
        };
    }
}

