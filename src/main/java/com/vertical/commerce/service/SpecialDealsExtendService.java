package com.vertical.commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.domain.ShoppingCarts;
import net.minidev.json.parser.ParseException;

import java.security.Principal;

public interface SpecialDealsExtendService {
    ShoppingCarts applyDiscount(Principal principal, String code) throws ParseException, NoSuchFieldException, JsonProcessingException;
}
