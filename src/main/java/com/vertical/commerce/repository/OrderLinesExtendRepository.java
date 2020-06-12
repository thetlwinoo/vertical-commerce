package com.vertical.commerce.repository;

import com.vertical.commerce.domain.OrderLines;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderLinesExtendRepository extends OrderLinesRepository {
    @Query(value = "SELECT A.* FROM order_lines A INNER JOIN stock_items B ON A.stock_item_id = B.id INNER JOIN products C ON B.product_id = C.id WHERE product_id = :productId", nativeQuery = true)
    List<OrderLines> getReviewsByProduct(@Param("productId") Long productId);
}
