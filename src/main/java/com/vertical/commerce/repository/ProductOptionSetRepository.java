package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductOptionSet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductOptionSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductOptionSetRepository extends JpaRepository<ProductOptionSet, Long>, JpaSpecificationExecutor<ProductOptionSet> {
}
