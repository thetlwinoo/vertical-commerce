package com.vertical.commerce.repository;

import com.vertical.commerce.domain.StockItemHoldings;

public interface StockItemHoldingsExtendRepository extends StockItemHoldingsRepository {
    StockItemHoldings findStockItemHoldingsByStockItemId(Long id);
}
