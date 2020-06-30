package com.vertical.commerce.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.vertical.commerce.domain.*;
import com.vertical.commerce.domain.enumeration.OrderLineStatus;
import com.vertical.commerce.domain.enumeration.OrderStatus;
import com.vertical.commerce.domain.enumeration.PaymentStatus;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.OrdersExtendService;
import com.vertical.commerce.service.dto.OrdersDTO;
import com.vertical.commerce.service.mapper.OrdersMapper;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.time.Period;
import java.util.Set;

@Service
@Transactional
public class OrdersExtendServiceImpl implements OrdersExtendService {

    private final Logger log = LoggerFactory.getLogger(OrdersExtendServiceImpl.class);
    private final PeopleExtendRepository peopleExtendRepository;
    private final CustomersExtendRepository customersExtendRepository;
    private final OrdersExtendRepository ordersExtendRepository;
    private final AddressesRepository addressesRepository;
    private final OrdersMapper ordersMapper;
    private final UserRepository userRepository;
    private final CommonService commonService;
    private final OrderLinesRepository orderLinesRepository;
    private final SuppliersRepository suppliersRepository;
    private final ShoppingCartItemsExtendRepository shoppingCartItemsExtendRepository;
    private final OrderPackagesRepository orderPackagesRepository;
    private final OrderPackagesExtendRepository orderPackagesExtendRepository;
    private final StockItemsRepository stockItemsRepository;
    private final DeliveryMethodsRepository deliveryMethodsRepository;

    public OrdersExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, CustomersExtendRepository customersExtendRepository, OrdersExtendRepository ordersExtendRepository, AddressesRepository addressesRepository, OrdersMapper ordersMapper, UserRepository userRepository, CommonService commonService, OrderLinesRepository orderLinesRepository, SuppliersRepository suppliersRepository, ShoppingCartItemsExtendRepository shoppingCartItemsExtendRepository, OrderPackagesRepository orderPackagesRepository, OrderPackagesExtendRepository orderPackagesExtendRepository, StockItemsRepository stockItemsRepository, DeliveryMethodsRepository deliveryMethodsRepository) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.customersExtendRepository = customersExtendRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.addressesRepository = addressesRepository;
        this.ordersMapper = ordersMapper;
        this.userRepository = userRepository;
        this.commonService = commonService;
        this.orderLinesRepository = orderLinesRepository;
        this.suppliersRepository = suppliersRepository;
        this.shoppingCartItemsExtendRepository = shoppingCartItemsExtendRepository;
        this.orderPackagesRepository = orderPackagesRepository;
        this.orderPackagesExtendRepository = orderPackagesExtendRepository;
        this.stockItemsRepository = stockItemsRepository;
        this.deliveryMethodsRepository = deliveryMethodsRepository;
    }

    @Override
    public Integer getAllOrdersCount(Principal principal) {
        Customers customer = commonService.getCustomerByPrincipal(principal);
        return ordersExtendRepository.countAllByCustomerId(customer.getId());
    }

    @Override
    public OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO) throws JsonProcessingException, ParseException {
        People people = commonService.getPeopleByPrincipal(principal);
        Customers customer = commonService.getCustomerByPrincipal(principal);

        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }

        Orders saveOrder = new Orders();
        saveOrder.setOrderDate(Instant.now());
        saveOrder.setBillToAddress(customer.getDeliveryAddress());
        saveOrder.setShipToAddress(customer.getDeliveryAddress());
        saveOrder.setPaymentStatus(PaymentStatus.PENDING);
        saveOrder.setTotalShippingFee(cart.getTotalShippingFee());
        saveOrder.setCustomer(customer);
        saveOrder.setSpecialDeals(cart.getSpecialDeals());
        saveOrder.setTotalDue(cart.getTotalPrice());
        saveOrder.setSubTotal(cart.getSubTotalPrice());
        saveOrder.setSpecialDeals(cart.getSpecialDeals());
        saveOrder.setStatus(OrderStatus.NEW_ORDER);
        saveOrder.setLastEditedBy(people.getFullName());
        saveOrder.setLastEditedWhen(Instant.now());

        saveOrder = ordersExtendRepository.save(saveOrder);

        Set<ShoppingCartItems> cartItems = cart.getCartItemLists();

        Gson gson = new Gson();
        OrderPackagesJson[] orderPackagesJsons = gson.fromJson(cart.getPackageDetails(),OrderPackagesJson[].class);

        for(OrderPackagesJson orderPackagesJson:orderPackagesJsons){

            OrderPackages orderPackages = new OrderPackages();
            orderPackages.setLastEditedBy(people.getFullName());
            orderPackages.setLastEditedWhen(Instant.now());

            Suppliers suppliers = suppliersRepository.getOne(orderPackagesJson.getSupplierId());
            orderPackages.setSupplier(suppliers);
            orderPackages.setOrder(saveOrder);
            orderPackages.setCompletedReview(false);

            orderPackages.setPackageShippingFee(orderPackagesJson.getShippingFee());
            orderPackages.setPackagePrice(orderPackagesJson.getTotalPrice());

            ShoppingItems[] shoppingItems = gson.fromJson(orderPackagesJson.getShoppingItems(),ShoppingItems[].class);

            DeliveryMethods deliveryMethods = null;

            for (ShoppingItems item : shoppingItems) {

                if(deliveryMethods == null){
                    deliveryMethods =deliveryMethodsRepository .getOne(item.getDeliveryMethodId());
                }
                OrderLines orderLines = new OrderLines();
                orderLines.setQuantity(item.getQuantity());
                StockItems stockItems = stockItemsRepository.getOne(item.getStockItemId());
                orderLines.setStockItem(stockItems);
                orderLines.setUnitPrice(item.getUnitPrice());
                orderLines.setLastEditedBy(people.getFullName());
                orderLines.setLastEditedWhen(Instant.now());
                orderLines.setStatus(OrderLineStatus.AVAILABLE);
                orderLines.setThumbnailUrl(stockItems.getThumbnailUrl());
                orderLines.setDescription(stockItems.getName());
                orderLines.setTaxRate(stockItems.getTaxRate());
                orderLines.setSupplier(suppliers);
                orderLines.setOrderPackage(orderPackages);

                orderLinesRepository.save(orderLines);
            }

            JSONObject orderPackageObject = new JSONObject();
            orderPackageObject.put("expectedMinArrivalDays",deliveryMethods.getExpectedMinArrivalDays());
            orderPackageObject.put("expectedMaxArrivalDays",deliveryMethods.getExpectedMaxArrivalDays());
            orderPackages.setDeliveryMethod(deliveryMethods);
            orderPackages.setOrderPackageDetails(orderPackageObject.toJSONString());
            orderPackages.setExpectedDeliveryDate(Instant.now().plus(Period.ofDays(deliveryMethods.getExpectedMinArrivalDays())));
            orderPackages = orderPackagesRepository.save(orderPackages);
        }

        saveOrder.setCustomerPurchaseOrderNumber("SO" + saveOrder.getId());
        saveOrder = ordersExtendRepository.save(saveOrder);
        saveOrder.setOrderDetails(ordersExtendRepository.getOrderPackageDetails(saveOrder.getId()));
        saveOrder = ordersExtendRepository.save(saveOrder);

        return ordersMapper.toDto(saveOrder);
    }
}
