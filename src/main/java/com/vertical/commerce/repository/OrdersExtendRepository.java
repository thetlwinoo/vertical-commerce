package com.vertical.commerce.repository;

import com.vertical.commerce.domain.Orders;

import java.util.List;

public interface OrdersExtendRepository extends OrdersRepository {

    List<Orders> findAllByCustomerIdOrderByLastEditedWhenDesc(Long id);

    Integer countAllByCustomerId(Long id);
}
