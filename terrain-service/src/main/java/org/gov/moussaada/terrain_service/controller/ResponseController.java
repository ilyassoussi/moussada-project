package org.gov.moussaada.terrain_service.controller;

import org.gov.moussaada.terrain_service.model.Rapport;
import org.gov.moussaada.terrain_service.service.ResponseTerrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/terrain/response")

public class ResponseController {

    private ResponseTerrainService responseTerrainService;

    public ResponseController(ResponseTerrainService responseTerrainService) {
        this.responseTerrainService = responseTerrainService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllResponse(){
        return responseTerrainService.getAllReponse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTraitementTechnique(@PathVariable int id){
        return responseTerrainService.getByIdReponse(id);
    }


    @GetMapping("/get-rapport")
    public ResponseEntity<?> getAllRapport(){
        return responseTerrainService.getAllRapport();
    }

    @GetMapping("/alldemande")
    public ResponseEntity<?> getAll(){
        return responseTerrainService.getAll();
    }

    @GetMapping("/info-demande/{id}")
    public ResponseEntity<?> getInfoDemande(@PathVariable Long id){
        return responseTerrainService.getInfoDemande(id);
    }

    @GetMapping("/demande-nontraite/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        return responseTerrainService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestParam(name = "id_traitement_subvention") int  id_traitement_subvention,
            @RequestParam(name = "nomTechnicien") String nomTechnicien,
            @RequestParam(name = "titre") String titre,
            @RequestParam(name = "commentaire") String commentaire,
            @RequestParam(name = "date_de_sortie") String date_de_sortie

    ) {
        return responseTerrainService.createOrUpdateResponse(id_traitement_subvention, nomTechnicien ,titre,commentaire,date_de_sortie);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> Delete(@PathVariable int id){
        return responseTerrainService.Delete(id);
    }

    @GetMapping("/demande/by-idreponse/{id}")
    public ResponseEntity<?> getByIdreponse(@PathVariable int id){
        return responseTerrainService.getDemandeByIdRespone(id);
    }

}
