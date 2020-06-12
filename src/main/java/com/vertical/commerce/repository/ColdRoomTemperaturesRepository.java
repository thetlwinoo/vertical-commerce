package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ColdRoomTemperatures;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ColdRoomTemperatures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColdRoomTemperaturesRepository extends JpaRepository<ColdRoomTemperatures, Long>, JpaSpecificationExecutor<ColdRoomTemperatures> {
}
