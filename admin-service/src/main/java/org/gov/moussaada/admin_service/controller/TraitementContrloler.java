package org.gov.moussaada.admin_service.controller;

import org.gov.moussaada.admin_service.service.KafkaBrokerAdmin;
import org.gov.moussaada.admin_service.dto.TraitementRequestDTO;
import org.gov.moussaada.admin_service.service.TraitementService;
import org.gov.moussaada.shared_lib.DTO.ReclamationTraite;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/reclamation")
public class TraitementContrloler {
    private TraitementService traitementService;

    private KafkaBrokerAdmin kafkaBroker;

    public TraitementContrloler(TraitementService traitementService , KafkaBrokerAdmin kafkaBroker) {
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
    public ReclamationTraite getTraitementByReclmation(@PathVariable int id){
        kafkaBroker.envoyerTraiteReclamtionAuPaysan(traitementService.GetByIdByReclamation(id));
        return traitementService.GetByIdByReclamation(id);
    }

}
