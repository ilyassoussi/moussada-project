package org.gov.moussaada.admin_service.controller;


import org.gov.moussaada.admin_service.dto.ActualiteReponseDTO;
import org.gov.moussaada.admin_service.dto.ActualiteRequestDTO;
import org.gov.moussaada.admin_service.service.ActualiteService;
import org.gov.moussaada.admin_service.utils.utile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping ("/admin/actualite")
public class ActualiteController {

    private ActualiteService actualiteService;

    public ActualiteController(ActualiteService actualiteService) {
        this.actualiteService = actualiteService;
    }

    @GetMapping("/getall")
    private List<ActualiteReponseDTO> getActualite(@RequestParam(defaultValue = "fr") String lang){
        return actualiteService.findAll(lang);
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getActualiteByLang(@PathVariable int id, @RequestParam(defaultValue = "fr") String lang){
        return actualiteService.getByIdAndLang(id,lang);
    }

    @GetMapping("/withoutLang/{id}")
    private ResponseEntity<?> getActualiteByLang(@PathVariable int id){
        return actualiteService.getById(id);
    }

    @GetMapping("/titre/{titre}")
    private ResponseEntity<?> getActualiteTitre(@PathVariable String titre){
        return actualiteService.findByTitre(titre);
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveActualite(@RequestParam("titreAr") String titreAr,
                                           @RequestParam("titreFr") String titreFr,
                                           @RequestParam("descriptionAr") String descriptionAr,
                                           @RequestParam("descriptionFr") String descriptionFr,
                                           @RequestParam("IsActive") boolean isactive,
                                           @RequestParam(value = "image" , required = false) MultipartFile image

    ) throws IOException {
        String imageFilename = utile.saveImage(image);
        ActualiteRequestDTO actualiteRQ = new ActualiteRequestDTO(imageFilename,titreFr,descriptionFr, titreAr, descriptionAr , utile.CurentDate(),isactive);
        return actualiteService.save(actualiteRQ);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<?> update(@PathVariable int id,
                                     @RequestParam("titreAr") String titreAr,
                                     @RequestParam("titreFr") String titreFr,
                                     @RequestParam("descriptionAr") String descriptionAr,
                                     @RequestParam("descriptionFr") String descriptionFr,
                                     @RequestParam("IsActive") boolean isactive,
                                     @RequestParam(value = "image" , required = false) MultipartFile image) throws IOException {
        String imageFilename = null;
        if(image != null || !image.isEmpty()){
            imageFilename = utile.saveImage(image);
        }
        ActualiteRequestDTO actualiteRQ = new ActualiteRequestDTO(imageFilename,titreFr, descriptionFr, titreAr, descriptionAr , utile.CurentDate(),isactive);
        return actualiteService.update(actualiteRQ,id);
    }

    @DeleteMapping("/delete/{id}")
    private ResponseEntity<?> delete(@PathVariable int id){
        return actualiteService.Delete(id);
    }
    @DeleteMapping("/delete")
    private ResponseEntity<?> delete(){
        return actualiteService.DeleteAll();
    }
}
