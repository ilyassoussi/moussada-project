package org.gov.moussaada.subventions_service.dao;

import org.gov.moussaada.subventions_service.model.Subvention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubventionDAO extends JpaRepository<Subvention,Integer> {
}
