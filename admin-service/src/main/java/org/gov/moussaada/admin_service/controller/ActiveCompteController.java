package org.gov.moussaada.admin_service.controller;

import org.gov.moussaada.admin_service.service.ActiveCompteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/compte")
public class ActiveCompteController {

    private ActiveCompteService activeCompteService;

    public ActiveCompteController(ActiveCompteService activeCompteService) {
        this.activeCompteService = activeCompteService;
    }

    @GetMapping("")
    public ResponseEntity<?> compte(){
        return activeCompteService.getAllCompte();
    }

    @GetMapping("/active")
    public ResponseEntity<?> compteActive(){
        return activeCompteService.getCompteActive();
    }

    @GetMapping("/inactive")
    public ResponseEntity<?> compteInActive(){
        return activeCompteService.getCompteNoActive();
    }

    @PutMapping("/confimed/{id}")
    public ResponseEntity<?> confimAccount(@PathVariable(name = "id") int id, @RequestBody Boolean isactive) {
        return activeCompteService.confirm(id,isactive);
    }
}
