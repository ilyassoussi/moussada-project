//package org.gov.moussaada.paysan_service.feign;
//
//
//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
//import org.gov.moussaada.admin_service.model.TraitmentReclamation;
//import org.gov.moussaada.utilisateur_service.model.Utilisateur;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.util.List;
//
//@FeignClient(name ="admin-service", fallbackFactory = AdminFeignFallbackFactory.class)
//@CircuitBreaker(name = "AdminFeign")
//public interface AdminFeign {
//
//    @GetMapping("/admin/traitrement/response/{id}") // <-- OK
//    TraitmentReclamation getReponse(@PathVariable("id") int id);
//
//}