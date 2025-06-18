package org.gov.moussaada.paysan_service.feign;

import org.gov.moussaada.paysan_service.dto.UtilisateurReponseDTO;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

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

