package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductCategoryLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductCategoryLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCategoryLocalizeRepository extends JpaRepository<ProductCategoryLocalize, Long>, JpaSpecificationExecutor<ProductCategoryLocalize> {
}
