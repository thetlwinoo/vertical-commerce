package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrdersExtendRepository extends OrdersRepository {

    List<Orders> findAllByCustomerIdOrderByLastEditedWhenDesc(Long id);

    Integer countAllByCustomerId(Long id);

    @Query(value = "SELECT * FROM get_order_packages(:orderId)", nativeQuery = true)
    String getOrderPackageDetails(@Param("orderId") Long orderId);
}
