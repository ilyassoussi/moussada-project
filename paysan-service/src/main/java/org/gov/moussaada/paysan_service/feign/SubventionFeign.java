package org.gov.moussaada.paysan_service.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="subventions-service", fallbackFactory = SubventionFeignFallbackFactory.class)
@CircuitBreaker(name = "SubventionFeign")

public interface SubventionFeign {

    @GetMapping("/subvention/traitement/{id}") // <-- OK
    ResponseEntity<?> getByIdDemande(@PathVariable int id);

    @GetMapping("/subvention/notexpired") // <-- OK
    ResponseEntity<?> getnotexpired();

}