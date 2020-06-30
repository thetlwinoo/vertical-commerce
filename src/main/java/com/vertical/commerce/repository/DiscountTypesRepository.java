package com.vertical.commerce.repository;

import com.vertical.commerce.domain.DiscountTypes;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DiscountTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountTypesRepository extends JpaRepository<DiscountTypes, Long>, JpaSpecificationExecutor<DiscountTypes> {
}
