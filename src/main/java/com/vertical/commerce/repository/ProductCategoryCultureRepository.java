package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductCategoryCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductCategoryCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCategoryCultureRepository extends JpaRepository<ProductCategoryCulture, Long>, JpaSpecificationExecutor<ProductCategoryCulture> {
}
