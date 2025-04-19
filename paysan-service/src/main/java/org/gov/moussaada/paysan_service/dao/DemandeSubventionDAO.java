package org.gov.moussaada.paysan_service.dao;

import org.gov.moussaada.paysan_service.model.DemandeSubvention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DemandeSubventionDAO extends JpaRepository<DemandeSubvention,Long> {
}
