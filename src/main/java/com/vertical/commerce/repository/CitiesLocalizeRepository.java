package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CitiesLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CitiesLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitiesLocalizeRepository extends JpaRepository<CitiesLocalize, Long>, JpaSpecificationExecutor<CitiesLocalize> {
}
