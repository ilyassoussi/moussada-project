package org.gov.moussaada.subventions_service.dao;

import org.gov.moussaada.subventions_service.model.TraitementSubvention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValidateSubventionDAO extends JpaRepository<TraitementSubvention,Integer> {
    @Query("FROM TraitementSubvention t WHERE t.id_demande = :id")
    Optional<TraitementSubvention> findByIDemanade(int id);
}
