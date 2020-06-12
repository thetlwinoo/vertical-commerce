package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductSetDetailPrice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductSetDetailPrice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductSetDetailPriceRepository extends JpaRepository<ProductSetDetailPrice, Long>, JpaSpecificationExecutor<ProductSetDetailPrice> {
}
