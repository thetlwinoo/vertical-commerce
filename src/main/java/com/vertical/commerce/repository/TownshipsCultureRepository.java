package com.vertical.commerce.repository;

import com.vertical.commerce.domain.TownshipsCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TownshipsCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TownshipsCultureRepository extends JpaRepository<TownshipsCulture, Long>, JpaSpecificationExecutor<TownshipsCulture> {
}
