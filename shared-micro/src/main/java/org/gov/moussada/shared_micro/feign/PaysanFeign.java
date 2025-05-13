package org.gov.moussada.shared_micro.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name ="paysan-service", fallbackFactory = PaysanSharedFeignFallbackFactory.class)
@CircuitBreaker(name = "PaysanFeign")

public interface PaysanFeign {

    @GetMapping("/paysan/demande/{id}")
    ResponseEntity<?> getDemande(@PathVariable Long id);
}