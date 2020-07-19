package com.vertical.commerce.repository;

import com.vertical.commerce.domain.MaterialsCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MaterialsCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialsCultureRepository extends JpaRepository<MaterialsCulture, Long>, JpaSpecificationExecutor<MaterialsCulture> {
}
