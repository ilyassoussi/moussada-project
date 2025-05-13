package org.gov.moussaada.terrain_service.controller;

import org.gov.moussaada.terrain_service.dto.ResponRequestDTO;
import org.gov.moussaada.terrain_service.service.ResponseTerrainService;
import org.gov.moussaada.terrain_service.utils.utile;
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
        String rapportName = utile.CheckPdfAccepded(rapport);
        return responseTerrainService.Create(id_traitement_subvention,titre,rapportName,commentaire,etat,date_de_sortie);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id,
                                    @RequestParam(name = "titre") String titre,
                                    @RequestParam(name = "rapport") MultipartFile rapport,
                                    @RequestParam(name = "etat") String etat,
                                    @RequestParam(name = "commentaire") String commentaire,
                                    @RequestParam(name = "date_de_sortie") String date_de_sortie) {

        String rapportName = utile.CheckPdfAccepded(rapport);
        return responseTerrainService.Update(id,titre,rapportName,commentaire,etat,date_de_sortie);
    }

    @PutMapping("/status/update/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable int id, @RequestBody ResponRequestDTO ResponseRequestDTO) {
        return responseTerrainService.UpdateStatus(id,ResponseRequestDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> Delete(@PathVariable int id){
        return responseTerrainService.Delete(id);
    }

}
