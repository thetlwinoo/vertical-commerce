package com.vertical.commerce.service;

import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.service.dto.ShoppingCartsDTO;

import java.security.Principal;

public interface ShoppingCartsExtendService {
    ShoppingCartsDTO addToCart(Principal principal, Long id, Integer quantity);

    ShoppingCartsDTO fetchCart(Principal principal);

    ShoppingCartsDTO removeFromCart(Principal principal, Long id);

    ShoppingCartsDTO reduceFromCart(Principal principal, Long id, Integer quantity);

    Boolean confirmCart(Principal principal, ShoppingCarts cart);

    void emptyCart(Principal principal);
}
