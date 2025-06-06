package org.gov.moussaada.subventions_service.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.gov.moussaada.subventions_service.dto.DemandeTechniqueRequestDTO;
import org.gov.moussaada.subventions_service.service.DemandeTechniqueService;
import org.gov.moussaada.subventions_service.service.inter.IDemandeTechnique;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subvention/demande-technique")


public class DemandeTechniqueController {

    private DemandeTechniqueService demandeTechniqueService;

    public DemandeTechniqueController(DemandeTechniqueService demandeTechniqueService) {
        this.demandeTechniqueService = demandeTechniqueService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DemandeTechniqueRequestDTO demandeTechniqueRequestDTO) {
        return demandeTechniqueService.create(demandeTechniqueRequestDTO);
    }

    @PostMapping("/vaildate-rapport/{id}")
    public void ValidateRapport(@PathVariable int id_rapport) {
         demandeTechniqueService.validateRapport(id_rapport);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody DemandeTechniqueRequestDTO demandeTechniqueRequestDTO) {
        return demandeTechniqueService.update(id , demandeTechniqueRequestDTO);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return demandeTechniqueService.getAll();
    }

    @GetMapping("/reponse/get-rapport")
    public ResponseEntity<?> getAllRapport() {
        return demandeTechniqueService.getAllRapport();
    }

    @GetMapping("/en-cours")
    public ResponseEntity<?> getAllNotFinished() {
        return demandeTechniqueService.getAllNotFinished();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return demandeTechniqueService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> Delete(@PathVariable int id) {
        return demandeTechniqueService.Delete(id);
    }

    @GetMapping("/reponse/{id}")
    public ResponseEntity<?> getResponseById(@PathVariable int id){
        return demandeTechniqueService.getResponseById(id);
    }
}
