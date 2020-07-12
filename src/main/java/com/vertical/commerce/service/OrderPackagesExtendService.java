package com.vertical.commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.service.dto.OrderLinesDTO;
import com.vertical.commerce.service.dto.OrderPackagesDTO;

import java.util.Optional;

public interface OrderPackagesExtendService {
    Optional<OrderPackagesDTO> findOrderPackage(Long id);

    OrderPackagesDTO saveOrderPackage(String payload) throws JsonProcessingException;

    OrderLinesDTO saveOrderLine(OrderLinesDTO orderLinesDTO);

    Optional<OrderLinesDTO> findOrderLine(Long id);

    void updateProductDetailsByOrder(Long orderId) throws JsonProcessingException;

    void orderDetailsBatchUpdate();
}
