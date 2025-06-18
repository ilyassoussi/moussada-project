package org.gov.moussaada.terrain_service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;

public class SheredFeignFallbackFactory implements FallbackFactory<SheredFeign> {
    @Override
    public SheredFeign create(Throwable cause) {
        return new SheredFeign() {

            @Override
            public ResponseEntity<?> getById(int id) {
                return null;
            }

            @Override
            public ResponseEntity<?> getAll() {
                return null;
            }

            @Override
            public ResponseEntity<?> getDemandeInfo(Long id) {
                return null;
            }

            @Override
            public ResponseEntity<?> getByIdReponse(int id) {
                return null;
            }
        };
    }
}
