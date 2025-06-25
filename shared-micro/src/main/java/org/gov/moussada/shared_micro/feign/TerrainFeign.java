package org.gov.moussada.shared_micro.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussada.shared_micro.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="terrain-service", fallbackFactory = TerrainFeignFallbackFactory.class, configuration = FeignClientConfig.class)
@CircuitBreaker(name = "TerrainFeign")

public interface TerrainFeign {

    @GetMapping("/terrain/response/{id}") // <-- OK
    ResponseEntity<?> getById(@PathVariable int id);

    @GetMapping("/terrain/response/get-rapport") // <-- OK
    ResponseEntity<?> getRapport();

}