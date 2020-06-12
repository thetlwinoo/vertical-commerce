package com.vertical.commerce.service;

import com.vertical.commerce.domain.ShoppingCarts;

public interface PriceService {
    ShoppingCarts calculatePrice(ShoppingCarts cart);
}
