package org.gov.moussada.shared_micro.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name ="terrain-service", fallbackFactory = TerrainFeignFallbackFactory.class)
@CircuitBreaker(name = "TerrainFeign")

public interface TerrainFeign {

    @GetMapping("/terrain/response/{id}") // <-- OK
    ResponseEntity<?> getById(@PathVariable int id);;

}