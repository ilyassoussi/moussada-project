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
    public ResponseEntity<?> getAddressById(){
        return demandeSubentionService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createDemande(@RequestParam("id_subvention") Long id_subvention,
                                           @RequestParam("numero_titre") String numero_titre,
                                           @RequestParam("description") String description,
                                           @RequestParam(value = "devis_fournisseur" , required = false) MultipartFile devis_fournisseur){
        String devisFilename = utile.CheckImageAccepded(devis_fournisseur);
        return this.demandeSubentionService.create(id_subvention,numero_titre,description,devisFilename);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateDemande(@PathVariable Long id,
                                           @RequestParam("id_subvention") Long id_subvention,
                                           @RequestParam("numero_titre") String numero_titre,
                                           @RequestParam("description") String description,
                                           @RequestParam(value = "devis_fournisseur" , required = false) MultipartFile devis_fournisseur){
        String pdfFilename = utile.CheckImageAccepded(devis_fournisseur);
        return this.demandeSubentionService.update(id_subvention,numero_titre,description,pdfFilename);
    }

}
