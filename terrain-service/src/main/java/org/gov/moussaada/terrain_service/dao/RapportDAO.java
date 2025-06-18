package org.gov.moussaada.terrain_service.dao;

import org.gov.moussaada.terrain_service.model.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportDAO extends JpaRepository<Rapport,Integer> {
}
