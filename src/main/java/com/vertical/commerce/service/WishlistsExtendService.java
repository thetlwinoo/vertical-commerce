package com.vertical.commerce.service;

import com.vertical.commerce.service.dto.StockItemsDTO;
import com.vertical.commerce.service.dto.WishlistsDTO;

import java.security.Principal;
import java.util.List;

public interface WishlistsExtendService {
    WishlistsDTO addToWishlist(Principal principal, Long id);

    WishlistsDTO fetchWishlist(Principal principal);

    List<StockItemsDTO> fetchWishlistStockItems(Principal principal);

    WishlistsDTO removeFromWishlist(Principal principal, Long id);

    void emptyWishlist(Principal principal);

    Boolean isInWishlist(Principal principal, Long id);
}
