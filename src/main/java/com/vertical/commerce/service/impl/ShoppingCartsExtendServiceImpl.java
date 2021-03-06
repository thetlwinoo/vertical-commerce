package com.vertical.commerce.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vertical.commerce.domain.*;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.security.SecurityUtils;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.PriceService;
import com.vertical.commerce.service.ShoppingCartsExtendService;
import com.vertical.commerce.service.dto.ShoppingCartsDTO;
import com.vertical.commerce.service.mapper.ShoppingCartsMapper;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class ShoppingCartsExtendServiceImpl implements ShoppingCartsExtendService {

    private final Logger log = LoggerFactory.getLogger(ShoppingCartsExtendServiceImpl.class);
    private final ShoppingCartsRepository shoppingCartsRepository;
    private final ShoppingCartItemsRepository shoppingCartItemsRepository;
    private final ShoppingCartsExtendRepository shoppingCartsExtendRepository;
    private final ShoppingCartItemsExtendRepository shoppingCartItemsExtendRepository;
    private final PriceService priceService;
    private final PeopleExtendRepository peopleExtendRepository;
    private final StockItemsRepository stockItemsRepository;
    private final ShoppingCartsMapper shoppingCartsMapper;
    private final CommonService commonService;

    public ShoppingCartsExtendServiceImpl(ShoppingCartsRepository shoppingCartsRepository, ShoppingCartItemsRepository shoppingCartItemsRepository, ShoppingCartsExtendRepository shoppingCartsExtendRepository, ShoppingCartItemsExtendRepository shoppingCartItemsExtendRepository, PriceService priceService, PeopleExtendRepository peopleExtendRepository, StockItemsRepository stockItemsRepository, ShoppingCartsMapper shoppingCartsMapper, CommonService commonService) {
        this.shoppingCartsRepository = shoppingCartsRepository;
        this.shoppingCartItemsRepository = shoppingCartItemsRepository;
        this.shoppingCartsExtendRepository = shoppingCartsExtendRepository;
        this.shoppingCartItemsExtendRepository = shoppingCartItemsExtendRepository;
        this.priceService = priceService;
        this.peopleExtendRepository = peopleExtendRepository;
        this.stockItemsRepository = stockItemsRepository;
        this.shoppingCartsMapper = shoppingCartsMapper;
        this.commonService = commonService;
    }

    @Override
    public ShoppingCartsDTO addToCart(Principal principal, Long id, Integer quantity) {
        try {
            People people = commonService.getPeopleByPrincipal(principal);
            String userLogin = SecurityUtils.getCurrentUserLogin().get();

            if (quantity <= 0 || id <= 0) {
                throw new IllegalArgumentException("Invalid parameters");
            }

            ShoppingCarts cart = people.getCart();

            Customers customer = commonService.getCustomerByPrincipal(principal);

            if (cart == null) {
                cart = new ShoppingCarts();
                cart.setCartUser(people);
                cart.setCustomer(customer);
                cart.setLastEditedBy(userLogin);
                cart.setLastEditedWhen(Instant.now());
                shoppingCartsRepository.save(cart);
            } else if (cart.getCartItemLists() != null || !cart.getCartItemLists().isEmpty()) {
                for (ShoppingCartItems i : cart.getCartItemLists()) {
                    if (i.getStockItem().getId().equals(id)) {
                        i.setQuantity(i.getQuantity() + quantity);
                        ShoppingCarts returnCart = priceService.calculatePrice(cart);
                        returnCart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));
                        shoppingCartsRepository.save(returnCart);
                        return shoppingCartsMapper.toDto(returnCart) ;
                    }
                }
            }

            StockItems stockItems = stockItemsRepository.getOne(id);
            ShoppingCartItems cartItem = new ShoppingCartItems();
            cartItem.setQuantity(quantity);
            cartItem.setStockItem(stockItems);
            cartItem.setLastEditedBy(userLogin);
            cartItem.setLastEditedWhen(Instant.now());
            cartItem.setSelectOrder(false);
            cartItem.setDeliveryMethod(commonService.getDeliveryMethodsEntity(null,"Standard"));
            cartItem.setCart(cart);
            cart.getCartItemLists().add(cartItem);
            cart = priceService.calculatePrice(cart);

            shoppingCartItemsRepository.save(cartItem);

            cart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));
            shoppingCartsRepository.save(cart);

            return shoppingCartsMapper.toDto(cart) ;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    @Override
    public ShoppingCartsDTO fetchCart(Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException {
        System.out.println("FETCH CART");
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts cart =  people.getCart();

        if(cart != null){
            cart = priceService.calculatePrice(cart);
            cart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));
        }


        return shoppingCartsMapper.toDto(cart) ;
    }

    @Override
    public ShoppingCartsDTO removeFromCart(Principal principal, Long id) throws ParseException, NoSuchFieldException, JsonProcessingException {
        System.out.println("Remove CartItem id " + id);
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();
        ShoppingCartItems cartItemToDelete = null;

        for (ShoppingCartItems i : cartItemsList) {
            if (i.getId().equals(id)) {
                cartItemToDelete = i;
                break;
            }
        }
        if (cartItemToDelete == null) {
            throw new IllegalArgumentException("CartItem not found");
        }

        cartItemsList.remove(cartItemToDelete);

        if (cart.getCartItemLists() == null || cart.getCartItemLists().size() == 0) {
            people.setCart(null);
            peopleExtendRepository.save(people);
            return null;
        }

        cart.setCartItemLists(cartItemsList);
        cart = priceService.calculatePrice(cart);
        shoppingCartItemsRepository.delete(cartItemToDelete);
        cart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));
        shoppingCartsRepository.save(cart);

        return shoppingCartsMapper.toDto(cart);
    }

    @Transactional
    @Override
    public ShoppingCartsDTO removeListFromCart(Principal principal, List<Long> idList) throws ParseException, NoSuchFieldException, JsonProcessingException {
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }

        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();
        Set<ShoppingCartItems> confirmDeleteList = new HashSet<>();

        for (ShoppingCartItems i : cartItemsList) {
            if(idList.contains(i.getId())){
                confirmDeleteList.add(i);
            }
        }

        cartItemsList.removeAll(confirmDeleteList);

        if (cart.getCartItemLists() == null || cart.getCartItemLists().size() == 0) {
            people.setCart(null);
            peopleExtendRepository.save(people);
            return null;
        }

        cart.setCartItemLists(cartItemsList);
        cart = priceService.calculatePrice(cart);
        cart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));
        shoppingCartsRepository.save(cart);

        return shoppingCartsMapper.toDto(cart);
    }

    @Override
    public ShoppingCartsDTO reduceFromCart(Principal principal, Long id, Integer quantity) throws ParseException, NoSuchFieldException, JsonProcessingException {
        System.out.println("Remove CartItem id " + id);
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();
        ShoppingCartItems cartItemToEdit = null;

        for (ShoppingCartItems i : cartItemsList) {
            if (i.getId().equals(id)) {
                cartItemToEdit = i;
                break;
            }
        }
        if (cartItemToEdit == null) {
            throw new IllegalArgumentException("CartItem not found");
        }

        if (cart.getCartItemLists() == null || cart.getCartItemLists().size() == 0) {
            people.setCart(null);
            peopleExtendRepository.save(people);
            return null;
        }

        if (cartItemToEdit.getQuantity() > quantity) {
            cartItemToEdit.setQuantity(cartItemToEdit.getQuantity() - quantity);

            cart.setCartItemLists(cartItemsList);
            cart = priceService.calculatePrice(cart);
            shoppingCartItemsRepository.save(cartItemToEdit);
            cart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));

        } else {
            cartItemsList.remove(cartItemToEdit);
            shoppingCartItemsRepository.delete(cartItemToEdit);
        }


        shoppingCartsRepository.save(cart);

        return shoppingCartsMapper.toDto(cart);
    }

    @Override
    public Boolean confirmCart(Principal principal, ShoppingCarts cart) {
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts dbCart = people.getCart();
        if (dbCart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> dbCartItemsList = dbCart.getCartItemLists();
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();

        if (dbCartItemsList.size() != cartItemsList.size()) {
            return false;
        }

        if (
            dbCart.getTotalPrice().equals(cart.getTotalPrice())
                && dbCart.getTotalShippingFee().equals(cart.getTotalShippingFee())
                && dbCart.getId().equals(cart.getId())) {
            if (dbCart.getSpecialDeals() != null && cart.getSpecialDeals() != null) {
                if (dbCart.getSpecialDeals().getDiscountPercentage().equals(cart.getSpecialDeals().getDiscountPercentage())
                    && dbCart.getSpecialDeals().getDiscountCode().equals(cart.getSpecialDeals().getDiscountCode())) {
                    System.out.println("equals");
                    return true;
                }
            } else if (dbCart.getSpecialDeals() == null && cart.getSpecialDeals() == null) {
                System.out.println("equals");
                return true;
            }

        }
        System.out.println("no u");
        System.out.println(dbCart.getCartItemLists().equals(cart.getCartItemLists()));
        return false;
    }

    @Override
    public ShoppingCartsDTO changedAddToOrder(Principal principal, Long id, Boolean selectOrder) throws ParseException, NoSuchFieldException, JsonProcessingException {
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();
        ShoppingCartItems cartItemToEdit = null;

        for (ShoppingCartItems i : cartItemsList) {
            if (i.getId().equals(id)) {
                cartItemToEdit = i;
                break;
            }
        }
        if (cartItemToEdit == null) {
            throw new IllegalArgumentException("CartItem not found");
        }

        cartItemToEdit.setSelectOrder(selectOrder);
        shoppingCartItemsRepository.save(cartItemToEdit);
        cart = priceService.calculatePrice(cart);

        cart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));
        shoppingCartsRepository.save(cart);
        return shoppingCartsMapper.toDto(cart);
    }

    @Override
    public ShoppingCartsDTO checkedAll(Boolean checked,Long packageId,Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException {
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();

        if(packageId == null){
            for (ShoppingCartItems i : cartItemsList) {
                if(i.getStockItem().getQuantityOnHand() > 0){
                    i.setSelectOrder(checked);
                }
            }
        }else{
            for (ShoppingCartItems i : cartItemsList) {
                if(i.getStockItem().getProduct().getSupplier().getId().equals(packageId)){
                    if(i.getStockItem().getQuantityOnHand() > 0){
                        i.setSelectOrder(checked);
                    }
                }
            }
        }


        cart = priceService.calculatePrice(cart);
        cart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));
        shoppingCartsRepository.save(cart);
        return shoppingCartsMapper.toDto(cart);
    }

    @Override
    public ShoppingCartsDTO changeDeliveryMethod(Long deliveryMethodId,Long cartid,Long supplierId, Principal principal) throws ParseException, NoSuchFieldException, JsonProcessingException{
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts cart = people.getCart();

        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }
        Set<ShoppingCartItems> cartItemsList = cart.getCartItemLists();

        for (ShoppingCartItems i : cartItemsList) {
            if(i.getStockItem().getProduct().getSupplier().getId().equals(supplierId) && i.isSelectOrder()){
                DeliveryMethods deliveryMethods = commonService.getDeliveryMethodsEntity(deliveryMethodId,null);
                i.setDeliveryMethod(deliveryMethods);
            }
        }

        cart = priceService.calculatePrice(cart);
        cart.setCartDetails(shoppingCartsExtendRepository.getCartDetails(cart.getId()));
        shoppingCartsRepository.save(cart);
        return shoppingCartsMapper.toDto(cart);
    }

    @Override
    public void emptyCart(Principal principal) {
        People people = commonService.getPeopleByPrincipal(principal);
        people.setCart(null);
        peopleExtendRepository.save(people);
    }


}
