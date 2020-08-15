package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductBrandLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductBrandLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductBrandLocalizeRepository extends JpaRepository<ProductBrandLocalize, Long>, JpaSpecificationExecutor<ProductBrandLocalize> {
}
