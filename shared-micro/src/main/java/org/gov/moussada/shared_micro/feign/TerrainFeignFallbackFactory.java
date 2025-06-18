package org.gov.moussada.shared_micro.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;

public class TerrainFeignFallbackFactory implements FallbackFactory<TerrainFeign> {
    @Override
    public TerrainFeign create(Throwable cause) {
        return new TerrainFeign(){

            @Override
            public ResponseEntity<?> getById(int id) {
                return null;
            }

            @Override
            public ResponseEntity<?> getRapport() {
                return null;
            }
        };
    }
}
