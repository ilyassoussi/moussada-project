package org.gov.moussaada.paysan_service.dao;

import org.gov.moussaada.paysan_service.model.DemandeSubvention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface DemandeSubventionDAO extends JpaRepository<DemandeSubvention,Long> {
    @Query("FROM DemandeSubvention d WHERE d.statusDemande <> 'VALIDEE' AND d.statusDemande <> 'REFUSEE'")
    List<DemandeSubvention> findAllNoTraitment();

    @Query("FROM DemandeSubvention d WHERE d.id_paysan = :idUtilisateur")
    List<DemandeSubvention> findAllIdPaysan(int idUtilisateur);
}
