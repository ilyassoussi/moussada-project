package org.gov.moussaada.paysan_service.controller;

import org.gov.moussaada.paysan_service.service.DemandeSubentionService;
import org.gov.moussaada.paysan_service.utils.utile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/paysan/demande")
public class DemandeSubventionController {
    @Autowired
    private DemandeSubentionService demandeSubentionService;


    @GetMapping("")
    public ResponseEntity<?> getAll(){
        return demandeSubentionService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getdemandeById(@PathVariable Long id){
        return demandeSubentionService.getdemandeById(id);
    }

    @GetMapping("/traitement/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        return demandeSubentionService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDemande(@RequestParam("id_subvention") Long id_subvention,
                                           @RequestParam("numero_titre") String numero_titre,
                                           @RequestParam("description") String description,
                                           @RequestParam(value = "devis_fournisseur" , required = false) MultipartFile devis_fournisseur){
        String devisFilename = utile.CheckImageAccepded(devis_fournisseur);
        return this.demandeSubentionService.create(id_subvention,numero_titre,description,devisFilename);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDemande(@PathVariable Long id,
                                           @RequestParam(value = "id_subvention" , required = false) Long id_subvention,
                                           @RequestParam(value = "numero_titre" , required = false) String numero_titre,
                                           @RequestParam(value = "description" ,required = false) String description,
                                           @RequestParam(value = "devis_fournisseur" , required = false) MultipartFile devis_fournisseur){
        String devisFilename = utile.CheckImageAccepded(devis_fournisseur);
        return this.demandeSubentionService.update(id,id_subvention,numero_titre,description,devisFilename);
    }

}
