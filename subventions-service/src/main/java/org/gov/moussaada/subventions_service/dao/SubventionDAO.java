package org.gov.moussaada.subventions_service.dao;

import org.gov.moussaada.subventions_service.model.Subvention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SubventionDAO extends JpaRepository<Subvention,Long> {
    @Query("SELECT s FROM Subvention s WHERE s.dateFin >= :date")
    List<Subvention> getNotExpired(@Param("date") Date date);

}
