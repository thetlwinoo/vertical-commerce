package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.OrderLinesDTO;

import java.util.List;

public interface OrderLinesExtendService {
    OrderLinesDTO save(OrderLinesDTO orderLinesDTO);

    List<OrderLinesDTO> getOrderLinesByProduct(Long id);
}
