package com.vertical.commerce.repository;

import com.vertical.commerce.domain.MaterialsLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MaterialsLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialsLocalizeRepository extends JpaRepository<MaterialsLocalize, Long>, JpaSpecificationExecutor<MaterialsLocalize> {
}
