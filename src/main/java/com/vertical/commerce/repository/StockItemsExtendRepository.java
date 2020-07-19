package com.vertical.commerce.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StockItemsExtendRepository extends StockItemsRepository {
    @Query(value = "SELECT COALESCE(sum(1),0) as allcount,COALESCE(sum(case when  B.active_flag  = true then 1 else 0 end),0) as active,COALESCE(sum(case when  B.active_flag  = false then 1 else 0 end),0) as inactive,COALESCE(sum(case when  B.quantity_on_hand  = 0 then 1 else 0 end),0) as onhand FROM products A INNER JOIN stock_items B ON A.id = B.product_id WHERE A.supplier_id = :supplierId", nativeQuery = true)
    List<Object[]> getStatistics(@Param("supplierId") Long supplierId);
}
