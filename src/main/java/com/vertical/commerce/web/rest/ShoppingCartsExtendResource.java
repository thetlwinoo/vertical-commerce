package com.vertical.commerce.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vertical.commerce.domain.ShoppingCarts;
import com.vertical.commerce.service.ShoppingCartsExtendService;
import com.vertical.commerce.service.dto.ShoppingCartsDTO;
import com.vertical.commerce.service.util.CommonUtil;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ShoppingCartsExtendResource controller
 */
@RestController
@RequestMapping("/api")
public class ShoppingCartsExtendResource {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsExtendResource.class);
    private final ShoppingCartsExtendService shoppingCartsExtendService;

    public ShoppingCartsExtendResource(ShoppingCartsExtendService shoppingCartsExtendService) {
        this.shoppingCartsExtendService = shoppingCartsExtendService;
    }

    @RequestMapping(value = "/shopping-carts-extend/cart", method = RequestMethod.POST)
    public ResponseEntity addToCart(@RequestBody String payload, Principal principal) throws IOException {
        System.out.println("Add to cart");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("stockItemId");
        JsonNode jsonNode2 = actualObj.get("amount");
        Long requestStockItemId = jsonNode1.longValue();
        Integer requestProductQuantity = jsonNode2.intValue();
        ShoppingCartsDTO cart = shoppingCartsExtendService.addToCart(principal, requestStockItemId, requestProductQuantity);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart/reduce", method = RequestMethod.POST)
    public ResponseEntity reduceFormCart(@RequestBody String payload, Principal principal) throws IOException, ParseException, NoSuchFieldException {
        System.out.println("reduce from cart");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("id");
        JsonNode jsonNode2 = actualObj.get("quantity");
        Long requestProductId = jsonNode1.longValue();
        Integer requestProductQuantity = jsonNode2.intValue();
        ShoppingCartsDTO cart = shoppingCartsExtendService.reduceFromCart(principal, requestProductId, requestProductQuantity);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart", method = RequestMethod.GET)
    public ResponseEntity fetchCart(Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException {
        ShoppingCartsDTO cart = shoppingCartsExtendService.fetchCart(principal);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart", method = RequestMethod.DELETE, params = "id")
    public ResponseEntity removeFromCart(@RequestParam("id") Long id, Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException {
        ShoppingCartsDTO cart = shoppingCartsExtendService.removeFromCart(principal, id);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart/deletelist", method = RequestMethod.DELETE, params = "idlist")
    public ResponseEntity removeListFromCart(@RequestParam("idlist") String idlist, Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException {
        List<String> items = Arrays.asList(idlist.split("\\s*,\\s*"));
        List<Long> deleteList = new ArrayList<>();

        for(String i:items){
            deleteList.add(Long.parseLong(i));
        }

        ShoppingCartsDTO cart = shoppingCartsExtendService.removeListFromCart(principal, deleteList);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart/change", method = RequestMethod.POST)
    public ResponseEntity changedAddToOrder(@RequestBody String payload, Principal principal) throws IOException, ParseException, NoSuchFieldException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode actualObj = mapper.readTree(payload);
        JsonNode jsonNode1 = actualObj.get("id");
        JsonNode jsonNode2 = actualObj.get("isSelectOrder");
        Long requestId = jsonNode1.longValue();
        Boolean isAddToOrder = jsonNode2.booleanValue();
        ShoppingCartsDTO cart = shoppingCartsExtendService.changedAddToOrder(principal, requestId, isAddToOrder);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart/changeall", method = RequestMethod.POST)
    public ResponseEntity changedOrderAll(@RequestParam(value="status") Boolean status, @RequestParam(value = "packageId",required = false) String packageId,Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException {
        Long id = null;

        if(!CommonUtil.isNullOrEmptyString(packageId)){
            id = Long.parseLong(packageId);
        }

        ShoppingCartsDTO cart = shoppingCartsExtendService.checkedAll(status,id, principal);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart/delivery-method", method = RequestMethod.PUT)
    public ResponseEntity changeDeliveryMethod(@RequestParam(value="deliveryMethodId") Long deliveryMethodId, @RequestParam(value = "cartId") Long cartId, @RequestParam(value = "supplierId") Long supplierId,Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException {
        ShoppingCartsDTO cart = shoppingCartsExtendService.changeDeliveryMethod(deliveryMethodId,cartId,supplierId, principal);
        return new ResponseEntity<ShoppingCartsDTO>(cart, HttpStatus.OK);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart/confirm", method = RequestMethod.POST)
    public ResponseEntity confirmCart(@Valid @RequestBody ShoppingCarts cart, BindingResult bindingResult, Principal principal) {
        System.out.println("RequestBody -> " + cart.toString());
        if (bindingResult.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Boolean result = shoppingCartsExtendService.confirmCart(principal, cart);
        return result ? new ResponseEntity(HttpStatus.OK) : new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/shopping-carts-extend/cart", method = RequestMethod.DELETE)
    public ResponseEntity emptyCart(Principal principal) {
        shoppingCartsExtendService.emptyCart(principal);
        return new ResponseEntity(HttpStatus.OK);
    }

}
