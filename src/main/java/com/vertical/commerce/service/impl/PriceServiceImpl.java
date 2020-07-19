package com.vertical.commerce.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertical.commerce.domain.ShippingFeeCalculated;
import com.vertical.commerce.domain.ShoppingCartItems;
import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.repository.ShippingFeeChartExtendRepository;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.PriceService;
import com.vertical.commerce.service.mapper.ShoppingCartsMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PriceServiceImpl implements PriceService {

    private final Logger log = LoggerFactory.getLogger(PriceServiceImpl.class);
    private final ShoppingCartsMapper shoppingCartsMapper;
    private final CommonService commonService;
    private final ShippingFeeChartExtendRepository shippingFeeChartExtendRepository;

    public PriceServiceImpl(ShoppingCartsMapper shoppingCartsMapper, CommonService commonService, ShippingFeeChartExtendRepository shippingFeeChartExtendRepository) {
        this.shoppingCartsMapper = shoppingCartsMapper;
        this.commonService = commonService;
        this.shippingFeeChartExtendRepository = shippingFeeChartExtendRepository;
    }

    @Override
    public ShoppingCarts calculatePrice(ShoppingCarts cart) throws JsonProcessingException {

        BigDecimal totalPrice = BigDecimal.ZERO;
        BigDecimal totalShippingFee = BigDecimal.ZERO;
        String shippingFeeJson = null;

        Optional<List<ShippingFeeCalculated>> result = shippingFeeChartExtendRepository.getCalculateShippingFee(cart.getId());

        if(result.isPresent()){
            List<ShippingFeeCalculated> shippingFeeCalculatedList = result.get();

            for(ShippingFeeCalculated i:shippingFeeCalculatedList){
                totalShippingFee = totalShippingFee.add(i.getShippingFee());
//                totalPrice = totalPrice.add(i.getTotalPrice()).add(i.getShippingFee());
            }

            Gson gson = new Gson();
            shippingFeeJson = gson.toJson(shippingFeeCalculatedList);
        }

        for (ShoppingCartItems i : cart.getCartItemLists()) {
            if(i.isSelectOrder()){
                totalPrice =totalPrice.add(i.getStockItem().getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())));
            }
        }

        //Applying discount percent if exists
        if (cart.getSpecialDeals() != null) {
            if(cart.getSpecialDeals().getDiscountPercentage() != null){
                totalPrice = totalPrice.subtract((totalPrice.multiply(cart.getSpecialDeals().getDiscountPercentage())).divide(BigDecimal.valueOf(100)));
            }else if(cart.getSpecialDeals().getDiscountAmount() != null){
                totalPrice = totalPrice.subtract(cart.getSpecialDeals().getDiscountAmount());
            }

            cart.setDealDetails(getDealString(cart));
        }

        cart.setPackageDetails(shippingFeeJson);
        cart.setTotalPrice(totalPrice.add(totalShippingFee).setScale(2, RoundingMode.CEILING));
        cart.setTotalShippingFee(totalShippingFee.setScale(2,RoundingMode.CEILING));
        return cart;
    }

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
