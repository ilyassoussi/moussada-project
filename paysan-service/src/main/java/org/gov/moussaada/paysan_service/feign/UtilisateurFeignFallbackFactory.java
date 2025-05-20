package org.gov.moussaada.paysan_service.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UtilisateurFeignFallbackFactory implements FallbackFactory<UtilisateurFeign> {
    @Override
    public UtilisateurFeign create(Throwable cause) {
        return new UtilisateurFeign() {

            @Override
            public ResponseEntity<?> getById(int id) {
                return null;
            }

        };
    }
}

