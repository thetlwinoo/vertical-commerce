package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CountriesLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CountriesLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountriesLocalizeRepository extends JpaRepository<CountriesLocalize, Long>, JpaSpecificationExecutor<CountriesLocalize> {
}
