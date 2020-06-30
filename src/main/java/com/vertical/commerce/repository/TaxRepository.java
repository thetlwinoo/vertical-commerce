package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Tax;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tax entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxRepository extends JpaRepository<Tax, Long>, JpaSpecificationExecutor<Tax> {
}
