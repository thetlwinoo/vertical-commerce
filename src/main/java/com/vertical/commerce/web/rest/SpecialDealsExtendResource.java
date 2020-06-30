package com.vertical.commerce.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.service.SpecialDealsExtendService;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * SpecialDealsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class SpecialDealsExtendResource {

    private final Logger log = LoggerFactory.getLogger(SpecialDealsExtendResource.class);
    private final SpecialDealsExtendService specialDealsExtendService;

    public SpecialDealsExtendResource(SpecialDealsExtendService specialDealsExtendService) {
        this.specialDealsExtendService = specialDealsExtendService;
    }

    @RequestMapping(value = "/special-deals-extend/cart/discount", method = RequestMethod.GET, params = "code")
    public ResponseEntity applyDiscount(@RequestParam("code") String code, Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException {
        ShoppingCarts shoppingCarts = specialDealsExtendService.applyDiscount(principal, code);
        return new ResponseEntity<ShoppingCarts>(shoppingCarts, HttpStatus.OK);
    }

}
