package org.gov.moussada.shared_micro.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussada.shared_micro.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="subventions-service", fallbackFactory = SubventionFeignFallbackFactory.class, configuration = FeignClientConfig.class)
@CircuitBreaker(name = "SubventionFeign")

public interface SubventionFeign {

    @GetMapping("/subvention/demande-technique/{id}") // <-- OK
    ResponseEntity<?> getById(@PathVariable int id);

    @GetMapping("/subvention/demande-technique") // <-- OK
    ResponseEntity<?> getAlldm();

    @GetMapping("/subvention/traitement/{id}") // <-- OK
    ResponseEntity<?> getByIdSubventionDemande(@PathVariable int id);

    @GetMapping("/subvention/demande-technique/by-reponse/{id}") // <-- OK
    ResponseEntity<?> getByIdreponse(@PathVariable int id);
}