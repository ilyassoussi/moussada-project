package org.gov.moussaada.admin_service.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussaada.admin_service.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@FeignClient(name ="paysan-service", fallbackFactory = PaysanFeignFallbackFactory.class, configuration = FeignClientConfig.class)
@CircuitBreaker(name = "PaysanFeign")
public interface PaysanFeign {

    @GetMapping("/paysan/reclamation/encours") // <-- OK
    ResponseEntity<?> getAll();

    @GetMapping("/paysan/reclamation/{id}") // <-- OK
    ResponseEntity<?> geById(@PathVariable("id") int id);

    @PutMapping("/paysan/reclamation/update/{id}")
    ResponseEntity<?> updateReclamationById(@PathVariable("id") int id);
}