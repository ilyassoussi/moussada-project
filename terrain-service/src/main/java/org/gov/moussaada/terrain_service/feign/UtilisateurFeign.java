package org.gov.moussaada.terrain_service.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussaada.admin_service.feign.UtilisateurFeignFallbackFactory;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name ="utilisateur-service", fallbackFactory = UtilisateurFeignFallbackFactory.class)
@CircuitBreaker(name = "UtilisateurFeign")
public interface UtilisateurFeign {

    @GetMapping("/utilisateur/compte") // <-- OK
    List<Utilisateur> getAll();

    @GetMapping("/utilisateur/compte/active") // <-- OK
    List<Utilisateur> getByStatus();

    @GetMapping("/utilisateur/compte/inactive") // <-- OK
    List<Utilisateur> getByInActive();

    @PutMapping("/utilisateur/compte/{id}") // <-- OK
    Utilisateur updateCompteById(@PathVariable("id") int id, @RequestBody Boolean isactive);
}