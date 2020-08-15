package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductsLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductsLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductsLocalizeRepository extends JpaRepository<ProductsLocalize, Long>, JpaSpecificationExecutor<ProductsLocalize> {
}
