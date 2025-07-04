package org.gov.moussaada.terrain_service.feign;


import org.gov.moussaada.terrain_service.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(name ="shared-micro", fallbackFactory = SheredFeignFallbackFactory.class, configuration = FeignClientConfig.class)
@CircuitBreaker(name = "SheredFeign")
public interface SheredFeign {

    @GetMapping("/shared/from-subvention/{id}") // <-- OK pour avoir les information d'une demande technique
    ResponseEntity<?> getById(@PathVariable int id);

    @GetMapping("/shared/from-subvention") // pour avoir toutes les demandes texhnoque <-- OK
    ResponseEntity<?> getAll();

    @GetMapping("/shared/from-paysan/demande-paysan/{id}")
    ResponseEntity<?> getDemandeInfo(@PathVariable Long id);

    @GetMapping("/shared/from-subvention/getby-idreponse/{id}") // pour avoir toutes les demandes texhnoque <-- OK
    ResponseEntity<?> getByIdReponse(@PathVariable  int id);
}