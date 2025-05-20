package org.gov.moussaada.paysan_service.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name ="utilisateur-service", fallbackFactory = UtilisateurFeignFallbackFactory.class)
@CircuitBreaker(name = "UtilisateurFeign")

public interface UtilisateurFeign {

    @GetMapping("/utilisateur/getbyid/{id}") // <-- OK
    ResponseEntity<?> getById(@PathVariable("id") int id);

}