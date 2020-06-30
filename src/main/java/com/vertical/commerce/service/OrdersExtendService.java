package com.vertical.commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.service.dto.OrdersDTO;
import net.minidev.json.parser.ParseException;

import java.security.Principal;

public interface OrdersExtendService {
    Integer getAllOrdersCount(Principal principal);

    OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO) throws JsonProcessingException, ParseException;
}
