package org.gov.moussada.shared_micro.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussaada.subventions_service.model.Demande_technique;
import org.gov.moussaada.subventions_service.model.TraitementSubvention;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="subventions-service", fallbackFactory = SubventionFeignFallbackFactory.class)
@CircuitBreaker(name = "SubventionFeign")

public interface SubventionFeign {

    @GetMapping("/subvention/demande-technique/{id}") // <-- OK
    ResponseEntity<?> getById(@PathVariable int id);

    @GetMapping("/subvention/demande-technique/en-cours") // <-- OK
    ResponseEntity<?> getAlldm();

    @GetMapping("/subvention/traitement/{id}") // <-- OK
    ResponseEntity<?> getByIdSubventionDemande(@PathVariable int id);
}