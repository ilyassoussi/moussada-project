package org.gov.moussada.shared_micro.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussada.shared_micro.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="paysan-service", fallbackFactory = PaysanSharedFeignFallbackFactory.class, configuration = FeignClientConfig.class)
@CircuitBreaker(name = "PaysanFeign")

public interface PaysanFeign {

    @GetMapping("/paysan/demande/{id}")
    ResponseEntity<?> getDemande(@PathVariable Long id);

    @GetMapping("/paysan/demande")
    ResponseEntity<?> getDemandeAll();
}