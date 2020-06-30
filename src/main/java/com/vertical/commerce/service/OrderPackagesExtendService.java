package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.OrderLinesDTO;
import com.vertical.commerce.service.dto.OrderPackagesDTO;

import java.util.Optional;

public interface OrderPackagesExtendService {
    Optional<OrderPackagesDTO> findOrderPackage(Long id);

    OrderPackagesDTO saveOrderPackage(OrderPackagesDTO orderPackagesDTO);

    OrderLinesDTO saveOrderLine(OrderLinesDTO orderLinesDTO);

    Optional<OrderLinesDTO> findOrderLine(Long id);
}
