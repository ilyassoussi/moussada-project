package org.gov.moussaada.admin_service.dao;

import org.gov.moussaada.admin_service.model.TraitmentReclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TraitementDAO extends JpaRepository<TraitmentReclamation,Integer> {
    @Query("FROM TraitmentReclamation t WHERE t.id_reclamation = :id")
    TraitmentReclamation gettraitementByReclamation(int id);
}
