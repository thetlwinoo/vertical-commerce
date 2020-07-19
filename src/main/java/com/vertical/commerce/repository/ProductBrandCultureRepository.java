package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductBrandCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductBrandCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductBrandCultureRepository extends JpaRepository<ProductBrandCulture, Long>, JpaSpecificationExecutor<ProductBrandCulture> {
}
