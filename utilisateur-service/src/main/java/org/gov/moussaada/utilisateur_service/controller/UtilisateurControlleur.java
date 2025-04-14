package org.gov.moussaada.utilisateur_service.controller;


import lombok.extern.slf4j.Slf4j;
import org.gov.moussaada.utilisateur_service.dao.JwtDAO;
import org.gov.moussaada.utilisateur_service.dto.AuthentifDTO;
import org.gov.moussaada.utilisateur_service.dto.UpdatePasswordRequestDTO;
import org.gov.moussaada.utilisateur_service.dto.UtilisateurRequestDTO;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.gov.moussaada.utilisateur_service.service.UtilisateurSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/utilisateur")
public class UtilisateurControlleur {

    @Autowired
    private UtilisateurSevice utilisateurservice;

    @Autowired
    private JwtDAO jwtDAO;

    @PostMapping("/auth/create")
    private ResponseEntity<?> CreateAffilie(@RequestBody UtilisateurRequestDTO requestbody){
        return this.utilisateurservice.createAffilie(requestbody);
    }

    @PostMapping("/auth/login")
    private ResponseEntity<?> login(@RequestBody AuthentifDTO requestbody){
        return this.utilisateurservice.loginUtilisateur(requestbody);
    }

    @PostMapping("/logout")
    private ResponseEntity<?> logout(){
        return this.utilisateurservice.logout();
    }

    @PutMapping("/update")
    private ResponseEntity<?> update(@RequestParam UtilisateurRequestDTO requestbody){

        return null;
    }

    @GetMapping("/getInfoUser/{token}")
    private ResponseEntity<?> getInfoUser(@PathVariable String token){
        return this.utilisateurservice.getUserByToken(token);
    }

    @PutMapping("/update/password")
    private ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDTO updatePasswordRequestDTO){
        return utilisateurservice.updatePassword(updatePasswordRequestDTO);
    }

    @GetMapping("/auth/verifyToken")
    public ResponseEntity<?> verifyToken(@RequestParam String token) {
        return utilisateurservice.isValide(token);
    }

    @GetMapping("/compte")
    public List<Utilisateur> compte(){
        log.info("ici le probleme");
        return utilisateurservice.getCompte();
    }

    @GetMapping("/compte/active")
    public List<Utilisateur> compteActive(){
        return utilisateurservice.getByStatus(true);
    }

    @GetMapping("/compte/inactive")
    public List<Utilisateur> compteInActive(){
        return utilisateurservice.getByStatus(false);
    }

    @PutMapping("/compte/{id}")
    public Utilisateur compteById(@PathVariable int id,@RequestBody Boolean isactive){
        return utilisateurservice.updateCompteById(id,isactive);
    }
}
