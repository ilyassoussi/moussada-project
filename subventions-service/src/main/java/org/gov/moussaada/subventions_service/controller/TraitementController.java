package org.gov.moussaada.subventions_service.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.subventions_service.dto.TraitementSubventionRequest;
import org.gov.moussaada.subventions_service.model.TraitementSubvention;
import org.gov.moussaada.subventions_service.service.TraitementSubventionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/subvention/traitement")
public class TraitementController {

    @Autowired
    private TraitementSubventionService traitementSubventionService;

    @PostMapping("/create")
    ResponseEntity<?> create(@RequestBody TraitementSubventionRequest traitementSubventionRequest){
        return traitementSubventionService.SubventionTraitement(traitementSubventionRequest);
    }

    @PutMapping("/update/{id}")
    ResponseEntity<?> update(@PathVariable int id, @RequestBody TraitementSubventionRequest traitementSubventionRequest){
        return traitementSubventionService.UpdateTraitement(id,traitementSubventionRequest);
    }

    @GetMapping("")
    ResponseEntity<?> getAll(){
        return traitementSubventionService.GetAllTraitement();
    }

    @GetMapping("/info-demande/{id}")
    ResponseEntity<?> getInfoDemande(@PathVariable Long id){
        return traitementSubventionService.GetInfoDemande(id);
    }

    @GetMapping("/getdemande/{id}")
    ResponseEntity<?> getByIdDemande(@PathVariable int id){
        return traitementSubventionService.GetByIdDemande(id);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getById(@PathVariable int id) {
        return traitementSubventionService.GetById(id);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteTraitement(@PathVariable int id){
        return traitementSubventionService.Delete(id);
    }


    @GetMapping("/info-demande")
    ResponseEntity<?> GetInfoDemandeAll(){
        return traitementSubventionService.GetInfoDemandeAll();
    }
}
