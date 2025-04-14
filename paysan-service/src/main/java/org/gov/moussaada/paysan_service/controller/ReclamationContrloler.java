package org.gov.moussaada.paysan_service.controller;

import org.gov.moussaada.paysan_service.dto.ReclamationRequestDTO;
import org.gov.moussaada.paysan_service.model.Reclamation;
import org.gov.moussaada.paysan_service.service.ReclamationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("paysan/reclamation")
public class ReclamationContrloler {

    @Autowired
    private ReclamationService reclamationService;

    public ReclamationContrloler(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllReclamation(){
        return reclamationService.GetAll();
    }

    @PostMapping("create")
    public ResponseEntity<?> CreateReclamation(@RequestBody ReclamationRequestDTO reclamationRequestDTO){
        return reclamationService.CreateReclamation(reclamationRequestDTO);
    }
    @GetMapping("reponse/{id}")
    public ResponseEntity<?> ResponseReclamation(@PathVariable int id ){
        return reclamationService.ReclamaTiondejatraite(id);
    }

    @GetMapping("/encours")
    public List<Reclamation> getReclamation(){
        return reclamationService.GetEncours();
    }

    @GetMapping("/{id}")
    public Reclamation getReclamationById(@PathVariable("id") int id){
        return reclamationService.GetById(id);
    }

    @PutMapping("/update/{id}")
    public Reclamation updateReclamationById(@PathVariable("id") int id){
        return reclamationService.updateReclamation(id);
    }

}
