package org.gov.moussaada.admin_service.feign;

import org.gov.moussaada.admin_service.dto.UtilisateurReponseDTO;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
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
            public ResponseEntity<?> getAll() {
                return null;
            }

            @Override
            public UtilisateurReponseDTO getById(int id) {
                return null;
            }

            @Override
            public ResponseEntity<?> getByStatus() {
                return null;
            }

            @Override
            public ResponseEntity<?> getByInActive() {
                return null;
            }

            @Override
            public ResponseEntity<?> updateCompteById(int id, Boolean isactive) {
                return null;
            }
        };
    }
}

