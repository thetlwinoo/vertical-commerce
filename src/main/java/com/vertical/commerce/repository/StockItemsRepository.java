package com.vertical.commerce.repository;

import com.vertical.commerce.domain.StockItems;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockItems entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockItemsRepository extends JpaRepository<StockItems, Long>, JpaSpecificationExecutor<StockItems> {
}
