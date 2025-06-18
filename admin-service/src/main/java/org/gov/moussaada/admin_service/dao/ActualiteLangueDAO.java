package org.gov.moussaada.admin_service.dao;

import org.gov.moussaada.admin_service.model.ActualiteLangue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActualiteLangueDAO extends JpaRepository<ActualiteLangue,Integer> {

}
