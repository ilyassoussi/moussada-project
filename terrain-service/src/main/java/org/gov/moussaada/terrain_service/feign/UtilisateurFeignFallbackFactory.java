package org.gov.moussaada.terrain_service.feign;

import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UtilisateurFeignFallbackFactory implements FallbackFactory<UtilisateurFeign> {
    @Override
    public UtilisateurFeign create(Throwable cause) {
        return new UtilisateurFeign() {
            @Override
            public List<Utilisateur> getAll() {
                System.out.println("Fallback getAll: " + cause.getMessage());
                return List.of(); // retour par défaut
            }

            @Override
            public Utilisateur getById(int id) {
                return null;
            }

            @Override
            public List<Utilisateur> getByStatus() {
                System.out.println("Fallback getByStatus: " + cause.getMessage());
                return List.of();
            }

            @Override
            public List<Utilisateur> getByInActive() {
                System.out.println("Fallback getByInActive: " + cause.getMessage());
                return List.of();
            }

            @Override
            public Utilisateur updateCompteById(int id, Boolean isactive) {
                return null;
            }
        };
    }
}

