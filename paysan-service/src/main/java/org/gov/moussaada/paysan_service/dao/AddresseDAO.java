package org.gov.moussaada.paysan_service.dao;

import org.gov.moussaada.paysan_service.model.Addresse;
import org.gov.moussaada.paysan_service.model.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddresseDAO extends JpaRepository<Addresse,Integer> {

    @Query("FROM Addresse a WHERE a.id_utilisateur = :id")
    Optional<Addresse> findByUser(Long id);

    @Query("FROM Ville v")
    List<Ville> getAllVille();
}
