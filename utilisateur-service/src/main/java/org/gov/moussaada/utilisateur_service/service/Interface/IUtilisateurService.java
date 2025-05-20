package org.gov.moussaada.utilisateur_service.service.Interface;


import org.gov.moussaada.utilisateur_service.dto.AuthentifDTO;
import org.gov.moussaada.utilisateur_service.dto.UpdatePasswordRequestDTO;
import org.gov.moussaada.utilisateur_service.dto.UtilisateurRequestDTO;
import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUtilisateurService {
    ResponseEntity<?> logout();

    ResponseEntity<?> update(UtilisateurRequestDTO adminRQ);

    ResponseEntity<?> loginUtilisateur(AuthentifDTO RQ);

    ResponseEntity<?> createAffilie(UtilisateurRequestDTO utilisateurRequestDTO);

    ResponseEntity<?> getUserByToken();

    ResponseEntity<?> updatePassword(UpdatePasswordRequestDTO updatePasswordRequestDTO);

    ResponseEntity<?> getCompte();

    ResponseEntity<?> getByStatus(Boolean status);

    ResponseEntity<?> updateCompteById(int id , Boolean isactive);

    ResponseEntity<?> ValidateAccount(int id , int numeroValidation);

    ResponseEntity<?> getById(int id);
}
