package org.gov.moussaada.terrain_service.dao;

import org.gov.moussaada.terrain_service.model.Response;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseDAO extends JpaRepository<Response,Integer> {
}
