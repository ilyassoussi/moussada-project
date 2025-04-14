package org.gov.moussaada.admin_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.gov.moussaada.admin_service.feign.UtilisateurFeign;
import org.gov.moussaada.admin_service.service.inter.IActiveCompte;
import org.gov.moussaada.utilisateur_service.dao.UtilisateurDAO;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.gov.moussaada.utilisateur_service.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ActiveCompteService implements IActiveCompte {
    private UtilisateurFeign utilisateurFeign;

    @Override
    public ResponseEntity<?> getAllCompte() {
        List<Utilisateur> Allcompte = utilisateurFeign.getAll();
        return ResponseEntity.ok().body(new SuccessResponse<>("comptes",200,Allcompte));
    }

    @Override
    public ResponseEntity<?> getCompteNoActive() {
        List<Utilisateur> Allcompte = utilisateurFeign.getByInActive();
        return ResponseEntity.ok().body(new SuccessResponse<>("comptes no active",200,Allcompte));
    }

    @Override
    public ResponseEntity<?> getCompteActive() {
        List<Utilisateur> Allcompte = utilisateurFeign.getByStatus();
        return ResponseEntity.ok().body(new SuccessResponse<>("comptes active",200,Allcompte));
    }

    @Override
    public ResponseEntity<?> ActiveCompte() {
        return null;
    }

    /* @TODO
    *  apres on va ajouter un message qu'il faut le envoyer si lors de desactivation du compte
    * */
    @Override
    public ResponseEntity<?> confirm(int id , Boolean isactive) {
        log.info("utilisateur opeinfein updda , {}",utilisateurFeign.updateCompteById(id,isactive));
        try{
            Utilisateur utilisateur = utilisateurFeign.updateCompteById(id,isactive);
            return ResponseEntity.ok().body(new SuccessResponse<>("compte updated with success",200,utilisateur));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(e);
        }
    }
}
