package org.gov.moussaada.utilisateur_service.dao;

import org.gov.moussaada.utilisateur_service.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface UtilisateurDAO extends JpaRepository<Utilisateur,Integer> {
    @Query(value = "SELECT * FROM utilisateur WHERE mail=?" , nativeQuery = true)
    Optional<Utilisateur> findByEmail(String email);

    @Query(value = "SELECT * FROM utilisateur WHERE identite=?" , nativeQuery = true)
    Optional<Utilisateur> findByIdentite(String identite);

    @Query("SELECT u FROM Jwt j INNER JOIN j.utilisateur u WHERE j.value = :token")
    Utilisateur findUserByToken(String token);

    @Query("SELECT u FROM Utilisateur u WHERE u.is_active = :status")
    List<Utilisateur> findByStatus(Boolean status);
}
