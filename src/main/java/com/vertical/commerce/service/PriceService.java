package com.vertical.commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.domain.ShoppingCarts;
import net.minidev.json.parser.ParseException;

public interface PriceService {
    ShoppingCarts calculatePrice(ShoppingCarts cart) throws ParseException, NoSuchFieldException, JsonProcessingException;
}
