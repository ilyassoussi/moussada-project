package org.gov.moussaada.terrain_service.service.inter;

import org.gov.moussaada.terrain_service.dto.ResponRequestDTO;
import org.gov.moussaada.terrain_service.model.Rapport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IResponseTerrain {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(int id);

    ResponseEntity<?> createOrUpdateResponse(int id_traitement_subvention, String nomTechnicien, String titre , String commentaire, String date_de_sortie);

    ResponseEntity<?> Delete(int id);

    ResponseEntity<?> getInfoDemande(Long id);

    ResponseEntity<?> getAllReponse();

    ResponseEntity<?> getByIdReponse(int id);

    ResponseEntity<?> getAllRapport();

}
