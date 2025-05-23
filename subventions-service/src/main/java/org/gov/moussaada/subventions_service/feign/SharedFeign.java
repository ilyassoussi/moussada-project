package org.gov.moussaada.subventions_service.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="shared-micro", fallbackFactory = SharedFeignFallbackFactory.class)
@CircuitBreaker(name = "SharedFeign")

public interface SharedFeign {

    @GetMapping("/shared/from-paysan/demande-paysan/{id}")
    ResponseEntity<?> getInfoDemande(@PathVariable Long id);

    @GetMapping("/shared/from-paysan/demande-paysan")
    ResponseEntity<?> getAllDemande();
}