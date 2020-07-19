package com.vertical.commerce.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.vertical.commerce.domain.*;
import com.vertical.commerce.domain.enumeration.OrderLineStatus;
import com.vertical.commerce.domain.enumeration.OrderStatus;
import com.vertical.commerce.domain.enumeration.PaymentStatus;
import com.vertical.commerce.repository.*;
import com.vertical.commerce.security.SecurityUtils;
import com.vertical.commerce.service.CommonService;
import com.vertical.commerce.service.OrderPackagesService;
import com.vertical.commerce.service.OrdersExtendService;
import com.vertical.commerce.service.OrdersQueryService;
import com.vertical.commerce.service.dto.OrdersCriteria;
import com.vertical.commerce.service.dto.OrdersDTO;
import com.vertical.commerce.service.mapper.OrdersMapper;
import io.github.jhipster.service.filter.LongFilter;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.Instant;
import java.time.Period;
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
    private final SuppliersRepository suppliersRepository;
    private final ShoppingCartItemsExtendRepository shoppingCartItemsExtendRepository;
    private final OrderPackagesRepository orderPackagesRepository;
    private final OrderPackagesService orderPackagesService;
    private final OrderPackagesExtendRepository orderPackagesExtendRepository;
    private final StockItemsRepository stockItemsRepository;
    private final DeliveryMethodsRepository deliveryMethodsRepository;
    private final OrdersQueryService ordersQueryService;
    private final TrackingEventExtendRepository trackingEventExtendRepository;
    private final OrderTrackingRepository orderTrackingRepository;

    public OrdersExtendServiceImpl(PeopleExtendRepository peopleExtendRepository, CustomersExtendRepository customersExtendRepository, OrdersExtendRepository ordersExtendRepository, AddressesRepository addressesRepository, OrdersMapper ordersMapper, UserRepository userRepository, CommonService commonService, OrderLinesRepository orderLinesRepository, SuppliersRepository suppliersRepository, ShoppingCartItemsExtendRepository shoppingCartItemsExtendRepository, OrderPackagesRepository orderPackagesRepository, OrderPackagesService orderPackagesService, OrderPackagesExtendRepository orderPackagesExtendRepository, StockItemsRepository stockItemsRepository, DeliveryMethodsRepository deliveryMethodsRepository, OrdersQueryService ordersQueryService, TrackingEventExtendRepository trackingEventExtendRepository, OrderTrackingRepository orderTrackingRepository) {
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
        this.orderPackagesService = orderPackagesService;
        this.orderPackagesExtendRepository = orderPackagesExtendRepository;
        this.stockItemsRepository = stockItemsRepository;
        this.deliveryMethodsRepository = deliveryMethodsRepository;
        this.ordersQueryService = ordersQueryService;
        this.trackingEventExtendRepository = trackingEventExtendRepository;
        this.orderTrackingRepository = orderTrackingRepository;
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
        String userLogin = SecurityUtils.getCurrentUserLogin().get();

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
        saveOrder.setLastEditedBy(userLogin);
        saveOrder.setLastEditedWhen(Instant.now());

        saveOrder = ordersExtendRepository.save(saveOrder);

        Set<ShoppingCartItems> cartItems = cart.getCartItemLists();

        Gson gson = new Gson();
        OrderPackagesJson[] orderPackagesJsons = gson.fromJson(cart.getPackageDetails(),OrderPackagesJson[].class);

        for(OrderPackagesJson orderPackagesJson:orderPackagesJsons){

            OrderPackages orderPackages = new OrderPackages();
            orderPackages.setLastEditedBy(userLogin);
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
                orderLines.setLastEditedBy(userLogin);
                orderLines.setLastEditedWhen(Instant.now());
                orderLines.setStatus(OrderLineStatus.AVAILABLE);
                orderLines.setStockItemPhoto(stockItems.getThumbnailPhoto());
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

        OrderTracking orderTracking = new OrderTracking();
        TrackingEvent trackingEvent = trackingEventExtendRepository.findTrackingEventByNameEquals("Payment Pending");
        orderTracking.setTrackingEvent(trackingEvent);
        orderTracking.setOrder(saveOrder);
        orderTracking.setEventDate(Instant.now());
        orderTracking.setEventDetails("Thank you for shopping at ZeZaWar! If you did not select a payment method, click PAY NOW above. Otherwise, your order will be cancelled automatically after 45 minutes or item is out of stock. An update will be sent via email once your order is successfully placed.");
        orderTrackingRepository.save(orderTracking);

        return ordersMapper.toDto(saveOrder);
    }

    @Override
    public Page<OrdersDTO> getCustomerOrdersReviews(Boolean completedReview, OrdersCriteria criteria, Pageable pageable, Principal principal) {
        Customers customer = commonService.getCustomerByPrincipal(principal);

        LongFilter customerIdFilter = new LongFilter();
        customerIdFilter.setEquals(customer.getId());
        criteria.setCustomerId(customerIdFilter);

        OrdersCriteria.OrderStatusFilter orderStatusFilter=new OrdersCriteria.OrderStatusFilter();
        orderStatusFilter.setEquals(OrderStatus.COMPLETED);
        criteria.setStatus(orderStatusFilter);

        List<Long> orderPackageIds = ordersExtendRepository.getOrderPackagesIdsByFilter(customer.getId(),completedReview);
        LongFilter orderPackageListIdFilter = new LongFilter();
        orderPackageListIdFilter.setIn(orderPackageIds);
        criteria.setOrderPackageListId(orderPackageListIdFilter);

        Page<OrdersDTO> page = ordersQueryService.findByCriteria(criteria, pageable);

        return page;
    }

    @Override
    public Page<OrdersDTO> getAllOrders(OrdersCriteria criteria, Pageable pageable, Principal principal){

        Customers customer = commonService.getCustomerByPrincipal(principal);

        LongFilter customerIdFilter = new LongFilter();
        customerIdFilter.setEquals(customer.getId());
        criteria.setCustomerId(customerIdFilter);

        Page<OrdersDTO> page = ordersQueryService.findByCriteria(criteria, pageable);

        return page;
    }
}
