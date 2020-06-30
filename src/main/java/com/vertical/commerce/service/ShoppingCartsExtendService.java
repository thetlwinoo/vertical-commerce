package com.vertical.commerce.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.service.dto.ShoppingCartsDTO;
import net.minidev.json.parser.ParseException;

import java.security.Principal;
import java.util.List;

public interface ShoppingCartsExtendService {
    ShoppingCartsDTO addToCart(Principal principal, Long id, Integer quantity);

    ShoppingCartsDTO fetchCart(Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException;

    ShoppingCartsDTO removeFromCart(Principal principal, Long id) throws ParseException, NoSuchFieldException, JsonProcessingException;

    ShoppingCartsDTO reduceFromCart(Principal principal, Long id, Integer quantity) throws ParseException, NoSuchFieldException, JsonProcessingException;

    Boolean confirmCart(Principal principal, ShoppingCarts cart);

    ShoppingCartsDTO changedAddToOrder(Principal principal, Long id, Boolean isAddToOrder) throws ParseException, NoSuchFieldException, JsonProcessingException;

    void emptyCart(Principal principal);

    ShoppingCartsDTO checkedAll(Boolean checked,Long packageId, Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException;

    ShoppingCartsDTO removeListFromCart(Principal principal, List<Long> idList) throws ParseException, NoSuchFieldException, JsonProcessingException;

    ShoppingCartsDTO changeDeliveryMethod(Long deliveryMethodId,Long cartid,Long supplierId, Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException;
}
