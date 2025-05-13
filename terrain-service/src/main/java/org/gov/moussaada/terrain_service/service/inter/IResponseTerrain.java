package org.gov.moussaada.terrain_service.service.inter;

import org.gov.moussaada.terrain_service.dto.ResponRequestDTO;
import org.springframework.http.ResponseEntity;

public interface IResponseTerrain {
    ResponseEntity<?> getAll();

    ResponseEntity<?> getById(int id);

    ResponseEntity<?> Create(int id_traitement_subvention, String rapport, String etat, String titre ,String commentaire, String date_de_sortie);

    ResponseEntity<?> Update(int id, String rapport, String etat, String titre ,String commentaire, String date_de_sortie);

    ResponseEntity<?> Delete(int id);

    ResponseEntity<?> UpdateStatus(int id, ResponRequestDTO ResponseRequestDTO);

    ResponseEntity<?> getInfoDemande(Long id);

}
