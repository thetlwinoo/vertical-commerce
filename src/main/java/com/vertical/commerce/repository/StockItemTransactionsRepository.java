package com.vertical.commerce.repository;

import com.vertical.commerce.domain.StockItemTransactions;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the StockItemTransactions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StockItemTransactionsRepository extends JpaRepository<StockItemTransactions, Long>, JpaSpecificationExecutor<StockItemTransactions> {
}
