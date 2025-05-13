package org.gov.moussaada.terrain_service.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gov.moussaada.terrain_service.dao.ResponseDAO;
import org.gov.moussaada.terrain_service.dto.ResponRequestDTO;
import org.gov.moussaada.terrain_service.feign.SheredFeign;
import org.gov.moussaada.terrain_service.model.Response;
import org.gov.moussaada.terrain_service.service.inter.IResponseTerrain;
import org.gov.moussaada.terrain_service.utils.utile;
import org.gov.moussaada.utilisateur_service.response.ErrorResponse;
import org.gov.moussaada.utilisateur_service.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseTerrainService implements IResponseTerrain {

    @Autowired
    private ResponseDAO responseDAO;

    @Autowired
    private SheredFeign sharedFeign;

    @Override
    public ResponseEntity<?> getAll() {
        ResponseEntity<?> AllDemande = sharedFeign.getAll();
        if(AllDemande.getStatusCode().is2xxSuccessful()){
            return AllDemande;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erreur lors la recuperation de liste des demande non traite"));
        }
    }

    @Override
    public ResponseEntity<?> getById(int id) {
        ResponseEntity<?> DemandebyId = sharedFeign.getById(id);
        if(DemandebyId.getStatusCode().is2xxSuccessful()){
            return DemandebyId;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erreur lors la recuperation de liste des demande non traite"));
        }
    }

    @Override
    public ResponseEntity<?> Create(int id_traitement_subvention, String rapport, String etat, String titre ,String commentaire, String date_de_sortie) {

        if (date_de_sortie == null || date_de_sortie.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'date' est requis.");
        }
        if (commentaire == null || commentaire.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le champ 'commentaire' est requis.");
        }

        Response response = Response.builder()
                .id_traitement_subvention(id_traitement_subvention)
                .titre(titre)
                .commentaire(commentaire)
                .etats(etat)
                .rapport(rapport)
                .date_de_sortie(utile.ReformulateDate(date_de_sortie))
                .date_update(utile.CurentDate())
                .date_creation(utile.CurentDate())
                .build();
        try{
            Response saved = responseDAO.save(response);
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>("created with success",201,saved));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> Update(int id, String rapport, String etat, String titre ,String commentaire, String date_de_sortie) {
        Optional<Response> response = responseDAO.findById(id);
        if(!response.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("la reponse n'existe pas"));
        }
        if (date_de_sortie != null ) {
            response.get().setDate_de_sortie(utile.ReformulateDate(date_de_sortie));
        }
        if (commentaire != null ) {
            response.get().setCommentaire(commentaire);
        }
        if (titre != null ) {
            response.get().setTitre(titre);
        }
        if(rapport != null){
            response.get().setRapport(rapport);
        }
        if(etat != null){
            response.get().setEtats(etat);
        }
        response.get().setDate_update(utile.CurentDate());
        try{
            Response updated = responseDAO.save(response.get());
            return ResponseEntity.ok().body(new SuccessResponse<>("Updated with success",200 ,updated));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> Delete(int id) {
        try{
            responseDAO.deleteById(id);
            return  ResponseEntity.ok().body(new SuccessResponse<>("deleted with success",200,true));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /*
     * @TODO il faut utilise kafka pour envoyer la mise a jours de status de demande et aussi la date de sortie
     */
    @Override
    public ResponseEntity<?> UpdateStatus(int id, ResponRequestDTO ResponseRequestDTO) {

        return null;
    }

    @Override
    public ResponseEntity<?> getInfoDemande(Long id) {
        ResponseEntity<?> getInfoDemande = sharedFeign.getDemandeInfo(id);
        if(getInfoDemande.getStatusCode().is2xxSuccessful()){
            return getInfoDemande;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erreur lors la recuperation de liste des inforamtions des demandes "));
        }
    }
}
