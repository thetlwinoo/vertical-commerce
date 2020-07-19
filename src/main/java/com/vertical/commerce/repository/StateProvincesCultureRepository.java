package com.vertical.commerce.repository;

import com.vertical.commerce.domain.StateProvincesCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StateProvincesCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateProvincesCultureRepository extends JpaRepository<StateProvincesCulture, Long>, JpaSpecificationExecutor<StateProvincesCulture> {
}
