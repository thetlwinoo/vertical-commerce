package com.vertical.commerce.repository;

import com.vertical.commerce.domain.DistrictsCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DistrictsCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistrictsCultureRepository extends JpaRepository<DistrictsCulture, Long>, JpaSpecificationExecutor<DistrictsCulture> {
}
