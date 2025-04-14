package org.gov.moussaada.admin_service.dao;

import org.gov.moussaada.admin_service.model.Actualite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ActualiteDAO extends JpaRepository<Actualite,Integer> {
    @Query(value = "select id_actualite , title , description , pdf , date_creation from actualite where title = ?1"
            , nativeQuery = true)
    Actualite findByTitre(String titre);

    @Query("FROM Actualite a order by a.date_creation desc")
    Collection<Object> findAllActualite();
}
