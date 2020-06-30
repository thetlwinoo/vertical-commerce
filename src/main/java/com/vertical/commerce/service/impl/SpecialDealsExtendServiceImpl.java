package com.vertical.commerce.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.domain.SpecialDeals;
import com.vertical.commerce.repository.ShoppingCartsRepository;
import com.vertical.commerce.repository.SpecialDealsExtendRepository;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.PriceService;
import com.vertical.commerce.service.SpecialDealsExtendService;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Service
@Transactional
public class SpecialDealsExtendServiceImpl implements SpecialDealsExtendService {

    private final Logger log = LoggerFactory.getLogger(SpecialDealsExtendServiceImpl.class);
    private final SpecialDealsExtendRepository specialDealsExtendRepository;
    private final ShoppingCartsRepository shoppingCartsRepository;
    private final CommonService commonService;
    private final PriceService priceService;

    public SpecialDealsExtendServiceImpl(SpecialDealsExtendRepository specialDealsExtendRepository, ShoppingCartsRepository shoppingCartsRepository, CommonService commonService, PriceService priceService) {
        this.specialDealsExtendRepository = specialDealsExtendRepository;
        this.shoppingCartsRepository = shoppingCartsRepository;
        this.commonService = commonService;
        this.priceService = priceService;
    }

    @Override
    public ShoppingCarts applyDiscount(Principal principal, String code) throws ParseException, NoSuchFieldException, JsonProcessingException {
        SpecialDeals specialDeals   = specialDealsExtendRepository.findSpecialDealsByDiscountCode(code);
        if (specialDeals == null) {
            throw new IllegalStateException("Discount code not found");
        }

        People people = commonService.getPeopleByPrincipal(principal);
        if (people.getCart() == null) {
            throw new IllegalArgumentException("Cart not found");
        }

        ShoppingCarts shoppingCarts = people.getCart();
        shoppingCarts.setSpecialDeals(specialDeals);
        shoppingCarts = priceService.calculatePrice(shoppingCarts);
//        shoppingCarts.setCartString(commonService.getCartString(shoppingCarts));
        shoppingCartsRepository.save(shoppingCarts);
        return shoppingCarts;
    }
}
