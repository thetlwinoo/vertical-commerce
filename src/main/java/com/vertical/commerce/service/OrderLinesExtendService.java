package com.vertical.commerce.service;

import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.service.dto.OrderLinesDTO;

import java.util.List;
import java.util.Map;

public interface OrderLinesExtendService {
    OrderLinesDTO save(OrderLinesDTO orderLinesDTO);

    List<OrderLinesDTO> getOrderLinesByProduct(Long id);

    Map<String, Object> cancelOrderLine(Long id);

    Orders getOrders(Long id);
}
