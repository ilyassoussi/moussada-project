package org.gov.moussaada.terrain_service.feign;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.gov.moussaada.admin_service.feign.PaysanFeignFallbackFactory;
import org.gov.moussaada.paysan_service.model.Reclamation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@FeignClient(name ="paysan-service", fallbackFactory = PaysanFeignFallbackFactory.class)
@CircuitBreaker(name = "PaysanFeign")
public interface PaysanFeign {

    @GetMapping("/paysan/reclamation/encours") // <-- OK
    List<Reclamation> getAll();

    @GetMapping("/paysan/reclamation/{id}") // <-- OK
    Reclamation geById(@PathVariable("id") int id);

    @PutMapping("/paysan/reclamation/update/{id}")
    Reclamation updateReclamationById(@PathVariable("id") int id);
}