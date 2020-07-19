package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CitiesCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CitiesCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitiesCultureRepository extends JpaRepository<CitiesCulture, Long>, JpaSpecificationExecutor<CitiesCulture> {
}
