package com.vertical.commerce.service.impl;

import com.vertical.commerce.domain.*;
import com.vertical.commerce.domain.enumeration.OrderLineStatus;
import com.vertical.commerce.domain.enumeration.OrderStatus;
import com.vertical.commerce.domain.enumeration.PaymentStatus;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.OrdersExtendService;
import com.vertical.commerce.service.dto.OrdersDTO;
import com.vertical.commerce.service.mapper.OrdersMapper;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    public OrdersExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, CustomersExtendRepository customersExtendRepository, OrdersExtendRepository ordersExtendRepository, AddressesRepository addressesRepository, OrdersMapper ordersMapper, UserRepository userRepository, CommonService commonService, OrderLinesRepository orderLinesRepository) {
        this.peopleExtendRepository = peopleExtendRepository;
        this.customersExtendRepository = customersExtendRepository;
        this.ordersExtendRepository = ordersExtendRepository;
        this.addressesRepository = addressesRepository;
        this.ordersMapper = ordersMapper;
        this.userRepository = userRepository;
        this.commonService = commonService;
        this.orderLinesRepository = orderLinesRepository;
    }

    @Override
    public Integer getAllOrdersCount(Principal principal) {
        Customers customer = commonService.getCustomerByPrincipal(principal);
        return ordersExtendRepository.countAllByCustomerId(customer.getId());
    }

    @Override
    public OrdersDTO postOrder(Principal principal, OrdersDTO ordersDTO) {
        People people = commonService.getPeopleByPrincipal(principal);
        ShoppingCarts cart = people.getCart();
        if (cart == null) {
            throw new IllegalArgumentException("Cart not found");
        }

        Customers customer = commonService.getCustomerByPrincipal(principal);

        Orders saveOrder = new Orders();

        saveOrder.setOrderDate(Instant.now());
        saveOrder.setDueDate(Instant.now());
        saveOrder.setExpectedDeliveryDate(Instant.now());
        Addresses billToAddress = addressesRepository.getOne(ordersDTO.getBillToAddressId());
        saveOrder.setBillToAddress(billToAddress);
        Addresses shipToAddress = addressesRepository.getOne(ordersDTO.getShipToAddressId());
        saveOrder.setShipToAddress(shipToAddress);

        saveOrder.setPaymentStatus(PaymentStatus.PENDING);
        saveOrder.setFrieight(cart.getTotalCargoPrice());
        saveOrder.setCustomer(customer);
        saveOrder.setSpecialDeals(cart.getSpecialDeals());
        saveOrder.setTotalDue(cart.getTotalPrice());
        saveOrder.setSpecialDeals(cart.getSpecialDeals());
        saveOrder.setStatus(OrderStatus.NEW_ORDER);
        saveOrder.setCompletedReview(false);
        saveOrder.setLastEditedBy(people.getFullName());
        saveOrder.setLastEditedWhen(Instant.now());

        saveOrder = ordersExtendRepository.save(saveOrder);

        Set<ShoppingCartItems> cartItems = cart.getCartItemLists();
        List<String> orderLineList= new ArrayList<>();
        for (ShoppingCartItems i : cartItems) {
            Integer sellCount = i.getStockItem().getSellCount() == null ? 0 : i.getStockItem().getSellCount();
            i.getStockItem().setSellCount(sellCount + i.getQuantity());

            OrderLines orderLines = new OrderLines();
            orderLines.setQuantity(i.getQuantity());
            orderLines.setOrder(saveOrder);
            orderLines.setStockItem(i.getStockItem());
            orderLines.setUnitPrice(i.getStockItem().getUnitPrice());
            orderLines.setLastEditedBy(people.getFullName());
            orderLines.setLastEditedWhen(Instant.now());
            orderLines.setStatus(OrderLineStatus.AVAILABLE);
            orderLines.setThumbnailUrl(i.getStockItem().getThumbnailUrl());
            orderLines.setDescription(i.getStockItem().getName());
            orderLines = orderLinesRepository.save(orderLines);
            String orderLineString = commonService.getOrderLineString(saveOrder,orderLines);
            orderLineList.add(orderLineString);
        }

        saveOrder.setAccountNumber("SO" + saveOrder.getId());
        saveOrder.setOrderLineString(String.join(";",orderLineList));
        saveOrder = ordersExtendRepository.save(saveOrder);

        return ordersMapper.toDto(saveOrder);
    }
}
