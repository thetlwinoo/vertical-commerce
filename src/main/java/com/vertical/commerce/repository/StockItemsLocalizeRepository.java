package com.vertical.commerce.repository;

import com.vertical.commerce.domain.StockItemsLocalize;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockItemsLocalize entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockItemsLocalizeRepository extends JpaRepository<StockItemsLocalize, Long>, JpaSpecificationExecutor<StockItemsLocalize> {
}
