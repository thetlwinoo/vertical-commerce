package com.vertical.commerce.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockItemsExtendRepository extends StockItemsRepository {
    @Query(value = "SELECT sum(1) as allcount,sum(case when  B.active_ind  = true then 1 else 0 end) as active,sum(case when  B.active_ind  = false then 1 else 0 end) as inactive,sum(case when  B.quantity_on_hand  = 0 then 1 else 0 end) as onhand FROM products A INNER JOIN stock_items B ON A.id = B.product_id WHERE A.supplier_id = :supplierId", nativeQuery = true)
    List<Object[]> getStatistics(@Param("supplierId") Long supplierId);
}
