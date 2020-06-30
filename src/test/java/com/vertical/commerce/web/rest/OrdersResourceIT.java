package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.domain.OrderPackages;
import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.domain.Addresses;
import com.vertical.commerce.domain.CurrencyRate;
import com.vertical.commerce.domain.PaymentMethods;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.OrderTracking;
import com.vertical.commerce.domain.SpecialDeals;
import com.vertical.commerce.repository.OrdersRepository;
import com.vertical.commerce.service.OrdersService;
import com.vertical.commerce.service.dto.OrdersDTO;
import com.vertical.commerce.service.mapper.OrdersMapper;
import com.vertical.commerce.service.dto.OrdersCriteria;
import com.vertical.commerce.service.OrdersQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vertical.commerce.domain.enumeration.PaymentStatus;
import com.vertical.commerce.domain.enumeration.OrderStatus;
/**
 * Integration tests for the {@link OrdersResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class OrdersResourceIT {

    private static final Instant DEFAULT_ORDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_SUB_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SUB_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SUB_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_SHIPPING_FEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SHIPPING_FEE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SHIPPING_FEE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_VOUCHER_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_VOUCHER_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_VOUCHER_DISCOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PROMTION_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PROMTION_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PROMTION_DISCOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_DUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_DUE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_DUE = new BigDecimal(1 - 1);

    private static final PaymentStatus DEFAULT_PAYMENT_STATUS = PaymentStatus.PENDING;
    private static final PaymentStatus UPDATED_PAYMENT_STATUS = PaymentStatus.PENDING;

    private static final String DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER = "BBBBBBBBBB";

    private static final OrderStatus DEFAULT_STATUS = OrderStatus.NEW_ORDER;
    private static final OrderStatus UPDATED_STATUS = OrderStatus.COMPLETED;

    private static final String DEFAULT_ORDER_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_DETAILS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED = false;
    private static final Boolean UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED = true;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdersQueryService ordersQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrdersMockMvc;

    private Orders orders;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orders createEntity(EntityManager em) {
        Orders orders = new Orders()
            .orderDate(DEFAULT_ORDER_DATE)
            .subTotal(DEFAULT_SUB_TOTAL)
            .totalTaxAmount(DEFAULT_TOTAL_TAX_AMOUNT)
            .totalShippingFee(DEFAULT_TOTAL_SHIPPING_FEE)
            .totalShippingFeeDiscount(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT)
            .totalVoucherDiscount(DEFAULT_TOTAL_VOUCHER_DISCOUNT)
            .totalPromtionDiscount(DEFAULT_TOTAL_PROMTION_DISCOUNT)
            .totalDue(DEFAULT_TOTAL_DUE)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .customerPurchaseOrderNumber(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)
            .status(DEFAULT_STATUS)
            .orderDetails(DEFAULT_ORDER_DETAILS)
            .isUnderSupplyBackOrdered(DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return orders;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Orders createUpdatedEntity(EntityManager em) {
        Orders orders = new Orders()
            .orderDate(UPDATED_ORDER_DATE)
            .subTotal(UPDATED_SUB_TOTAL)
            .totalTaxAmount(UPDATED_TOTAL_TAX_AMOUNT)
            .totalShippingFee(UPDATED_TOTAL_SHIPPING_FEE)
            .totalShippingFeeDiscount(UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT)
            .totalVoucherDiscount(UPDATED_TOTAL_VOUCHER_DISCOUNT)
            .totalPromtionDiscount(UPDATED_TOTAL_PROMTION_DISCOUNT)
            .totalDue(UPDATED_TOTAL_DUE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .customerPurchaseOrderNumber(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER)
            .status(UPDATED_STATUS)
            .orderDetails(UPDATED_ORDER_DETAILS)
            .isUnderSupplyBackOrdered(UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return orders;
    }

    @BeforeEach
    public void initTest() {
        orders = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrders() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();
        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);
        restOrdersMockMvc.perform(post("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isCreated());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate + 1);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrders.getSubTotal()).isEqualTo(DEFAULT_SUB_TOTAL);
        assertThat(testOrders.getTotalTaxAmount()).isEqualTo(DEFAULT_TOTAL_TAX_AMOUNT);
        assertThat(testOrders.getTotalShippingFee()).isEqualTo(DEFAULT_TOTAL_SHIPPING_FEE);
        assertThat(testOrders.getTotalShippingFeeDiscount()).isEqualTo(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);
        assertThat(testOrders.getTotalVoucherDiscount()).isEqualTo(DEFAULT_TOTAL_VOUCHER_DISCOUNT);
        assertThat(testOrders.getTotalPromtionDiscount()).isEqualTo(DEFAULT_TOTAL_PROMTION_DISCOUNT);
        assertThat(testOrders.getTotalDue()).isEqualTo(DEFAULT_TOTAL_DUE);
        assertThat(testOrders.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testOrders.getCustomerPurchaseOrderNumber()).isEqualTo(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);
        assertThat(testOrders.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrders.getOrderDetails()).isEqualTo(DEFAULT_ORDER_DETAILS);
        assertThat(testOrders.isIsUnderSupplyBackOrdered()).isEqualTo(DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED);
        assertThat(testOrders.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testOrders.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createOrdersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordersRepository.findAll().size();

        // Create the Orders with an existing ID
        orders.setId(1L);
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdersMockMvc.perform(post("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrderDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setOrderDate(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);


        restOrdersMockMvc.perform(post("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setStatus(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);


        restOrdersMockMvc.perform(post("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setLastEditedBy(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);


        restOrdersMockMvc.perform(post("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = ordersRepository.findAll().size();
        // set the field null
        orders.setLastEditedWhen(null);

        // Create the Orders, which fails.
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);


        restOrdersMockMvc.perform(post("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].totalTaxAmount").value(hasItem(DEFAULT_TOTAL_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalShippingFee").value(hasItem(DEFAULT_TOTAL_SHIPPING_FEE.intValue())))
            .andExpect(jsonPath("$.[*].totalShippingFeeDiscount").value(hasItem(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalVoucherDiscount").value(hasItem(DEFAULT_TOTAL_VOUCHER_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalPromtionDiscount").value(hasItem(DEFAULT_TOTAL_PROMTION_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalDue").value(hasItem(DEFAULT_TOTAL_DUE.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].customerPurchaseOrderNumber").value(hasItem(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderDetails").value(hasItem(DEFAULT_ORDER_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].isUnderSupplyBackOrdered").value(hasItem(DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }

    @Test
    @Transactional
    public void getOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", orders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orders.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.subTotal").value(DEFAULT_SUB_TOTAL.intValue()))
            .andExpect(jsonPath("$.totalTaxAmount").value(DEFAULT_TOTAL_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.totalShippingFee").value(DEFAULT_TOTAL_SHIPPING_FEE.intValue()))
            .andExpect(jsonPath("$.totalShippingFeeDiscount").value(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.totalVoucherDiscount").value(DEFAULT_TOTAL_VOUCHER_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.totalPromtionDiscount").value(DEFAULT_TOTAL_PROMTION_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.totalDue").value(DEFAULT_TOTAL_DUE.intValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS.toString()))
            .andExpect(jsonPath("$.customerPurchaseOrderNumber").value(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.orderDetails").value(DEFAULT_ORDER_DETAILS.toString()))
            .andExpect(jsonPath("$.isUnderSupplyBackOrdered").value(DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED.booleanValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getOrdersByIdFiltering() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        Long id = orders.getId();

        defaultOrdersShouldBeFound("id.equals=" + id);
        defaultOrdersShouldNotBeFound("id.notEquals=" + id);

        defaultOrdersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrdersShouldNotBeFound("id.greaterThan=" + id);

        defaultOrdersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrdersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate equals to DEFAULT_ORDER_DATE
        defaultOrdersShouldBeFound("orderDate.equals=" + DEFAULT_ORDER_DATE);

        // Get all the ordersList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrdersShouldNotBeFound("orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate not equals to DEFAULT_ORDER_DATE
        defaultOrdersShouldNotBeFound("orderDate.notEquals=" + DEFAULT_ORDER_DATE);

        // Get all the ordersList where orderDate not equals to UPDATED_ORDER_DATE
        defaultOrdersShouldBeFound("orderDate.notEquals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate in DEFAULT_ORDER_DATE or UPDATED_ORDER_DATE
        defaultOrdersShouldBeFound("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE);

        // Get all the ordersList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrdersShouldNotBeFound("orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where orderDate is not null
        defaultOrdersShouldBeFound("orderDate.specified=true");

        // Get all the ordersList where orderDate is null
        defaultOrdersShouldNotBeFound("orderDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal equals to DEFAULT_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.equals=" + DEFAULT_SUB_TOTAL);

        // Get all the ordersList where subTotal equals to UPDATED_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.equals=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal not equals to DEFAULT_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.notEquals=" + DEFAULT_SUB_TOTAL);

        // Get all the ordersList where subTotal not equals to UPDATED_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.notEquals=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal in DEFAULT_SUB_TOTAL or UPDATED_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.in=" + DEFAULT_SUB_TOTAL + "," + UPDATED_SUB_TOTAL);

        // Get all the ordersList where subTotal equals to UPDATED_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.in=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal is not null
        defaultOrdersShouldBeFound("subTotal.specified=true");

        // Get all the ordersList where subTotal is null
        defaultOrdersShouldNotBeFound("subTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal is greater than or equal to DEFAULT_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.greaterThanOrEqual=" + DEFAULT_SUB_TOTAL);

        // Get all the ordersList where subTotal is greater than or equal to UPDATED_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.greaterThanOrEqual=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal is less than or equal to DEFAULT_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.lessThanOrEqual=" + DEFAULT_SUB_TOTAL);

        // Get all the ordersList where subTotal is less than or equal to SMALLER_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.lessThanOrEqual=" + SMALLER_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal is less than DEFAULT_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.lessThan=" + DEFAULT_SUB_TOTAL);

        // Get all the ordersList where subTotal is less than UPDATED_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.lessThan=" + UPDATED_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersBySubTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where subTotal is greater than DEFAULT_SUB_TOTAL
        defaultOrdersShouldNotBeFound("subTotal.greaterThan=" + DEFAULT_SUB_TOTAL);

        // Get all the ordersList where subTotal is greater than SMALLER_SUB_TOTAL
        defaultOrdersShouldBeFound("subTotal.greaterThan=" + SMALLER_SUB_TOTAL);
    }


    @Test
    @Transactional
    public void getAllOrdersByTotalTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalTaxAmount equals to DEFAULT_TOTAL_TAX_AMOUNT
        defaultOrdersShouldBeFound("totalTaxAmount.equals=" + DEFAULT_TOTAL_TAX_AMOUNT);

        // Get all the ordersList where totalTaxAmount equals to UPDATED_TOTAL_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("totalTaxAmount.equals=" + UPDATED_TOTAL_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalTaxAmount not equals to DEFAULT_TOTAL_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("totalTaxAmount.notEquals=" + DEFAULT_TOTAL_TAX_AMOUNT);

        // Get all the ordersList where totalTaxAmount not equals to UPDATED_TOTAL_TAX_AMOUNT
        defaultOrdersShouldBeFound("totalTaxAmount.notEquals=" + UPDATED_TOTAL_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalTaxAmount in DEFAULT_TOTAL_TAX_AMOUNT or UPDATED_TOTAL_TAX_AMOUNT
        defaultOrdersShouldBeFound("totalTaxAmount.in=" + DEFAULT_TOTAL_TAX_AMOUNT + "," + UPDATED_TOTAL_TAX_AMOUNT);

        // Get all the ordersList where totalTaxAmount equals to UPDATED_TOTAL_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("totalTaxAmount.in=" + UPDATED_TOTAL_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalTaxAmount is not null
        defaultOrdersShouldBeFound("totalTaxAmount.specified=true");

        // Get all the ordersList where totalTaxAmount is null
        defaultOrdersShouldNotBeFound("totalTaxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalTaxAmount is greater than or equal to DEFAULT_TOTAL_TAX_AMOUNT
        defaultOrdersShouldBeFound("totalTaxAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_TAX_AMOUNT);

        // Get all the ordersList where totalTaxAmount is greater than or equal to UPDATED_TOTAL_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("totalTaxAmount.greaterThanOrEqual=" + UPDATED_TOTAL_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalTaxAmount is less than or equal to DEFAULT_TOTAL_TAX_AMOUNT
        defaultOrdersShouldBeFound("totalTaxAmount.lessThanOrEqual=" + DEFAULT_TOTAL_TAX_AMOUNT);

        // Get all the ordersList where totalTaxAmount is less than or equal to SMALLER_TOTAL_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("totalTaxAmount.lessThanOrEqual=" + SMALLER_TOTAL_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalTaxAmount is less than DEFAULT_TOTAL_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("totalTaxAmount.lessThan=" + DEFAULT_TOTAL_TAX_AMOUNT);

        // Get all the ordersList where totalTaxAmount is less than UPDATED_TOTAL_TAX_AMOUNT
        defaultOrdersShouldBeFound("totalTaxAmount.lessThan=" + UPDATED_TOTAL_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalTaxAmount is greater than DEFAULT_TOTAL_TAX_AMOUNT
        defaultOrdersShouldNotBeFound("totalTaxAmount.greaterThan=" + DEFAULT_TOTAL_TAX_AMOUNT);

        // Get all the ordersList where totalTaxAmount is greater than SMALLER_TOTAL_TAX_AMOUNT
        defaultOrdersShouldBeFound("totalTaxAmount.greaterThan=" + SMALLER_TOTAL_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFee equals to DEFAULT_TOTAL_SHIPPING_FEE
        defaultOrdersShouldBeFound("totalShippingFee.equals=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the ordersList where totalShippingFee equals to UPDATED_TOTAL_SHIPPING_FEE
        defaultOrdersShouldNotBeFound("totalShippingFee.equals=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFee not equals to DEFAULT_TOTAL_SHIPPING_FEE
        defaultOrdersShouldNotBeFound("totalShippingFee.notEquals=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the ordersList where totalShippingFee not equals to UPDATED_TOTAL_SHIPPING_FEE
        defaultOrdersShouldBeFound("totalShippingFee.notEquals=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFee in DEFAULT_TOTAL_SHIPPING_FEE or UPDATED_TOTAL_SHIPPING_FEE
        defaultOrdersShouldBeFound("totalShippingFee.in=" + DEFAULT_TOTAL_SHIPPING_FEE + "," + UPDATED_TOTAL_SHIPPING_FEE);

        // Get all the ordersList where totalShippingFee equals to UPDATED_TOTAL_SHIPPING_FEE
        defaultOrdersShouldNotBeFound("totalShippingFee.in=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFee is not null
        defaultOrdersShouldBeFound("totalShippingFee.specified=true");

        // Get all the ordersList where totalShippingFee is null
        defaultOrdersShouldNotBeFound("totalShippingFee.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFee is greater than or equal to DEFAULT_TOTAL_SHIPPING_FEE
        defaultOrdersShouldBeFound("totalShippingFee.greaterThanOrEqual=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the ordersList where totalShippingFee is greater than or equal to UPDATED_TOTAL_SHIPPING_FEE
        defaultOrdersShouldNotBeFound("totalShippingFee.greaterThanOrEqual=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFee is less than or equal to DEFAULT_TOTAL_SHIPPING_FEE
        defaultOrdersShouldBeFound("totalShippingFee.lessThanOrEqual=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the ordersList where totalShippingFee is less than or equal to SMALLER_TOTAL_SHIPPING_FEE
        defaultOrdersShouldNotBeFound("totalShippingFee.lessThanOrEqual=" + SMALLER_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFee is less than DEFAULT_TOTAL_SHIPPING_FEE
        defaultOrdersShouldNotBeFound("totalShippingFee.lessThan=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the ordersList where totalShippingFee is less than UPDATED_TOTAL_SHIPPING_FEE
        defaultOrdersShouldBeFound("totalShippingFee.lessThan=" + UPDATED_TOTAL_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFee is greater than DEFAULT_TOTAL_SHIPPING_FEE
        defaultOrdersShouldNotBeFound("totalShippingFee.greaterThan=" + DEFAULT_TOTAL_SHIPPING_FEE);

        // Get all the ordersList where totalShippingFee is greater than SMALLER_TOTAL_SHIPPING_FEE
        defaultOrdersShouldBeFound("totalShippingFee.greaterThan=" + SMALLER_TOTAL_SHIPPING_FEE);
    }


    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFeeDiscount equals to DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldBeFound("totalShippingFeeDiscount.equals=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the ordersList where totalShippingFeeDiscount equals to UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldNotBeFound("totalShippingFeeDiscount.equals=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFeeDiscount not equals to DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldNotBeFound("totalShippingFeeDiscount.notEquals=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the ordersList where totalShippingFeeDiscount not equals to UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldBeFound("totalShippingFeeDiscount.notEquals=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFeeDiscount in DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT or UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldBeFound("totalShippingFeeDiscount.in=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT + "," + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the ordersList where totalShippingFeeDiscount equals to UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldNotBeFound("totalShippingFeeDiscount.in=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFeeDiscount is not null
        defaultOrdersShouldBeFound("totalShippingFeeDiscount.specified=true");

        // Get all the ordersList where totalShippingFeeDiscount is null
        defaultOrdersShouldNotBeFound("totalShippingFeeDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFeeDiscount is greater than or equal to DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldBeFound("totalShippingFeeDiscount.greaterThanOrEqual=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the ordersList where totalShippingFeeDiscount is greater than or equal to UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldNotBeFound("totalShippingFeeDiscount.greaterThanOrEqual=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFeeDiscount is less than or equal to DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldBeFound("totalShippingFeeDiscount.lessThanOrEqual=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the ordersList where totalShippingFeeDiscount is less than or equal to SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldNotBeFound("totalShippingFeeDiscount.lessThanOrEqual=" + SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFeeDiscount is less than DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldNotBeFound("totalShippingFeeDiscount.lessThan=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the ordersList where totalShippingFeeDiscount is less than UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldBeFound("totalShippingFeeDiscount.lessThan=" + UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalShippingFeeDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalShippingFeeDiscount is greater than DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldNotBeFound("totalShippingFeeDiscount.greaterThan=" + DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT);

        // Get all the ordersList where totalShippingFeeDiscount is greater than SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT
        defaultOrdersShouldBeFound("totalShippingFeeDiscount.greaterThan=" + SMALLER_TOTAL_SHIPPING_FEE_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllOrdersByTotalVoucherDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalVoucherDiscount equals to DEFAULT_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldBeFound("totalVoucherDiscount.equals=" + DEFAULT_TOTAL_VOUCHER_DISCOUNT);

        // Get all the ordersList where totalVoucherDiscount equals to UPDATED_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldNotBeFound("totalVoucherDiscount.equals=" + UPDATED_TOTAL_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalVoucherDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalVoucherDiscount not equals to DEFAULT_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldNotBeFound("totalVoucherDiscount.notEquals=" + DEFAULT_TOTAL_VOUCHER_DISCOUNT);

        // Get all the ordersList where totalVoucherDiscount not equals to UPDATED_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldBeFound("totalVoucherDiscount.notEquals=" + UPDATED_TOTAL_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalVoucherDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalVoucherDiscount in DEFAULT_TOTAL_VOUCHER_DISCOUNT or UPDATED_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldBeFound("totalVoucherDiscount.in=" + DEFAULT_TOTAL_VOUCHER_DISCOUNT + "," + UPDATED_TOTAL_VOUCHER_DISCOUNT);

        // Get all the ordersList where totalVoucherDiscount equals to UPDATED_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldNotBeFound("totalVoucherDiscount.in=" + UPDATED_TOTAL_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalVoucherDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalVoucherDiscount is not null
        defaultOrdersShouldBeFound("totalVoucherDiscount.specified=true");

        // Get all the ordersList where totalVoucherDiscount is null
        defaultOrdersShouldNotBeFound("totalVoucherDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalVoucherDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalVoucherDiscount is greater than or equal to DEFAULT_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldBeFound("totalVoucherDiscount.greaterThanOrEqual=" + DEFAULT_TOTAL_VOUCHER_DISCOUNT);

        // Get all the ordersList where totalVoucherDiscount is greater than or equal to UPDATED_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldNotBeFound("totalVoucherDiscount.greaterThanOrEqual=" + UPDATED_TOTAL_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalVoucherDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalVoucherDiscount is less than or equal to DEFAULT_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldBeFound("totalVoucherDiscount.lessThanOrEqual=" + DEFAULT_TOTAL_VOUCHER_DISCOUNT);

        // Get all the ordersList where totalVoucherDiscount is less than or equal to SMALLER_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldNotBeFound("totalVoucherDiscount.lessThanOrEqual=" + SMALLER_TOTAL_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalVoucherDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalVoucherDiscount is less than DEFAULT_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldNotBeFound("totalVoucherDiscount.lessThan=" + DEFAULT_TOTAL_VOUCHER_DISCOUNT);

        // Get all the ordersList where totalVoucherDiscount is less than UPDATED_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldBeFound("totalVoucherDiscount.lessThan=" + UPDATED_TOTAL_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalVoucherDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalVoucherDiscount is greater than DEFAULT_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldNotBeFound("totalVoucherDiscount.greaterThan=" + DEFAULT_TOTAL_VOUCHER_DISCOUNT);

        // Get all the ordersList where totalVoucherDiscount is greater than SMALLER_TOTAL_VOUCHER_DISCOUNT
        defaultOrdersShouldBeFound("totalVoucherDiscount.greaterThan=" + SMALLER_TOTAL_VOUCHER_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllOrdersByTotalPromtionDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalPromtionDiscount equals to DEFAULT_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldBeFound("totalPromtionDiscount.equals=" + DEFAULT_TOTAL_PROMTION_DISCOUNT);

        // Get all the ordersList where totalPromtionDiscount equals to UPDATED_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldNotBeFound("totalPromtionDiscount.equals=" + UPDATED_TOTAL_PROMTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalPromtionDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalPromtionDiscount not equals to DEFAULT_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldNotBeFound("totalPromtionDiscount.notEquals=" + DEFAULT_TOTAL_PROMTION_DISCOUNT);

        // Get all the ordersList where totalPromtionDiscount not equals to UPDATED_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldBeFound("totalPromtionDiscount.notEquals=" + UPDATED_TOTAL_PROMTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalPromtionDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalPromtionDiscount in DEFAULT_TOTAL_PROMTION_DISCOUNT or UPDATED_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldBeFound("totalPromtionDiscount.in=" + DEFAULT_TOTAL_PROMTION_DISCOUNT + "," + UPDATED_TOTAL_PROMTION_DISCOUNT);

        // Get all the ordersList where totalPromtionDiscount equals to UPDATED_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldNotBeFound("totalPromtionDiscount.in=" + UPDATED_TOTAL_PROMTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalPromtionDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalPromtionDiscount is not null
        defaultOrdersShouldBeFound("totalPromtionDiscount.specified=true");

        // Get all the ordersList where totalPromtionDiscount is null
        defaultOrdersShouldNotBeFound("totalPromtionDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalPromtionDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalPromtionDiscount is greater than or equal to DEFAULT_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldBeFound("totalPromtionDiscount.greaterThanOrEqual=" + DEFAULT_TOTAL_PROMTION_DISCOUNT);

        // Get all the ordersList where totalPromtionDiscount is greater than or equal to UPDATED_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldNotBeFound("totalPromtionDiscount.greaterThanOrEqual=" + UPDATED_TOTAL_PROMTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalPromtionDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalPromtionDiscount is less than or equal to DEFAULT_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldBeFound("totalPromtionDiscount.lessThanOrEqual=" + DEFAULT_TOTAL_PROMTION_DISCOUNT);

        // Get all the ordersList where totalPromtionDiscount is less than or equal to SMALLER_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldNotBeFound("totalPromtionDiscount.lessThanOrEqual=" + SMALLER_TOTAL_PROMTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalPromtionDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalPromtionDiscount is less than DEFAULT_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldNotBeFound("totalPromtionDiscount.lessThan=" + DEFAULT_TOTAL_PROMTION_DISCOUNT);

        // Get all the ordersList where totalPromtionDiscount is less than UPDATED_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldBeFound("totalPromtionDiscount.lessThan=" + UPDATED_TOTAL_PROMTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalPromtionDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalPromtionDiscount is greater than DEFAULT_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldNotBeFound("totalPromtionDiscount.greaterThan=" + DEFAULT_TOTAL_PROMTION_DISCOUNT);

        // Get all the ordersList where totalPromtionDiscount is greater than SMALLER_TOTAL_PROMTION_DISCOUNT
        defaultOrdersShouldBeFound("totalPromtionDiscount.greaterThan=" + SMALLER_TOTAL_PROMTION_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue equals to DEFAULT_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.equals=" + DEFAULT_TOTAL_DUE);

        // Get all the ordersList where totalDue equals to UPDATED_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.equals=" + UPDATED_TOTAL_DUE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue not equals to DEFAULT_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.notEquals=" + DEFAULT_TOTAL_DUE);

        // Get all the ordersList where totalDue not equals to UPDATED_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.notEquals=" + UPDATED_TOTAL_DUE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue in DEFAULT_TOTAL_DUE or UPDATED_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.in=" + DEFAULT_TOTAL_DUE + "," + UPDATED_TOTAL_DUE);

        // Get all the ordersList where totalDue equals to UPDATED_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.in=" + UPDATED_TOTAL_DUE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue is not null
        defaultOrdersShouldBeFound("totalDue.specified=true");

        // Get all the ordersList where totalDue is null
        defaultOrdersShouldNotBeFound("totalDue.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue is greater than or equal to DEFAULT_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.greaterThanOrEqual=" + DEFAULT_TOTAL_DUE);

        // Get all the ordersList where totalDue is greater than or equal to UPDATED_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.greaterThanOrEqual=" + UPDATED_TOTAL_DUE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue is less than or equal to DEFAULT_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.lessThanOrEqual=" + DEFAULT_TOTAL_DUE);

        // Get all the ordersList where totalDue is less than or equal to SMALLER_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.lessThanOrEqual=" + SMALLER_TOTAL_DUE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsLessThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue is less than DEFAULT_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.lessThan=" + DEFAULT_TOTAL_DUE);

        // Get all the ordersList where totalDue is less than UPDATED_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.lessThan=" + UPDATED_TOTAL_DUE);
    }

    @Test
    @Transactional
    public void getAllOrdersByTotalDueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where totalDue is greater than DEFAULT_TOTAL_DUE
        defaultOrdersShouldNotBeFound("totalDue.greaterThan=" + DEFAULT_TOTAL_DUE);

        // Get all the ordersList where totalDue is greater than SMALLER_TOTAL_DUE
        defaultOrdersShouldBeFound("totalDue.greaterThan=" + SMALLER_TOTAL_DUE);
    }


    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultOrdersShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the ordersList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultOrdersShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus not equals to DEFAULT_PAYMENT_STATUS
        defaultOrdersShouldNotBeFound("paymentStatus.notEquals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the ordersList where paymentStatus not equals to UPDATED_PAYMENT_STATUS
        defaultOrdersShouldBeFound("paymentStatus.notEquals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultOrdersShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the ordersList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultOrdersShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where paymentStatus is not null
        defaultOrdersShouldBeFound("paymentStatus.specified=true");

        // Get all the ordersList where paymentStatus is null
        defaultOrdersShouldNotBeFound("paymentStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByCustomerPurchaseOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where customerPurchaseOrderNumber equals to DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldBeFound("customerPurchaseOrderNumber.equals=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the ordersList where customerPurchaseOrderNumber equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldNotBeFound("customerPurchaseOrderNumber.equals=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrdersByCustomerPurchaseOrderNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where customerPurchaseOrderNumber not equals to DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldNotBeFound("customerPurchaseOrderNumber.notEquals=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the ordersList where customerPurchaseOrderNumber not equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldBeFound("customerPurchaseOrderNumber.notEquals=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrdersByCustomerPurchaseOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where customerPurchaseOrderNumber in DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER or UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldBeFound("customerPurchaseOrderNumber.in=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER + "," + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the ordersList where customerPurchaseOrderNumber equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldNotBeFound("customerPurchaseOrderNumber.in=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrdersByCustomerPurchaseOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where customerPurchaseOrderNumber is not null
        defaultOrdersShouldBeFound("customerPurchaseOrderNumber.specified=true");

        // Get all the ordersList where customerPurchaseOrderNumber is null
        defaultOrdersShouldNotBeFound("customerPurchaseOrderNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByCustomerPurchaseOrderNumberContainsSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where customerPurchaseOrderNumber contains DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldBeFound("customerPurchaseOrderNumber.contains=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the ordersList where customerPurchaseOrderNumber contains UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldNotBeFound("customerPurchaseOrderNumber.contains=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrdersByCustomerPurchaseOrderNumberNotContainsSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where customerPurchaseOrderNumber does not contain DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldNotBeFound("customerPurchaseOrderNumber.doesNotContain=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the ordersList where customerPurchaseOrderNumber does not contain UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrdersShouldBeFound("customerPurchaseOrderNumber.doesNotContain=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }


    @Test
    @Transactional
    public void getAllOrdersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where status equals to DEFAULT_STATUS
        defaultOrdersShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the ordersList where status equals to UPDATED_STATUS
        defaultOrdersShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where status not equals to DEFAULT_STATUS
        defaultOrdersShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the ordersList where status not equals to UPDATED_STATUS
        defaultOrdersShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrdersShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the ordersList where status equals to UPDATED_STATUS
        defaultOrdersShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllOrdersByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where status is not null
        defaultOrdersShouldBeFound("status.specified=true");

        // Get all the ordersList where status is null
        defaultOrdersShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByIsUnderSupplyBackOrderedIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where isUnderSupplyBackOrdered equals to DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED
        defaultOrdersShouldBeFound("isUnderSupplyBackOrdered.equals=" + DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED);

        // Get all the ordersList where isUnderSupplyBackOrdered equals to UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED
        defaultOrdersShouldNotBeFound("isUnderSupplyBackOrdered.equals=" + UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED);
    }

    @Test
    @Transactional
    public void getAllOrdersByIsUnderSupplyBackOrderedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where isUnderSupplyBackOrdered not equals to DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED
        defaultOrdersShouldNotBeFound("isUnderSupplyBackOrdered.notEquals=" + DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED);

        // Get all the ordersList where isUnderSupplyBackOrdered not equals to UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED
        defaultOrdersShouldBeFound("isUnderSupplyBackOrdered.notEquals=" + UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED);
    }

    @Test
    @Transactional
    public void getAllOrdersByIsUnderSupplyBackOrderedIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where isUnderSupplyBackOrdered in DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED or UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED
        defaultOrdersShouldBeFound("isUnderSupplyBackOrdered.in=" + DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED + "," + UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED);

        // Get all the ordersList where isUnderSupplyBackOrdered equals to UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED
        defaultOrdersShouldNotBeFound("isUnderSupplyBackOrdered.in=" + UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED);
    }

    @Test
    @Transactional
    public void getAllOrdersByIsUnderSupplyBackOrderedIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where isUnderSupplyBackOrdered is not null
        defaultOrdersShouldBeFound("isUnderSupplyBackOrdered.specified=true");

        // Get all the ordersList where isUnderSupplyBackOrdered is null
        defaultOrdersShouldNotBeFound("isUnderSupplyBackOrdered.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultOrdersShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the ordersList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultOrdersShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrdersByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultOrdersShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the ordersList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultOrdersShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrdersByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultOrdersShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the ordersList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultOrdersShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrdersByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedBy is not null
        defaultOrdersShouldBeFound("lastEditedBy.specified=true");

        // Get all the ordersList where lastEditedBy is null
        defaultOrdersShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultOrdersShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the ordersList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultOrdersShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrdersByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultOrdersShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the ordersList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultOrdersShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllOrdersByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultOrdersShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the ordersList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultOrdersShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrdersByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultOrdersShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the ordersList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultOrdersShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrdersByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultOrdersShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the ordersList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultOrdersShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrdersByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        // Get all the ordersList where lastEditedWhen is not null
        defaultOrdersShouldBeFound("lastEditedWhen.specified=true");

        // Get all the ordersList where lastEditedWhen is null
        defaultOrdersShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderPackageListIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        OrderPackages orderPackageList = OrderPackagesResourceIT.createEntity(em);
        em.persist(orderPackageList);
        em.flush();
        orders.addOrderPackageList(orderPackageList);
        ordersRepository.saveAndFlush(orders);
        Long orderPackageListId = orderPackageList.getId();

        // Get all the ordersList where orderPackageList equals to orderPackageListId
        defaultOrdersShouldBeFound("orderPackageListId.equals=" + orderPackageListId);

        // Get all the ordersList where orderPackageList equals to orderPackageListId + 1
        defaultOrdersShouldNotBeFound("orderPackageListId.equals=" + (orderPackageListId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByCustomerIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        Customers customer = CustomersResourceIT.createEntity(em);
        em.persist(customer);
        em.flush();
        orders.setCustomer(customer);
        ordersRepository.saveAndFlush(orders);
        Long customerId = customer.getId();

        // Get all the ordersList where customer equals to customerId
        defaultOrdersShouldBeFound("customerId.equals=" + customerId);

        // Get all the ordersList where customer equals to customerId + 1
        defaultOrdersShouldNotBeFound("customerId.equals=" + (customerId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByShipToAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        Addresses shipToAddress = AddressesResourceIT.createEntity(em);
        em.persist(shipToAddress);
        em.flush();
        orders.setShipToAddress(shipToAddress);
        ordersRepository.saveAndFlush(orders);
        Long shipToAddressId = shipToAddress.getId();

        // Get all the ordersList where shipToAddress equals to shipToAddressId
        defaultOrdersShouldBeFound("shipToAddressId.equals=" + shipToAddressId);

        // Get all the ordersList where shipToAddress equals to shipToAddressId + 1
        defaultOrdersShouldNotBeFound("shipToAddressId.equals=" + (shipToAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByBillToAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        Addresses billToAddress = AddressesResourceIT.createEntity(em);
        em.persist(billToAddress);
        em.flush();
        orders.setBillToAddress(billToAddress);
        ordersRepository.saveAndFlush(orders);
        Long billToAddressId = billToAddress.getId();

        // Get all the ordersList where billToAddress equals to billToAddressId
        defaultOrdersShouldBeFound("billToAddressId.equals=" + billToAddressId);

        // Get all the ordersList where billToAddress equals to billToAddressId + 1
        defaultOrdersShouldNotBeFound("billToAddressId.equals=" + (billToAddressId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByCurrencyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        CurrencyRate currencyRate = CurrencyRateResourceIT.createEntity(em);
        em.persist(currencyRate);
        em.flush();
        orders.setCurrencyRate(currencyRate);
        ordersRepository.saveAndFlush(orders);
        Long currencyRateId = currencyRate.getId();

        // Get all the ordersList where currencyRate equals to currencyRateId
        defaultOrdersShouldBeFound("currencyRateId.equals=" + currencyRateId);

        // Get all the ordersList where currencyRate equals to currencyRateId + 1
        defaultOrdersShouldNotBeFound("currencyRateId.equals=" + (currencyRateId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        PaymentMethods paymentMethod = PaymentMethodsResourceIT.createEntity(em);
        em.persist(paymentMethod);
        em.flush();
        orders.setPaymentMethod(paymentMethod);
        ordersRepository.saveAndFlush(orders);
        Long paymentMethodId = paymentMethod.getId();

        // Get all the ordersList where paymentMethod equals to paymentMethodId
        defaultOrdersShouldBeFound("paymentMethodId.equals=" + paymentMethodId);

        // Get all the ordersList where paymentMethod equals to paymentMethodId + 1
        defaultOrdersShouldNotBeFound("paymentMethodId.equals=" + (paymentMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersBySalePersonIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        People salePerson = PeopleResourceIT.createEntity(em);
        em.persist(salePerson);
        em.flush();
        orders.setSalePerson(salePerson);
        ordersRepository.saveAndFlush(orders);
        Long salePersonId = salePerson.getId();

        // Get all the ordersList where salePerson equals to salePersonId
        defaultOrdersShouldBeFound("salePersonId.equals=" + salePersonId);

        // Get all the ordersList where salePerson equals to salePersonId + 1
        defaultOrdersShouldNotBeFound("salePersonId.equals=" + (salePersonId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByOrderTrackingIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        OrderTracking orderTracking = OrderTrackingResourceIT.createEntity(em);
        em.persist(orderTracking);
        em.flush();
        orders.setOrderTracking(orderTracking);
        orderTracking.setOrder(orders);
        ordersRepository.saveAndFlush(orders);
        Long orderTrackingId = orderTracking.getId();

        // Get all the ordersList where orderTracking equals to orderTrackingId
        defaultOrdersShouldBeFound("orderTrackingId.equals=" + orderTrackingId);

        // Get all the ordersList where orderTracking equals to orderTrackingId + 1
        defaultOrdersShouldNotBeFound("orderTrackingId.equals=" + (orderTrackingId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersBySpecialDealsIsEqualToSomething() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);
        SpecialDeals specialDeals = SpecialDealsResourceIT.createEntity(em);
        em.persist(specialDeals);
        em.flush();
        orders.setSpecialDeals(specialDeals);
        ordersRepository.saveAndFlush(orders);
        Long specialDealsId = specialDeals.getId();

        // Get all the ordersList where specialDeals equals to specialDealsId
        defaultOrdersShouldBeFound("specialDealsId.equals=" + specialDealsId);

        // Get all the ordersList where specialDeals equals to specialDealsId + 1
        defaultOrdersShouldNotBeFound("specialDealsId.equals=" + (specialDealsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrdersShouldBeFound(String filter) throws Exception {
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orders.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].subTotal").value(hasItem(DEFAULT_SUB_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].totalTaxAmount").value(hasItem(DEFAULT_TOTAL_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalShippingFee").value(hasItem(DEFAULT_TOTAL_SHIPPING_FEE.intValue())))
            .andExpect(jsonPath("$.[*].totalShippingFeeDiscount").value(hasItem(DEFAULT_TOTAL_SHIPPING_FEE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalVoucherDiscount").value(hasItem(DEFAULT_TOTAL_VOUCHER_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalPromtionDiscount").value(hasItem(DEFAULT_TOTAL_PROMTION_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].totalDue").value(hasItem(DEFAULT_TOTAL_DUE.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].customerPurchaseOrderNumber").value(hasItem(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].orderDetails").value(hasItem(DEFAULT_ORDER_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].isUnderSupplyBackOrdered").value(hasItem(DEFAULT_IS_UNDER_SUPPLY_BACK_ORDERED.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restOrdersMockMvc.perform(get("/api/orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrdersShouldNotBeFound(String filter) throws Exception {
        restOrdersMockMvc.perform(get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrdersMockMvc.perform(get("/api/orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOrders() throws Exception {
        // Get the orders
        restOrdersMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Update the orders
        Orders updatedOrders = ordersRepository.findById(orders.getId()).get();
        // Disconnect from session so that the updates on updatedOrders are not directly saved in db
        em.detach(updatedOrders);
        updatedOrders
            .orderDate(UPDATED_ORDER_DATE)
            .subTotal(UPDATED_SUB_TOTAL)
            .totalTaxAmount(UPDATED_TOTAL_TAX_AMOUNT)
            .totalShippingFee(UPDATED_TOTAL_SHIPPING_FEE)
            .totalShippingFeeDiscount(UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT)
            .totalVoucherDiscount(UPDATED_TOTAL_VOUCHER_DISCOUNT)
            .totalPromtionDiscount(UPDATED_TOTAL_PROMTION_DISCOUNT)
            .totalDue(UPDATED_TOTAL_DUE)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .customerPurchaseOrderNumber(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER)
            .status(UPDATED_STATUS)
            .orderDetails(UPDATED_ORDER_DETAILS)
            .isUnderSupplyBackOrdered(UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        OrdersDTO ordersDTO = ordersMapper.toDto(updatedOrders);

        restOrdersMockMvc.perform(put("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isOk());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
        Orders testOrders = ordersList.get(ordersList.size() - 1);
        assertThat(testOrders.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrders.getSubTotal()).isEqualTo(UPDATED_SUB_TOTAL);
        assertThat(testOrders.getTotalTaxAmount()).isEqualTo(UPDATED_TOTAL_TAX_AMOUNT);
        assertThat(testOrders.getTotalShippingFee()).isEqualTo(UPDATED_TOTAL_SHIPPING_FEE);
        assertThat(testOrders.getTotalShippingFeeDiscount()).isEqualTo(UPDATED_TOTAL_SHIPPING_FEE_DISCOUNT);
        assertThat(testOrders.getTotalVoucherDiscount()).isEqualTo(UPDATED_TOTAL_VOUCHER_DISCOUNT);
        assertThat(testOrders.getTotalPromtionDiscount()).isEqualTo(UPDATED_TOTAL_PROMTION_DISCOUNT);
        assertThat(testOrders.getTotalDue()).isEqualTo(UPDATED_TOTAL_DUE);
        assertThat(testOrders.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testOrders.getCustomerPurchaseOrderNumber()).isEqualTo(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
        assertThat(testOrders.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrders.getOrderDetails()).isEqualTo(UPDATED_ORDER_DETAILS);
        assertThat(testOrders.isIsUnderSupplyBackOrdered()).isEqualTo(UPDATED_IS_UNDER_SUPPLY_BACK_ORDERED);
        assertThat(testOrders.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testOrders.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingOrders() throws Exception {
        int databaseSizeBeforeUpdate = ordersRepository.findAll().size();

        // Create the Orders
        OrdersDTO ordersDTO = ordersMapper.toDto(orders);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrdersMockMvc.perform(put("/api/orders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ordersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Orders in the database
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrders() throws Exception {
        // Initialize the database
        ordersRepository.saveAndFlush(orders);

        int databaseSizeBeforeDelete = ordersRepository.findAll().size();

        // Delete the orders
        restOrdersMockMvc.perform(delete("/api/orders/{id}", orders.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Orders> ordersList = ordersRepository.findAll();
        assertThat(ordersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
