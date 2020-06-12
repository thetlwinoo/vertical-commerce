package com.vertical.commerce.web.rest;

import com.vertical.commerce.service.WishlistsExtendService;
import com.vertical.commerce.service.dto.StockItemsDTO;
import com.vertical.commerce.service.dto.WishlistsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

/**
 * WishlistsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class WishlistsExtendResource {

    private final Logger log = LoggerFactory.getLogger(WishlistsExtendResource.class);
    private final WishlistsExtendService wishlistsExtendService;

    public WishlistsExtendResource(WishlistsExtendService wishlistsExtendService) {
        this.wishlistsExtendService = wishlistsExtendService;
    }

    @RequestMapping(value = "/wishlists-extend/add", method = RequestMethod.POST)
    public ResponseEntity addToWishlist(@RequestBody Long id, Principal principal) throws IOException {
        WishlistsDTO wishlists = wishlistsExtendService.addToWishlist(principal, id);
        return new ResponseEntity<WishlistsDTO>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/wishlists-extend/fetch", method = RequestMethod.GET)
    public ResponseEntity fetchWishlist(Principal principal) {
        WishlistsDTO wishlists = wishlistsExtendService.fetchWishlist(principal);
        return new ResponseEntity<WishlistsDTO>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/wishlists-extend/fetch/products", method = RequestMethod.GET)
    public ResponseEntity fetchWishlistProducts(Principal principal) {
        List<StockItemsDTO> stockItemsDTOList = wishlistsExtendService.fetchWishlistStockItems(principal);
        return new ResponseEntity<List<StockItemsDTO>>(stockItemsDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/wishlists-extend/check", method = RequestMethod.GET, params = {"productId"})
    public ResponseEntity getAllOrders(@RequestParam("productId") Long productId, Principal principal) {
        Boolean isInWishlist = wishlistsExtendService.isInWishlist(principal, productId);
        return new ResponseEntity<Boolean>(isInWishlist, HttpStatus.OK);
    }

    @RequestMapping(value = "/wishlists-extend/remove", method = RequestMethod.DELETE, params = "id")
    public ResponseEntity removeFromCart(@RequestParam("id") Long id, Principal principal) {
        WishlistsDTO wishlists = wishlistsExtendService.removeFromWishlist(principal, id);
        return new ResponseEntity<WishlistsDTO>(wishlists, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public ResponseEntity emptyCart(Principal principal) {
        wishlistsExtendService.emptyWishlist(principal);
        return new ResponseEntity(HttpStatus.OK);
    }

}
