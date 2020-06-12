package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductSet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSetRepository extends JpaRepository<ProductSet, Long>, JpaSpecificationExecutor<ProductSet> {
}
