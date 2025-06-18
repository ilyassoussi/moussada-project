package org.gov.moussaada.paysan_service.dao;


import org.gov.moussaada.paysan_service.model.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReclamationDAO extends JpaRepository<Reclamation,Integer> {
    @Query("FROM Reclamation r WHERE r.inTreatment = false ")
    List<Reclamation> findAllByInTreatment();

    @Query("FROM Reclamation r WHERE r.id_reclamation =:id ")
    Reclamation findByIdUnique(int id);

    @Query("SELECT r FROM Reclamation r WHERE r.id_paysan = :utilisateur")
    List<Reclamation> findByUser(int utilisateur);

}
