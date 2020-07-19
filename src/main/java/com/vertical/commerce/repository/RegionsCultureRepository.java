package com.vertical.commerce.repository;

import com.vertical.commerce.domain.RegionsCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RegionsCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionsCultureRepository extends JpaRepository<RegionsCulture, Long>, JpaSpecificationExecutor<RegionsCulture> {
}
