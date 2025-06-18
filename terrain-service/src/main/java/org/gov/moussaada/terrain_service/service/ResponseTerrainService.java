package org.gov.moussaada.terrain_service.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gov.moussaada.common_dto.KafkaMoussaadaDTO;
import org.gov.moussaada.terrain_service.dao.RapportDAO;
import org.gov.moussaada.terrain_service.dao.ResponseDAO;
import org.gov.moussaada.terrain_service.dto.KafkaUpdateStatusTerrain;
import org.gov.moussaada.terrain_service.feign.SheredFeign;
import org.gov.moussaada.terrain_service.model.EtatServiceTewrrain;
import org.gov.moussaada.terrain_service.model.Rapport;
import org.gov.moussaada.terrain_service.model.Response;
import org.gov.moussaada.terrain_service.response.ErrorResponse;
import org.gov.moussaada.terrain_service.response.SuccessResponse;
import org.gov.moussaada.terrain_service.service.inter.IResponseTerrain;
import org.gov.moussaada.terrain_service.utils.utile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseTerrainService implements IResponseTerrain {

    @Autowired
    private ResponseDAO responseDAO;

    @Autowired
    private RapportDAO rapportDAO;

    @Autowired
    private KafkaTerrainService kafkaTerrainService;

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
        if(DemandebyId.getStatusCode().is2xxSuccessful()) {
            return DemandebyId;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erreur lors la recuperation de liste des demande non traite"));
        }
    }

    @Override
    public ResponseEntity<?> createOrUpdateResponse(int id_traitement_subvention, String nomTechnicien, String titre , String commentaire, String date_de_sortie) {
        Optional<Response> existeResponse = responseDAO.findByIdDemandeSubvention(id_traitement_subvention);
        try {
            Response response = null;
            if (existeResponse.isPresent()){
                response = existeResponse.get();
                if (date_de_sortie != null ) {
                    response.setDate_de_sortie(utile.ReformulateDate(date_de_sortie));
                }
                if (commentaire != null ) {
                    response.setCommentaire(commentaire);
                }
                if (titre != null ) {
                    response.setTitre(titre);
                }
                response.setDate_update(utile.CurentDate());

            } else {
                response = new Response();
                if (date_de_sortie == null || date_de_sortie.trim().isEmpty()) {
                    return ResponseEntity.badRequest().body("Le champ 'date de sortie' est requis.");
                }
                response.setEtats(EtatServiceTewrrain.SUR_TERRAIN);
                response.setId_traitement_subvention(id_traitement_subvention);
                response.setNomTechnicien(nomTechnicien);
                response.setDate_de_sortie(utile.ReformulateDate(date_de_sortie));
                response.setDate_creation(utile.CurentDate());
                response.setDate_update(utile.CurentDate());
            }
            Response saved = responseDAO.save(response);
            KafkaMoussaadaDTO kafkaMoussaadaDTO = new KafkaMoussaadaDTO("TERRAIN",new KafkaUpdateStatusTerrain(saved.getId_traitement_subvention(),saved.getId_response(), saved.getDate_de_sortie(), String.valueOf(saved.getEtats())));
            kafkaTerrainService.SendIdReponseTraitementDemandeSubvention(kafkaMoussaadaDTO);
            return ResponseEntity.ok().body(new SuccessResponse<>("Success",200,saved));
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

    @Override
    public ResponseEntity<?> getInfoDemande(Long id) {
        ResponseEntity<?> getInfoDemande = sharedFeign.getDemandeInfo(id);
        if(getInfoDemande.getStatusCode().is2xxSuccessful()){
            return getInfoDemande;
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Erreur lors la recuperation de liste des inforamtions des demandes "));
        }
    }

    @Override
    public ResponseEntity<?> getAllReponse() {
        List<Response> allReponse = responseDAO.findAll();
        if(allReponse.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Aucune repopnse existe"));
        }else{
            return ResponseEntity.ok().body(new SuccessResponse<>("Success",200,allReponse));
        }
    }

    @Override
    public ResponseEntity<?> getByIdReponse(int id) {
        Optional<Response> response = responseDAO.findById(id);
        if(response.isPresent()){
            return ResponseEntity.ok().body(new SuccessResponse<>("la reponse existe",200,response.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("la reponse n'existe pas!"));
        }
    }

    @Override
    public ResponseEntity<?> getAllRapport() {
        List<Rapport> AllRapport = rapportDAO.findAll();
        if(AllRapport.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Auccun rapport existe "));
        } else {
            return ResponseEntity.ok().body(new SuccessResponse<>("toutes les rapports",200,AllRapport));
        }
    }

    @Override
    public ResponseEntity<?> getDemandeByIdRespone(int id) {
        ResponseEntity<?> demadne = sharedFeign.getByIdReponse(id);
        if(demadne.getStatusCode().is2xxSuccessful()){
            return demadne;
        } else {
            return demadne;
        }
    }
}
