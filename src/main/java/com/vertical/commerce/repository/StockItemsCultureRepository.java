package com.vertical.commerce.repository;

import com.vertical.commerce.domain.StockItemsCulture;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockItemsCulture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockItemsCultureRepository extends JpaRepository<StockItemsCulture, Long>, JpaSpecificationExecutor<StockItemsCulture> {
}
