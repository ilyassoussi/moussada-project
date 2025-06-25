package org.gov.moussaada.admin_service.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussaada.admin_service.config.FeignClientConfig;
import org.gov.moussaada.admin_service.dto.UtilisateurReponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name ="utilisateur-service", fallbackFactory = UtilisateurFeignFallbackFactory.class, configuration = FeignClientConfig.class)
@CircuitBreaker(name = "UtilisateurFeign")
public interface UtilisateurFeign {

    @GetMapping("/utilisateur/compte") // <-- OK
    ResponseEntity<?> getAll();

    @GetMapping("/utilisateur/getbyid/{id}") // <-- OK
    UtilisateurReponseDTO getById(@PathVariable("id") int id);

    @GetMapping("/utilisateur/compte/active") // <-- OK
    ResponseEntity<?> getByStatus();

    @GetMapping("/utilisateur/compte/inactive") // <-- OK
    ResponseEntity<?> getByInActive();

    @PutMapping("/utilisateur/compte/{id}") // <-- OK
    ResponseEntity<?> updateCompteById(@PathVariable("id") int id, @RequestBody Boolean isactive);
}