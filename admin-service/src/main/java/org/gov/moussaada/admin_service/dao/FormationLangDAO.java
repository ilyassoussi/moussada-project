package org.gov.moussaada.admin_service.dao;

import org.gov.moussaada.admin_service.model.FormationLangue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationLangDAO extends JpaRepository<FormationLangue, Integer> {
}
