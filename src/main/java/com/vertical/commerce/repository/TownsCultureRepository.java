package com.vertical.commerce.repository;

import com.vertical.commerce.domain.TownsCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TownsCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TownsCultureRepository extends JpaRepository<TownsCulture, Long>, JpaSpecificationExecutor<TownsCulture> {
}
