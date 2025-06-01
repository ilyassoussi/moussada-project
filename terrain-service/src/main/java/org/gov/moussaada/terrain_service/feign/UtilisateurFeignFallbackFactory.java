package org.gov.moussaada.terrain_service.feign;

import org.gov.moussaada.terrain_service.dto.UtilisateurReponseDTO;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UtilisateurFeignFallbackFactory implements FallbackFactory<UtilisateurFeign> {
    @Override
    public UtilisateurFeign create(Throwable cause) {
        return new UtilisateurFeign() {

            @Override
            public UtilisateurReponseDTO getById(int id) {
                return null;
            }
        };
    }
}

