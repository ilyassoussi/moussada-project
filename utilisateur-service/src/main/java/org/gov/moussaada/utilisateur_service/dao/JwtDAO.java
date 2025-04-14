package org.gov.moussaada.utilisateur_service.dao;

import jakarta.transaction.Transactional;
import org.gov.moussaada.utilisateur_service.model.Jwt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JwtDAO extends JpaRepository<Jwt,Integer> {
    Jwt findByValue(String token);

    @Query("From Jwt t WHERE t.utilisateur.mail = :mail AND t.isDesactive=:isDesactive AND t.isExpired=:isExpired")
    List<Jwt> findByMailActive(String mail, boolean isExpired, boolean isDesactive);

    @Modifying
    @Transactional
    @Query("DELETE FROM Jwt t WHERE t.isDesactive = :isDesactive AND t.isExpired = :isExpired")
    void deleteAllByExpired(boolean isExpired, boolean isDesactive);

}
