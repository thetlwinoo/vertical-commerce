package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductsCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductsCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductsCultureRepository extends JpaRepository<ProductsCulture, Long>, JpaSpecificationExecutor<ProductsCulture> {
}
