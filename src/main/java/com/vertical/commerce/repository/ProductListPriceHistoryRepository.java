package com.vertical.commerce.repository;

import com.vertical.commerce.domain.ProductListPriceHistory;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ProductListPriceHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductListPriceHistoryRepository extends JpaRepository<ProductListPriceHistory, Long>, JpaSpecificationExecutor<ProductListPriceHistory> {
}
