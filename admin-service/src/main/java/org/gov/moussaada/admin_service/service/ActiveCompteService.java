package org.gov.moussaada.admin_service.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.gov.moussaada.admin_service.feign.UtilisateurFeign;
import org.gov.moussaada.admin_service.service.inter.IActiveCompte;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class ActiveCompteService implements IActiveCompte {
    private UtilisateurFeign utilisateurFeign;

    @Override
    public ResponseEntity<?> getAllCompte() {
        return utilisateurFeign.getAll();
    }

    @Override
    public ResponseEntity<?> getCompteNoActive() {
        return  utilisateurFeign.getByInActive();
    }

    @Override
    public ResponseEntity<?> getCompteActive() {
        return utilisateurFeign.getByStatus();
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
            return utilisateurFeign.updateCompteById(id,isactive);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body(e);
        }
    }
}
