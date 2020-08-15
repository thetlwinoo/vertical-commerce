package com.vertical.commerce.repository;

import com.vertical.commerce.domain.StateProvincesLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StateProvincesLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateProvincesLocalizeRepository extends JpaRepository<StateProvincesLocalize, Long>, JpaSpecificationExecutor<StateProvincesLocalize> {
}
