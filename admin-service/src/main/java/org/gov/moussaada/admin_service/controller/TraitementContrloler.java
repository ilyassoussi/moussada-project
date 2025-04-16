package org.gov.moussaada.admin_service.controller;

import org.gov.moussaada.admin_service.service.KafkaAdminService;
import org.gov.moussaada.admin_service.dto.TraitementRequestDTO;
import org.gov.moussaada.admin_service.service.TraitementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/reclamation")
public class TraitementContrloler {
    private TraitementService traitementService;

    private KafkaAdminService kafkaBroker;

    public TraitementContrloler(TraitementService traitementService , KafkaAdminService kafkaBroker) {
        this.traitementService = traitementService;
        this.kafkaBroker = kafkaBroker;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTraitement(){
        return traitementService.GetAll();
    }

    @PostMapping("create/{id}")
    public ResponseEntity<?> TraiteReclamation(@PathVariable("id") int id, @RequestBody TraitementRequestDTO traitementRequestDTO){
        return traitementService.CreateTraitement(id,traitementRequestDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTraitementById(@PathVariable int id){
        return traitementService.GetById(id);
    }

    @GetMapping("response/{id}")
    public ResponseEntity<?> getTraitementByReclmation(@PathVariable int id){
        return traitementService.GetByIdByReclamation(id);
    }

}
