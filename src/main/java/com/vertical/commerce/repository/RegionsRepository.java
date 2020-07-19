package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Regions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Regions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionsRepository extends JpaRepository<Regions, Long>, JpaSpecificationExecutor<Regions> {
}
