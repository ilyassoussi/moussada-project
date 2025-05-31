package org.gov.moussaada.terrain_service.dao;

import org.gov.moussaada.terrain_service.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponseDAO extends JpaRepository<Response,Integer> {
    @Query("FROM Response r WHERE r.id_traitement_subvention = :idTraitementSubvention")
    Optional<Response> findByIdDemandeSubvention(int idTraitementSubvention);
}
