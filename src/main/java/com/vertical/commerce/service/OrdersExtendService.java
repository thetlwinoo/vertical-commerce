package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.OrdersDTO;

import java.security.Principal;

public interface OrdersExtendService {
    Integer getAllOrdersCount(Principal principal);

    OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO);
}
