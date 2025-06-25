package org.gov.moussaada.terrain_service.feign;

import org.gov.moussaada.terrain_service.config.FeignClientConfig;
import org.gov.moussaada.terrain_service.dto.UtilisateurReponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@FeignClient(name ="utilisateur-service", fallbackFactory = UtilisateurFeignFallbackFactory.class, configuration = FeignClientConfig.class)
@CircuitBreaker(name = "UtilisateurFeign")

public interface UtilisateurFeign {

    @GetMapping("/utilisateur/getbyid/{id}") // <-- OK
    UtilisateurReponseDTO getById(@PathVariable("id") int id);

}