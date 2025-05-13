package org.gov.moussaada.admin_service.controller;


import org.gov.moussaada.admin_service.dto.ActualiteReponseDTO;
import org.gov.moussaada.admin_service.dto.ActualiteRequestDTO;
import org.gov.moussaada.admin_service.service.ActualiteService;
import org.gov.moussaada.admin_service.utils.utile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping ("/admin/actualite")
public class ActualiteController {

    private ActualiteService actualiteService;

    public ActualiteController(ActualiteService actualiteService) {
        this.actualiteService = actualiteService;
    }

    @GetMapping("/getall")
    private List<ActualiteReponseDTO> getActualite(){
        System.out.println("test");
        return actualiteService.findAll();
    }

    @GetMapping("/{id}")
    private ResponseEntity<?> getActualite(@PathVariable int id){
        return actualiteService.findById(id);
    }

    @GetMapping("/titre/{titre}")
    private ResponseEntity<?> getActualiteTitre(@PathVariable String titre){
        return actualiteService.findByTitre(titre);
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveActualite(@RequestParam("titre") String titre,
                                           @RequestParam("description") String description,
                                           @RequestParam(value = "pdf" , required = false) MultipartFile pdf) {
        String pdfFilename = utile.CheckPdfAccepded(pdf);
        ActualiteRequestDTO actualiteRQ = new ActualiteRequestDTO(titre, description, pdfFilename, utile.CurentDate());
        return actualiteService.save(actualiteRQ);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<?> update(@PathVariable int id,
                                     @RequestParam("titre") String titre,
                                     @RequestParam("description") String description,
                                     @RequestParam(value = "pdf" , required = false) MultipartFile pdf){
        String pdfFilename = utile.CheckPdfAccepded(pdf);
        ActualiteRequestDTO actualiteRQ = new ActualiteRequestDTO(titre, description, pdfFilename, utile.CurentDate());
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
