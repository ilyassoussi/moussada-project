package org.gov.moussaada.paysan_service.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussaada.paysan_service.dto.UtilisateurReponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="utilisateur-service", fallbackFactory = UtilisateurFeignFallbackFactory.class)
@CircuitBreaker(name = "UtilisateurFeign")

public interface UtilisateurFeign {

    @GetMapping("/utilisateur/getbyid/{id}") // <-- OK
    UtilisateurReponseDTO getById(@PathVariable("id") int id);

}