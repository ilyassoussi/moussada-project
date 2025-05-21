package org.gov.moussaada.subventions_service.feign;


import org.gov.moussaada.subventions_service.dto.UtilisateurReponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="utilisateur-service", fallbackFactory = UtilisateurFeignFallbackFactory.class)
@CircuitBreaker(name = "UtilisateurFeign")

public interface UtilisateurFeign {

    @GetMapping("/utilisateur/getbyid/{id}") // <-- OK
    UtilisateurReponseDTO getById(@PathVariable("id") int id);

}