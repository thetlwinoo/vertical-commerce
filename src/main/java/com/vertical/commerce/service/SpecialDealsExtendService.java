package com.vertical.commerce.service;

import com.vertical.commerce.domain.ShoppingCarts;

import java.security.Principal;

public interface SpecialDealsExtendService {
    ShoppingCarts applyDiscount(Principal principal, String code);
}
