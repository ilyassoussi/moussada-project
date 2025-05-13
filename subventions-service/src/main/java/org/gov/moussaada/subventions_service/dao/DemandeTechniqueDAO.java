package org.gov.moussaada.subventions_service.dao;

import org.gov.moussaada.subventions_service.model.Demande_technique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DemandeTechniqueDAO extends JpaRepository<Demande_technique,Integer> {
    @Query("FROM Demande_technique d WHERE d.isFinished = false")
    List<Demande_technique> NotFinished();
}
