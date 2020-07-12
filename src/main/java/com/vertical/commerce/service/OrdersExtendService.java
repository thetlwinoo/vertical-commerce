package com.vertical.commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.service.dto.OrdersCriteria;
import com.vertical.commerce.service.dto.OrdersDTO;
import net.minidev.json.parser.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.Map;

public interface OrdersExtendService {
    Integer getAllOrdersCount(Principal principal);

    OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO) throws JsonProcessingException, ParseException;

    Page<OrdersDTO> getCustomerOrdersReviews(Boolean completedReview, OrdersCriteria criteria, Pageable pageable, Principal principal);

    Page<OrdersDTO> getAllOrders(OrdersCriteria criteria, Pageable pageable, Principal principal);
}
