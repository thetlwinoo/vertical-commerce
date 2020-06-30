package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Logistics;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Logistics entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LogisticsRepository extends JpaRepository<Logistics, Long>, JpaSpecificationExecutor<Logistics> {
}
