package com.vertical.commerce.repository;

import com.vertical.commerce.domain.CountriesCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the CountriesCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CountriesCultureRepository extends JpaRepository<CountriesCulture, Long>, JpaSpecificationExecutor<CountriesCulture> {
}
