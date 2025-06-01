package org.gov.moussaada.terrain_service.controller;

import org.gov.moussaada.terrain_service.service.ResponseTerrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @GetMapping("/alldemande")
    public ResponseEntity<?> getAll(){
        return responseTerrainService.getAll();
    }

    @GetMapping("/info-demande/{id}")
    public ResponseEntity<?> getInfoDemande(@PathVariable Long id){
        return responseTerrainService.getInfoDemande(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id){
        return responseTerrainService.getById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestParam(name = "id_traitement_subvention") int  id_traitement_subvention,
            @RequestParam(name = "titre") String titre,
            @RequestParam(name = "rapport") MultipartFile rapport,
            @RequestParam(name = "etat") String etat,
            @RequestParam(name = "commentaire") String commentaire,
            @RequestParam(name = "date_de_sortie") String date_de_sortie

    ) {
        return responseTerrainService.createOrUpdateResponse(id_traitement_subvention,rapport,etat, titre,commentaire,date_de_sortie);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> Delete(@PathVariable int id){
        return responseTerrainService.Delete(id);
    }

}
