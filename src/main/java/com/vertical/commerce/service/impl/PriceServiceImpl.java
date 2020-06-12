package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.ShoppingCartItems;
import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.PriceService;
import com.vertical.commerce.service.mapper.ShoppingCartsMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);
    private final ShoppingCartsMapper shoppingCartsMapper;
    private final CommonService commonService;

    public PriceServiceImpl(ShoppingCartsMapper shoppingCartsMapper, CommonService commonService) {
        this.shoppingCartsMapper = shoppingCartsMapper;
        this.commonService = commonService;
    }

    @Override
    public ShoppingCarts calculatePrice(ShoppingCarts cart) {

        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalCargoPrice = BigDecimal.ZERO;

        for (ShoppingCartItems i : cart.getCartItemLists()) {
            System.out.println("amount " + i.getQuantity());
            totalPrice = ((i.getStockItem().getUnitPrice().add(i.getStockItem().getRecommendedRetailPrice())).multiply(BigDecimal.valueOf(i.getQuantity()))).add(totalCargoPrice).add(totalPrice);
            totalCargoPrice = (i.getStockItem().getRecommendedRetailPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
        }

        //Applying discount percent if exists
        if (cart.getSpecialDeals() != null) {
            if(cart.getSpecialDeals().getDiscountPercentage() != null){
                totalPrice = totalPrice.subtract((totalPrice.multiply(cart.getSpecialDeals().getDiscountPercentage())).divide(BigDecimal.valueOf(100)));
            }else if(cart.getSpecialDeals().getDiscountAmount() != null){
                totalPrice = totalPrice.subtract(cart.getSpecialDeals().getDiscountAmount());
            }

            cart.setDealString(getDealString(cart));
        }

        cart.setTotalPrice(totalPrice.setScale(2, RoundingMode.CEILING));
        cart.setTotalCargoPrice(totalCargoPrice.setScale(2,RoundingMode.CEILING));
//        cart.setCartString(getCartString(cart));
        return cart;
    }

//    private String getCartString(ShoppingCarts cart){
//        List<String> cartItemList= new ArrayList<>();
//        for (ShoppingCartItems i : cart.getCartItemLists()) {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("cartItemId",i.getId());
//            jsonObject.put("stockItemId",i.getStockItem().getId());
//            jsonObject.put("stockItemName",i.getStockItem().getName());
//            jsonObject.put("productId",i.getStockItem().getProduct().getId());
//            jsonObject.put("productName",i.getStockItem().getProduct().getName());
//            jsonObject.put("quantity",i.getQuantity());
//            jsonObject.put("unitPrice",i.getStockItem().getUnitPrice());
//            jsonObject.put("photo",i.getStockItem().getThumbnailUrl());
//            String jsonString = jsonObject.toJSONString();
//            cartItemList.add(jsonString);
//        }
//
//        return String.join(";",cartItemList);
//    }

    private String getDealString(ShoppingCarts cart){
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code",cart.getSpecialDeals().getDiscountCode());
        jsonObject.put("amount",cart.getSpecialDeals().getDiscountAmount());
        jsonObject.put("percentage",cart.getSpecialDeals().getDiscountPercentage());
        jsonObject.put("id",cart.getSpecialDeals().getId());
        jsonObject.put("description",cart.getSpecialDeals().getDealDescription());

        return jsonObject.toJSONString();
    }
}
