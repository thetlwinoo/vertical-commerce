package com.vertical.commerce.repository;

import com.vertical.commerce.domain.TaxClass;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TaxClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxClassRepository extends JpaRepository<TaxClass, Long>, JpaSpecificationExecutor<TaxClass> {
}
