package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.OrderPackages;
import com.vertical.commerce.domain.OrderLines;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.DeliveryMethods;
import com.vertical.commerce.domain.SpecialDeals;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.repository.OrderPackagesRepository;
import com.vertical.commerce.service.OrderPackagesService;
import com.vertical.commerce.service.dto.OrderPackagesDTO;
import com.vertical.commerce.service.mapper.OrderPackagesMapper;
import com.vertical.commerce.service.dto.OrderPackagesCriteria;
import com.vertical.commerce.service.OrderPackagesQueryService;

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

/**
 * Integration tests for the {@link OrderPackagesResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class OrderPackagesResourceIT {

    private static final Instant DEFAULT_EXPECTED_DELIVERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_DELIVERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ORDER_PLACED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_PLACED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ORDER_DELIVERED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_DELIVERED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PACKAGE_SHIPPING_FEE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACKAGE_SHIPPING_FEE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACKAGE_SHIPPING_FEE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACKAGE_SHIPPING_FEE_DISCOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PACKAGE_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACKAGE_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACKAGE_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PACKAGE_SUB_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACKAGE_SUB_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACKAGE_SUB_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PACKAGE_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACKAGE_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACKAGE_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PACKAGE_VOUCHER_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACKAGE_VOUCHER_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACKAGE_VOUCHER_DISCOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PACKAGE_PROMOTION_DISCOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PACKAGE_PROMOTION_DISCOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PACKAGE_PROMOTION_DISCOUNT = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_PICKING_COMPLETED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PICKING_COMPLETED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CUSTOMER_REVIEWED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CUSTOMER_REVIEWED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SELLER_RATING = 1;
    private static final Integer UPDATED_SELLER_RATING = 2;
    private static final Integer SMALLER_SELLER_RATING = 1 - 1;

    private static final String DEFAULT_SELLER_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_SELLER_REVIEW = "BBBBBBBBBB";

    private static final Integer DEFAULT_DELIVERY_RATING = 1;
    private static final Integer UPDATED_DELIVERY_RATING = 2;
    private static final Integer SMALLER_DELIVERY_RATING = 1 - 1;

    private static final String DEFAULT_DELIVERY_REVIEW = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_REVIEW = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REVIEW_AS_ANONYMOUS = false;
    private static final Boolean UPDATED_REVIEW_AS_ANONYMOUS = true;

    private static final Boolean DEFAULT_COMPLETED_REVIEW = false;
    private static final Boolean UPDATED_COMPLETED_REVIEW = true;

    private static final String DEFAULT_ORDER_PACKAGE_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_PACKAGE_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OrderPackagesRepository orderPackagesRepository;

    @Autowired
    private OrderPackagesMapper orderPackagesMapper;

    @Autowired
    private OrderPackagesService orderPackagesService;

    @Autowired
    private OrderPackagesQueryService orderPackagesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderPackagesMockMvc;

    private OrderPackages orderPackages;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderPackages createEntity(EntityManager em) {
        OrderPackages orderPackages = new OrderPackages()
            .expectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE)
            .orderPlacedOn(DEFAULT_ORDER_PLACED_ON)
            .orderDeliveredOn(DEFAULT_ORDER_DELIVERED_ON)
            .comments(DEFAULT_COMMENTS)
            .deliveryInstructions(DEFAULT_DELIVERY_INSTRUCTIONS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .packageShippingFee(DEFAULT_PACKAGE_SHIPPING_FEE)
            .packageShippingFeeDiscount(DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT)
            .packagePrice(DEFAULT_PACKAGE_PRICE)
            .packageSubTotal(DEFAULT_PACKAGE_SUB_TOTAL)
            .packageTaxAmount(DEFAULT_PACKAGE_TAX_AMOUNT)
            .packageVoucherDiscount(DEFAULT_PACKAGE_VOUCHER_DISCOUNT)
            .packagePromotionDiscount(DEFAULT_PACKAGE_PROMOTION_DISCOUNT)
            .pickingCompletedWhen(DEFAULT_PICKING_COMPLETED_WHEN)
            .customerReviewedOn(DEFAULT_CUSTOMER_REVIEWED_ON)
            .sellerRating(DEFAULT_SELLER_RATING)
            .sellerReview(DEFAULT_SELLER_REVIEW)
            .deliveryRating(DEFAULT_DELIVERY_RATING)
            .deliveryReview(DEFAULT_DELIVERY_REVIEW)
            .reviewAsAnonymous(DEFAULT_REVIEW_AS_ANONYMOUS)
            .completedReview(DEFAULT_COMPLETED_REVIEW)
            .orderPackageDetails(DEFAULT_ORDER_PACKAGE_DETAILS)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return orderPackages;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderPackages createUpdatedEntity(EntityManager em) {
        OrderPackages orderPackages = new OrderPackages()
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .orderPlacedOn(UPDATED_ORDER_PLACED_ON)
            .orderDeliveredOn(UPDATED_ORDER_DELIVERED_ON)
            .comments(UPDATED_COMMENTS)
            .deliveryInstructions(UPDATED_DELIVERY_INSTRUCTIONS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .packageShippingFee(UPDATED_PACKAGE_SHIPPING_FEE)
            .packageShippingFeeDiscount(UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT)
            .packagePrice(UPDATED_PACKAGE_PRICE)
            .packageSubTotal(UPDATED_PACKAGE_SUB_TOTAL)
            .packageTaxAmount(UPDATED_PACKAGE_TAX_AMOUNT)
            .packageVoucherDiscount(UPDATED_PACKAGE_VOUCHER_DISCOUNT)
            .packagePromotionDiscount(UPDATED_PACKAGE_PROMOTION_DISCOUNT)
            .pickingCompletedWhen(UPDATED_PICKING_COMPLETED_WHEN)
            .customerReviewedOn(UPDATED_CUSTOMER_REVIEWED_ON)
            .sellerRating(UPDATED_SELLER_RATING)
            .sellerReview(UPDATED_SELLER_REVIEW)
            .deliveryRating(UPDATED_DELIVERY_RATING)
            .deliveryReview(UPDATED_DELIVERY_REVIEW)
            .reviewAsAnonymous(UPDATED_REVIEW_AS_ANONYMOUS)
            .completedReview(UPDATED_COMPLETED_REVIEW)
            .orderPackageDetails(UPDATED_ORDER_PACKAGE_DETAILS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return orderPackages;
    }

    @BeforeEach
    public void initTest() {
        orderPackages = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderPackages() throws Exception {
        int databaseSizeBeforeCreate = orderPackagesRepository.findAll().size();
        // Create the OrderPackages
        OrderPackagesDTO orderPackagesDTO = orderPackagesMapper.toDto(orderPackages);
        restOrderPackagesMockMvc.perform(post("/api/order-packages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPackagesDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderPackages in the database
        List<OrderPackages> orderPackagesList = orderPackagesRepository.findAll();
        assertThat(orderPackagesList).hasSize(databaseSizeBeforeCreate + 1);
        OrderPackages testOrderPackages = orderPackagesList.get(orderPackagesList.size() - 1);
        assertThat(testOrderPackages.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testOrderPackages.getOrderPlacedOn()).isEqualTo(DEFAULT_ORDER_PLACED_ON);
        assertThat(testOrderPackages.getOrderDeliveredOn()).isEqualTo(DEFAULT_ORDER_DELIVERED_ON);
        assertThat(testOrderPackages.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testOrderPackages.getDeliveryInstructions()).isEqualTo(DEFAULT_DELIVERY_INSTRUCTIONS);
        assertThat(testOrderPackages.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testOrderPackages.getPackageShippingFee()).isEqualTo(DEFAULT_PACKAGE_SHIPPING_FEE);
        assertThat(testOrderPackages.getPackageShippingFeeDiscount()).isEqualTo(DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT);
        assertThat(testOrderPackages.getPackagePrice()).isEqualTo(DEFAULT_PACKAGE_PRICE);
        assertThat(testOrderPackages.getPackageSubTotal()).isEqualTo(DEFAULT_PACKAGE_SUB_TOTAL);
        assertThat(testOrderPackages.getPackageTaxAmount()).isEqualTo(DEFAULT_PACKAGE_TAX_AMOUNT);
        assertThat(testOrderPackages.getPackageVoucherDiscount()).isEqualTo(DEFAULT_PACKAGE_VOUCHER_DISCOUNT);
        assertThat(testOrderPackages.getPackagePromotionDiscount()).isEqualTo(DEFAULT_PACKAGE_PROMOTION_DISCOUNT);
        assertThat(testOrderPackages.getPickingCompletedWhen()).isEqualTo(DEFAULT_PICKING_COMPLETED_WHEN);
        assertThat(testOrderPackages.getCustomerReviewedOn()).isEqualTo(DEFAULT_CUSTOMER_REVIEWED_ON);
        assertThat(testOrderPackages.getSellerRating()).isEqualTo(DEFAULT_SELLER_RATING);
        assertThat(testOrderPackages.getSellerReview()).isEqualTo(DEFAULT_SELLER_REVIEW);
        assertThat(testOrderPackages.getDeliveryRating()).isEqualTo(DEFAULT_DELIVERY_RATING);
        assertThat(testOrderPackages.getDeliveryReview()).isEqualTo(DEFAULT_DELIVERY_REVIEW);
        assertThat(testOrderPackages.isReviewAsAnonymous()).isEqualTo(DEFAULT_REVIEW_AS_ANONYMOUS);
        assertThat(testOrderPackages.isCompletedReview()).isEqualTo(DEFAULT_COMPLETED_REVIEW);
        assertThat(testOrderPackages.getOrderPackageDetails()).isEqualTo(DEFAULT_ORDER_PACKAGE_DETAILS);
        assertThat(testOrderPackages.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testOrderPackages.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createOrderPackagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderPackagesRepository.findAll().size();

        // Create the OrderPackages with an existing ID
        orderPackages.setId(1L);
        OrderPackagesDTO orderPackagesDTO = orderPackagesMapper.toDto(orderPackages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderPackagesMockMvc.perform(post("/api/order-packages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPackagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPackages in the database
        List<OrderPackages> orderPackagesList = orderPackagesRepository.findAll();
        assertThat(orderPackagesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderPackagesRepository.findAll().size();
        // set the field null
        orderPackages.setLastEditedBy(null);

        // Create the OrderPackages, which fails.
        OrderPackagesDTO orderPackagesDTO = orderPackagesMapper.toDto(orderPackages);


        restOrderPackagesMockMvc.perform(post("/api/order-packages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPackagesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderPackages> orderPackagesList = orderPackagesRepository.findAll();
        assertThat(orderPackagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderPackagesRepository.findAll().size();
        // set the field null
        orderPackages.setLastEditedWhen(null);

        // Create the OrderPackages, which fails.
        OrderPackagesDTO orderPackagesDTO = orderPackagesMapper.toDto(orderPackages);


        restOrderPackagesMockMvc.perform(post("/api/order-packages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPackagesDTO)))
            .andExpect(status().isBadRequest());

        List<OrderPackages> orderPackagesList = orderPackagesRepository.findAll();
        assertThat(orderPackagesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderPackages() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList
        restOrderPackagesMockMvc.perform(get("/api/order-packages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderPackages.getId().intValue())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderPlacedOn").value(hasItem(DEFAULT_ORDER_PLACED_ON.toString())))
            .andExpect(jsonPath("$.[*].orderDeliveredOn").value(hasItem(DEFAULT_ORDER_DELIVERED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].packageShippingFee").value(hasItem(DEFAULT_PACKAGE_SHIPPING_FEE.intValue())))
            .andExpect(jsonPath("$.[*].packageShippingFeeDiscount").value(hasItem(DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].packagePrice").value(hasItem(DEFAULT_PACKAGE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].packageSubTotal").value(hasItem(DEFAULT_PACKAGE_SUB_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].packageTaxAmount").value(hasItem(DEFAULT_PACKAGE_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].packageVoucherDiscount").value(hasItem(DEFAULT_PACKAGE_VOUCHER_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].packagePromotionDiscount").value(hasItem(DEFAULT_PACKAGE_PROMOTION_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].pickingCompletedWhen").value(hasItem(DEFAULT_PICKING_COMPLETED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].customerReviewedOn").value(hasItem(DEFAULT_CUSTOMER_REVIEWED_ON.toString())))
            .andExpect(jsonPath("$.[*].sellerRating").value(hasItem(DEFAULT_SELLER_RATING)))
            .andExpect(jsonPath("$.[*].sellerReview").value(hasItem(DEFAULT_SELLER_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].deliveryRating").value(hasItem(DEFAULT_DELIVERY_RATING)))
            .andExpect(jsonPath("$.[*].deliveryReview").value(hasItem(DEFAULT_DELIVERY_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].reviewAsAnonymous").value(hasItem(DEFAULT_REVIEW_AS_ANONYMOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].completedReview").value(hasItem(DEFAULT_COMPLETED_REVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].orderPackageDetails").value(hasItem(DEFAULT_ORDER_PACKAGE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderPackages() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get the orderPackages
        restOrderPackagesMockMvc.perform(get("/api/order-packages/{id}", orderPackages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderPackages.getId().intValue()))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(DEFAULT_EXPECTED_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.orderPlacedOn").value(DEFAULT_ORDER_PLACED_ON.toString()))
            .andExpect(jsonPath("$.orderDeliveredOn").value(DEFAULT_ORDER_DELIVERED_ON.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.deliveryInstructions").value(DEFAULT_DELIVERY_INSTRUCTIONS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS))
            .andExpect(jsonPath("$.packageShippingFee").value(DEFAULT_PACKAGE_SHIPPING_FEE.intValue()))
            .andExpect(jsonPath("$.packageShippingFeeDiscount").value(DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.packagePrice").value(DEFAULT_PACKAGE_PRICE.intValue()))
            .andExpect(jsonPath("$.packageSubTotal").value(DEFAULT_PACKAGE_SUB_TOTAL.intValue()))
            .andExpect(jsonPath("$.packageTaxAmount").value(DEFAULT_PACKAGE_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.packageVoucherDiscount").value(DEFAULT_PACKAGE_VOUCHER_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.packagePromotionDiscount").value(DEFAULT_PACKAGE_PROMOTION_DISCOUNT.intValue()))
            .andExpect(jsonPath("$.pickingCompletedWhen").value(DEFAULT_PICKING_COMPLETED_WHEN.toString()))
            .andExpect(jsonPath("$.customerReviewedOn").value(DEFAULT_CUSTOMER_REVIEWED_ON.toString()))
            .andExpect(jsonPath("$.sellerRating").value(DEFAULT_SELLER_RATING))
            .andExpect(jsonPath("$.sellerReview").value(DEFAULT_SELLER_REVIEW.toString()))
            .andExpect(jsonPath("$.deliveryRating").value(DEFAULT_DELIVERY_RATING))
            .andExpect(jsonPath("$.deliveryReview").value(DEFAULT_DELIVERY_REVIEW.toString()))
            .andExpect(jsonPath("$.reviewAsAnonymous").value(DEFAULT_REVIEW_AS_ANONYMOUS.booleanValue()))
            .andExpect(jsonPath("$.completedReview").value(DEFAULT_COMPLETED_REVIEW.booleanValue()))
            .andExpect(jsonPath("$.orderPackageDetails").value(DEFAULT_ORDER_PACKAGE_DETAILS.toString()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getOrderPackagesByIdFiltering() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        Long id = orderPackages.getId();

        defaultOrderPackagesShouldBeFound("id.equals=" + id);
        defaultOrderPackagesShouldNotBeFound("id.notEquals=" + id);

        defaultOrderPackagesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderPackagesShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderPackagesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderPackagesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByExpectedDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where expectedDeliveryDate equals to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultOrderPackagesShouldBeFound("expectedDeliveryDate.equals=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the orderPackagesList where expectedDeliveryDate equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderPackagesShouldNotBeFound("expectedDeliveryDate.equals=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByExpectedDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where expectedDeliveryDate not equals to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultOrderPackagesShouldNotBeFound("expectedDeliveryDate.notEquals=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the orderPackagesList where expectedDeliveryDate not equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderPackagesShouldBeFound("expectedDeliveryDate.notEquals=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByExpectedDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where expectedDeliveryDate in DEFAULT_EXPECTED_DELIVERY_DATE or UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderPackagesShouldBeFound("expectedDeliveryDate.in=" + DEFAULT_EXPECTED_DELIVERY_DATE + "," + UPDATED_EXPECTED_DELIVERY_DATE);

        // Get all the orderPackagesList where expectedDeliveryDate equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderPackagesShouldNotBeFound("expectedDeliveryDate.in=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByExpectedDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where expectedDeliveryDate is not null
        defaultOrderPackagesShouldBeFound("expectedDeliveryDate.specified=true");

        // Get all the orderPackagesList where expectedDeliveryDate is null
        defaultOrderPackagesShouldNotBeFound("expectedDeliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderPlacedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where orderPlacedOn equals to DEFAULT_ORDER_PLACED_ON
        defaultOrderPackagesShouldBeFound("orderPlacedOn.equals=" + DEFAULT_ORDER_PLACED_ON);

        // Get all the orderPackagesList where orderPlacedOn equals to UPDATED_ORDER_PLACED_ON
        defaultOrderPackagesShouldNotBeFound("orderPlacedOn.equals=" + UPDATED_ORDER_PLACED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderPlacedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where orderPlacedOn not equals to DEFAULT_ORDER_PLACED_ON
        defaultOrderPackagesShouldNotBeFound("orderPlacedOn.notEquals=" + DEFAULT_ORDER_PLACED_ON);

        // Get all the orderPackagesList where orderPlacedOn not equals to UPDATED_ORDER_PLACED_ON
        defaultOrderPackagesShouldBeFound("orderPlacedOn.notEquals=" + UPDATED_ORDER_PLACED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderPlacedOnIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where orderPlacedOn in DEFAULT_ORDER_PLACED_ON or UPDATED_ORDER_PLACED_ON
        defaultOrderPackagesShouldBeFound("orderPlacedOn.in=" + DEFAULT_ORDER_PLACED_ON + "," + UPDATED_ORDER_PLACED_ON);

        // Get all the orderPackagesList where orderPlacedOn equals to UPDATED_ORDER_PLACED_ON
        defaultOrderPackagesShouldNotBeFound("orderPlacedOn.in=" + UPDATED_ORDER_PLACED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderPlacedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where orderPlacedOn is not null
        defaultOrderPackagesShouldBeFound("orderPlacedOn.specified=true");

        // Get all the orderPackagesList where orderPlacedOn is null
        defaultOrderPackagesShouldNotBeFound("orderPlacedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderDeliveredOnIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where orderDeliveredOn equals to DEFAULT_ORDER_DELIVERED_ON
        defaultOrderPackagesShouldBeFound("orderDeliveredOn.equals=" + DEFAULT_ORDER_DELIVERED_ON);

        // Get all the orderPackagesList where orderDeliveredOn equals to UPDATED_ORDER_DELIVERED_ON
        defaultOrderPackagesShouldNotBeFound("orderDeliveredOn.equals=" + UPDATED_ORDER_DELIVERED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderDeliveredOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where orderDeliveredOn not equals to DEFAULT_ORDER_DELIVERED_ON
        defaultOrderPackagesShouldNotBeFound("orderDeliveredOn.notEquals=" + DEFAULT_ORDER_DELIVERED_ON);

        // Get all the orderPackagesList where orderDeliveredOn not equals to UPDATED_ORDER_DELIVERED_ON
        defaultOrderPackagesShouldBeFound("orderDeliveredOn.notEquals=" + UPDATED_ORDER_DELIVERED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderDeliveredOnIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where orderDeliveredOn in DEFAULT_ORDER_DELIVERED_ON or UPDATED_ORDER_DELIVERED_ON
        defaultOrderPackagesShouldBeFound("orderDeliveredOn.in=" + DEFAULT_ORDER_DELIVERED_ON + "," + UPDATED_ORDER_DELIVERED_ON);

        // Get all the orderPackagesList where orderDeliveredOn equals to UPDATED_ORDER_DELIVERED_ON
        defaultOrderPackagesShouldNotBeFound("orderDeliveredOn.in=" + UPDATED_ORDER_DELIVERED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderDeliveredOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where orderDeliveredOn is not null
        defaultOrderPackagesShouldBeFound("orderDeliveredOn.specified=true");

        // Get all the orderPackagesList where orderDeliveredOn is null
        defaultOrderPackagesShouldNotBeFound("orderDeliveredOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where comments equals to DEFAULT_COMMENTS
        defaultOrderPackagesShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the orderPackagesList where comments equals to UPDATED_COMMENTS
        defaultOrderPackagesShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where comments not equals to DEFAULT_COMMENTS
        defaultOrderPackagesShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the orderPackagesList where comments not equals to UPDATED_COMMENTS
        defaultOrderPackagesShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultOrderPackagesShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the orderPackagesList where comments equals to UPDATED_COMMENTS
        defaultOrderPackagesShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where comments is not null
        defaultOrderPackagesShouldBeFound("comments.specified=true");

        // Get all the orderPackagesList where comments is null
        defaultOrderPackagesShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderPackagesByCommentsContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where comments contains DEFAULT_COMMENTS
        defaultOrderPackagesShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the orderPackagesList where comments contains UPDATED_COMMENTS
        defaultOrderPackagesShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where comments does not contain DEFAULT_COMMENTS
        defaultOrderPackagesShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the orderPackagesList where comments does not contain UPDATED_COMMENTS
        defaultOrderPackagesShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryInstructionsIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryInstructions equals to DEFAULT_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldBeFound("deliveryInstructions.equals=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the orderPackagesList where deliveryInstructions equals to UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldNotBeFound("deliveryInstructions.equals=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryInstructionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryInstructions not equals to DEFAULT_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldNotBeFound("deliveryInstructions.notEquals=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the orderPackagesList where deliveryInstructions not equals to UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldBeFound("deliveryInstructions.notEquals=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryInstructionsIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryInstructions in DEFAULT_DELIVERY_INSTRUCTIONS or UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldBeFound("deliveryInstructions.in=" + DEFAULT_DELIVERY_INSTRUCTIONS + "," + UPDATED_DELIVERY_INSTRUCTIONS);

        // Get all the orderPackagesList where deliveryInstructions equals to UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldNotBeFound("deliveryInstructions.in=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryInstructionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryInstructions is not null
        defaultOrderPackagesShouldBeFound("deliveryInstructions.specified=true");

        // Get all the orderPackagesList where deliveryInstructions is null
        defaultOrderPackagesShouldNotBeFound("deliveryInstructions.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryInstructionsContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryInstructions contains DEFAULT_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldBeFound("deliveryInstructions.contains=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the orderPackagesList where deliveryInstructions contains UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldNotBeFound("deliveryInstructions.contains=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryInstructionsNotContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryInstructions does not contain DEFAULT_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldNotBeFound("deliveryInstructions.doesNotContain=" + DEFAULT_DELIVERY_INSTRUCTIONS);

        // Get all the orderPackagesList where deliveryInstructions does not contain UPDATED_DELIVERY_INSTRUCTIONS
        defaultOrderPackagesShouldBeFound("deliveryInstructions.doesNotContain=" + UPDATED_DELIVERY_INSTRUCTIONS);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByInternalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where internalComments equals to DEFAULT_INTERNAL_COMMENTS
        defaultOrderPackagesShouldBeFound("internalComments.equals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the orderPackagesList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultOrderPackagesShouldNotBeFound("internalComments.equals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByInternalCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where internalComments not equals to DEFAULT_INTERNAL_COMMENTS
        defaultOrderPackagesShouldNotBeFound("internalComments.notEquals=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the orderPackagesList where internalComments not equals to UPDATED_INTERNAL_COMMENTS
        defaultOrderPackagesShouldBeFound("internalComments.notEquals=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByInternalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where internalComments in DEFAULT_INTERNAL_COMMENTS or UPDATED_INTERNAL_COMMENTS
        defaultOrderPackagesShouldBeFound("internalComments.in=" + DEFAULT_INTERNAL_COMMENTS + "," + UPDATED_INTERNAL_COMMENTS);

        // Get all the orderPackagesList where internalComments equals to UPDATED_INTERNAL_COMMENTS
        defaultOrderPackagesShouldNotBeFound("internalComments.in=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByInternalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where internalComments is not null
        defaultOrderPackagesShouldBeFound("internalComments.specified=true");

        // Get all the orderPackagesList where internalComments is null
        defaultOrderPackagesShouldNotBeFound("internalComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderPackagesByInternalCommentsContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where internalComments contains DEFAULT_INTERNAL_COMMENTS
        defaultOrderPackagesShouldBeFound("internalComments.contains=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the orderPackagesList where internalComments contains UPDATED_INTERNAL_COMMENTS
        defaultOrderPackagesShouldNotBeFound("internalComments.contains=" + UPDATED_INTERNAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByInternalCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where internalComments does not contain DEFAULT_INTERNAL_COMMENTS
        defaultOrderPackagesShouldNotBeFound("internalComments.doesNotContain=" + DEFAULT_INTERNAL_COMMENTS);

        // Get all the orderPackagesList where internalComments does not contain UPDATED_INTERNAL_COMMENTS
        defaultOrderPackagesShouldBeFound("internalComments.doesNotContain=" + UPDATED_INTERNAL_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFee equals to DEFAULT_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldBeFound("packageShippingFee.equals=" + DEFAULT_PACKAGE_SHIPPING_FEE);

        // Get all the orderPackagesList where packageShippingFee equals to UPDATED_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldNotBeFound("packageShippingFee.equals=" + UPDATED_PACKAGE_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFee not equals to DEFAULT_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldNotBeFound("packageShippingFee.notEquals=" + DEFAULT_PACKAGE_SHIPPING_FEE);

        // Get all the orderPackagesList where packageShippingFee not equals to UPDATED_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldBeFound("packageShippingFee.notEquals=" + UPDATED_PACKAGE_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFee in DEFAULT_PACKAGE_SHIPPING_FEE or UPDATED_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldBeFound("packageShippingFee.in=" + DEFAULT_PACKAGE_SHIPPING_FEE + "," + UPDATED_PACKAGE_SHIPPING_FEE);

        // Get all the orderPackagesList where packageShippingFee equals to UPDATED_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldNotBeFound("packageShippingFee.in=" + UPDATED_PACKAGE_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFee is not null
        defaultOrderPackagesShouldBeFound("packageShippingFee.specified=true");

        // Get all the orderPackagesList where packageShippingFee is null
        defaultOrderPackagesShouldNotBeFound("packageShippingFee.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFee is greater than or equal to DEFAULT_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldBeFound("packageShippingFee.greaterThanOrEqual=" + DEFAULT_PACKAGE_SHIPPING_FEE);

        // Get all the orderPackagesList where packageShippingFee is greater than or equal to UPDATED_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldNotBeFound("packageShippingFee.greaterThanOrEqual=" + UPDATED_PACKAGE_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFee is less than or equal to DEFAULT_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldBeFound("packageShippingFee.lessThanOrEqual=" + DEFAULT_PACKAGE_SHIPPING_FEE);

        // Get all the orderPackagesList where packageShippingFee is less than or equal to SMALLER_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldNotBeFound("packageShippingFee.lessThanOrEqual=" + SMALLER_PACKAGE_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFee is less than DEFAULT_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldNotBeFound("packageShippingFee.lessThan=" + DEFAULT_PACKAGE_SHIPPING_FEE);

        // Get all the orderPackagesList where packageShippingFee is less than UPDATED_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldBeFound("packageShippingFee.lessThan=" + UPDATED_PACKAGE_SHIPPING_FEE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFee is greater than DEFAULT_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldNotBeFound("packageShippingFee.greaterThan=" + DEFAULT_PACKAGE_SHIPPING_FEE);

        // Get all the orderPackagesList where packageShippingFee is greater than SMALLER_PACKAGE_SHIPPING_FEE
        defaultOrderPackagesShouldBeFound("packageShippingFee.greaterThan=" + SMALLER_PACKAGE_SHIPPING_FEE);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFeeDiscount equals to DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageShippingFeeDiscount.equals=" + DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT);

        // Get all the orderPackagesList where packageShippingFeeDiscount equals to UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageShippingFeeDiscount.equals=" + UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFeeDiscount not equals to DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageShippingFeeDiscount.notEquals=" + DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT);

        // Get all the orderPackagesList where packageShippingFeeDiscount not equals to UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageShippingFeeDiscount.notEquals=" + UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFeeDiscount in DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT or UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageShippingFeeDiscount.in=" + DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT + "," + UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT);

        // Get all the orderPackagesList where packageShippingFeeDiscount equals to UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageShippingFeeDiscount.in=" + UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFeeDiscount is not null
        defaultOrderPackagesShouldBeFound("packageShippingFeeDiscount.specified=true");

        // Get all the orderPackagesList where packageShippingFeeDiscount is null
        defaultOrderPackagesShouldNotBeFound("packageShippingFeeDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFeeDiscount is greater than or equal to DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageShippingFeeDiscount.greaterThanOrEqual=" + DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT);

        // Get all the orderPackagesList where packageShippingFeeDiscount is greater than or equal to UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageShippingFeeDiscount.greaterThanOrEqual=" + UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFeeDiscount is less than or equal to DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageShippingFeeDiscount.lessThanOrEqual=" + DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT);

        // Get all the orderPackagesList where packageShippingFeeDiscount is less than or equal to SMALLER_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageShippingFeeDiscount.lessThanOrEqual=" + SMALLER_PACKAGE_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFeeDiscount is less than DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageShippingFeeDiscount.lessThan=" + DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT);

        // Get all the orderPackagesList where packageShippingFeeDiscount is less than UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageShippingFeeDiscount.lessThan=" + UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageShippingFeeDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageShippingFeeDiscount is greater than DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageShippingFeeDiscount.greaterThan=" + DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT);

        // Get all the orderPackagesList where packageShippingFeeDiscount is greater than SMALLER_PACKAGE_SHIPPING_FEE_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageShippingFeeDiscount.greaterThan=" + SMALLER_PACKAGE_SHIPPING_FEE_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePriceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePrice equals to DEFAULT_PACKAGE_PRICE
        defaultOrderPackagesShouldBeFound("packagePrice.equals=" + DEFAULT_PACKAGE_PRICE);

        // Get all the orderPackagesList where packagePrice equals to UPDATED_PACKAGE_PRICE
        defaultOrderPackagesShouldNotBeFound("packagePrice.equals=" + UPDATED_PACKAGE_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePrice not equals to DEFAULT_PACKAGE_PRICE
        defaultOrderPackagesShouldNotBeFound("packagePrice.notEquals=" + DEFAULT_PACKAGE_PRICE);

        // Get all the orderPackagesList where packagePrice not equals to UPDATED_PACKAGE_PRICE
        defaultOrderPackagesShouldBeFound("packagePrice.notEquals=" + UPDATED_PACKAGE_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePriceIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePrice in DEFAULT_PACKAGE_PRICE or UPDATED_PACKAGE_PRICE
        defaultOrderPackagesShouldBeFound("packagePrice.in=" + DEFAULT_PACKAGE_PRICE + "," + UPDATED_PACKAGE_PRICE);

        // Get all the orderPackagesList where packagePrice equals to UPDATED_PACKAGE_PRICE
        defaultOrderPackagesShouldNotBeFound("packagePrice.in=" + UPDATED_PACKAGE_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePrice is not null
        defaultOrderPackagesShouldBeFound("packagePrice.specified=true");

        // Get all the orderPackagesList where packagePrice is null
        defaultOrderPackagesShouldNotBeFound("packagePrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePrice is greater than or equal to DEFAULT_PACKAGE_PRICE
        defaultOrderPackagesShouldBeFound("packagePrice.greaterThanOrEqual=" + DEFAULT_PACKAGE_PRICE);

        // Get all the orderPackagesList where packagePrice is greater than or equal to UPDATED_PACKAGE_PRICE
        defaultOrderPackagesShouldNotBeFound("packagePrice.greaterThanOrEqual=" + UPDATED_PACKAGE_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePrice is less than or equal to DEFAULT_PACKAGE_PRICE
        defaultOrderPackagesShouldBeFound("packagePrice.lessThanOrEqual=" + DEFAULT_PACKAGE_PRICE);

        // Get all the orderPackagesList where packagePrice is less than or equal to SMALLER_PACKAGE_PRICE
        defaultOrderPackagesShouldNotBeFound("packagePrice.lessThanOrEqual=" + SMALLER_PACKAGE_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePriceIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePrice is less than DEFAULT_PACKAGE_PRICE
        defaultOrderPackagesShouldNotBeFound("packagePrice.lessThan=" + DEFAULT_PACKAGE_PRICE);

        // Get all the orderPackagesList where packagePrice is less than UPDATED_PACKAGE_PRICE
        defaultOrderPackagesShouldBeFound("packagePrice.lessThan=" + UPDATED_PACKAGE_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePrice is greater than DEFAULT_PACKAGE_PRICE
        defaultOrderPackagesShouldNotBeFound("packagePrice.greaterThan=" + DEFAULT_PACKAGE_PRICE);

        // Get all the orderPackagesList where packagePrice is greater than SMALLER_PACKAGE_PRICE
        defaultOrderPackagesShouldBeFound("packagePrice.greaterThan=" + SMALLER_PACKAGE_PRICE);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByPackageSubTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageSubTotal equals to DEFAULT_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldBeFound("packageSubTotal.equals=" + DEFAULT_PACKAGE_SUB_TOTAL);

        // Get all the orderPackagesList where packageSubTotal equals to UPDATED_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldNotBeFound("packageSubTotal.equals=" + UPDATED_PACKAGE_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageSubTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageSubTotal not equals to DEFAULT_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldNotBeFound("packageSubTotal.notEquals=" + DEFAULT_PACKAGE_SUB_TOTAL);

        // Get all the orderPackagesList where packageSubTotal not equals to UPDATED_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldBeFound("packageSubTotal.notEquals=" + UPDATED_PACKAGE_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageSubTotalIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageSubTotal in DEFAULT_PACKAGE_SUB_TOTAL or UPDATED_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldBeFound("packageSubTotal.in=" + DEFAULT_PACKAGE_SUB_TOTAL + "," + UPDATED_PACKAGE_SUB_TOTAL);

        // Get all the orderPackagesList where packageSubTotal equals to UPDATED_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldNotBeFound("packageSubTotal.in=" + UPDATED_PACKAGE_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageSubTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageSubTotal is not null
        defaultOrderPackagesShouldBeFound("packageSubTotal.specified=true");

        // Get all the orderPackagesList where packageSubTotal is null
        defaultOrderPackagesShouldNotBeFound("packageSubTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageSubTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageSubTotal is greater than or equal to DEFAULT_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldBeFound("packageSubTotal.greaterThanOrEqual=" + DEFAULT_PACKAGE_SUB_TOTAL);

        // Get all the orderPackagesList where packageSubTotal is greater than or equal to UPDATED_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldNotBeFound("packageSubTotal.greaterThanOrEqual=" + UPDATED_PACKAGE_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageSubTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageSubTotal is less than or equal to DEFAULT_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldBeFound("packageSubTotal.lessThanOrEqual=" + DEFAULT_PACKAGE_SUB_TOTAL);

        // Get all the orderPackagesList where packageSubTotal is less than or equal to SMALLER_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldNotBeFound("packageSubTotal.lessThanOrEqual=" + SMALLER_PACKAGE_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageSubTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageSubTotal is less than DEFAULT_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldNotBeFound("packageSubTotal.lessThan=" + DEFAULT_PACKAGE_SUB_TOTAL);

        // Get all the orderPackagesList where packageSubTotal is less than UPDATED_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldBeFound("packageSubTotal.lessThan=" + UPDATED_PACKAGE_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageSubTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageSubTotal is greater than DEFAULT_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldNotBeFound("packageSubTotal.greaterThan=" + DEFAULT_PACKAGE_SUB_TOTAL);

        // Get all the orderPackagesList where packageSubTotal is greater than SMALLER_PACKAGE_SUB_TOTAL
        defaultOrderPackagesShouldBeFound("packageSubTotal.greaterThan=" + SMALLER_PACKAGE_SUB_TOTAL);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByPackageTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageTaxAmount equals to DEFAULT_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldBeFound("packageTaxAmount.equals=" + DEFAULT_PACKAGE_TAX_AMOUNT);

        // Get all the orderPackagesList where packageTaxAmount equals to UPDATED_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldNotBeFound("packageTaxAmount.equals=" + UPDATED_PACKAGE_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageTaxAmount not equals to DEFAULT_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldNotBeFound("packageTaxAmount.notEquals=" + DEFAULT_PACKAGE_TAX_AMOUNT);

        // Get all the orderPackagesList where packageTaxAmount not equals to UPDATED_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldBeFound("packageTaxAmount.notEquals=" + UPDATED_PACKAGE_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageTaxAmount in DEFAULT_PACKAGE_TAX_AMOUNT or UPDATED_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldBeFound("packageTaxAmount.in=" + DEFAULT_PACKAGE_TAX_AMOUNT + "," + UPDATED_PACKAGE_TAX_AMOUNT);

        // Get all the orderPackagesList where packageTaxAmount equals to UPDATED_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldNotBeFound("packageTaxAmount.in=" + UPDATED_PACKAGE_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageTaxAmount is not null
        defaultOrderPackagesShouldBeFound("packageTaxAmount.specified=true");

        // Get all the orderPackagesList where packageTaxAmount is null
        defaultOrderPackagesShouldNotBeFound("packageTaxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageTaxAmount is greater than or equal to DEFAULT_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldBeFound("packageTaxAmount.greaterThanOrEqual=" + DEFAULT_PACKAGE_TAX_AMOUNT);

        // Get all the orderPackagesList where packageTaxAmount is greater than or equal to UPDATED_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldNotBeFound("packageTaxAmount.greaterThanOrEqual=" + UPDATED_PACKAGE_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageTaxAmount is less than or equal to DEFAULT_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldBeFound("packageTaxAmount.lessThanOrEqual=" + DEFAULT_PACKAGE_TAX_AMOUNT);

        // Get all the orderPackagesList where packageTaxAmount is less than or equal to SMALLER_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldNotBeFound("packageTaxAmount.lessThanOrEqual=" + SMALLER_PACKAGE_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageTaxAmount is less than DEFAULT_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldNotBeFound("packageTaxAmount.lessThan=" + DEFAULT_PACKAGE_TAX_AMOUNT);

        // Get all the orderPackagesList where packageTaxAmount is less than UPDATED_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldBeFound("packageTaxAmount.lessThan=" + UPDATED_PACKAGE_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageTaxAmount is greater than DEFAULT_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldNotBeFound("packageTaxAmount.greaterThan=" + DEFAULT_PACKAGE_TAX_AMOUNT);

        // Get all the orderPackagesList where packageTaxAmount is greater than SMALLER_PACKAGE_TAX_AMOUNT
        defaultOrderPackagesShouldBeFound("packageTaxAmount.greaterThan=" + SMALLER_PACKAGE_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByPackageVoucherDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageVoucherDiscount equals to DEFAULT_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageVoucherDiscount.equals=" + DEFAULT_PACKAGE_VOUCHER_DISCOUNT);

        // Get all the orderPackagesList where packageVoucherDiscount equals to UPDATED_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageVoucherDiscount.equals=" + UPDATED_PACKAGE_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageVoucherDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageVoucherDiscount not equals to DEFAULT_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageVoucherDiscount.notEquals=" + DEFAULT_PACKAGE_VOUCHER_DISCOUNT);

        // Get all the orderPackagesList where packageVoucherDiscount not equals to UPDATED_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageVoucherDiscount.notEquals=" + UPDATED_PACKAGE_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageVoucherDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageVoucherDiscount in DEFAULT_PACKAGE_VOUCHER_DISCOUNT or UPDATED_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageVoucherDiscount.in=" + DEFAULT_PACKAGE_VOUCHER_DISCOUNT + "," + UPDATED_PACKAGE_VOUCHER_DISCOUNT);

        // Get all the orderPackagesList where packageVoucherDiscount equals to UPDATED_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageVoucherDiscount.in=" + UPDATED_PACKAGE_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageVoucherDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageVoucherDiscount is not null
        defaultOrderPackagesShouldBeFound("packageVoucherDiscount.specified=true");

        // Get all the orderPackagesList where packageVoucherDiscount is null
        defaultOrderPackagesShouldNotBeFound("packageVoucherDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageVoucherDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageVoucherDiscount is greater than or equal to DEFAULT_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageVoucherDiscount.greaterThanOrEqual=" + DEFAULT_PACKAGE_VOUCHER_DISCOUNT);

        // Get all the orderPackagesList where packageVoucherDiscount is greater than or equal to UPDATED_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageVoucherDiscount.greaterThanOrEqual=" + UPDATED_PACKAGE_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageVoucherDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageVoucherDiscount is less than or equal to DEFAULT_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageVoucherDiscount.lessThanOrEqual=" + DEFAULT_PACKAGE_VOUCHER_DISCOUNT);

        // Get all the orderPackagesList where packageVoucherDiscount is less than or equal to SMALLER_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageVoucherDiscount.lessThanOrEqual=" + SMALLER_PACKAGE_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageVoucherDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageVoucherDiscount is less than DEFAULT_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageVoucherDiscount.lessThan=" + DEFAULT_PACKAGE_VOUCHER_DISCOUNT);

        // Get all the orderPackagesList where packageVoucherDiscount is less than UPDATED_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageVoucherDiscount.lessThan=" + UPDATED_PACKAGE_VOUCHER_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackageVoucherDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packageVoucherDiscount is greater than DEFAULT_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packageVoucherDiscount.greaterThan=" + DEFAULT_PACKAGE_VOUCHER_DISCOUNT);

        // Get all the orderPackagesList where packageVoucherDiscount is greater than SMALLER_PACKAGE_VOUCHER_DISCOUNT
        defaultOrderPackagesShouldBeFound("packageVoucherDiscount.greaterThan=" + SMALLER_PACKAGE_VOUCHER_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePromotionDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePromotionDiscount equals to DEFAULT_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldBeFound("packagePromotionDiscount.equals=" + DEFAULT_PACKAGE_PROMOTION_DISCOUNT);

        // Get all the orderPackagesList where packagePromotionDiscount equals to UPDATED_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packagePromotionDiscount.equals=" + UPDATED_PACKAGE_PROMOTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePromotionDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePromotionDiscount not equals to DEFAULT_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packagePromotionDiscount.notEquals=" + DEFAULT_PACKAGE_PROMOTION_DISCOUNT);

        // Get all the orderPackagesList where packagePromotionDiscount not equals to UPDATED_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldBeFound("packagePromotionDiscount.notEquals=" + UPDATED_PACKAGE_PROMOTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePromotionDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePromotionDiscount in DEFAULT_PACKAGE_PROMOTION_DISCOUNT or UPDATED_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldBeFound("packagePromotionDiscount.in=" + DEFAULT_PACKAGE_PROMOTION_DISCOUNT + "," + UPDATED_PACKAGE_PROMOTION_DISCOUNT);

        // Get all the orderPackagesList where packagePromotionDiscount equals to UPDATED_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packagePromotionDiscount.in=" + UPDATED_PACKAGE_PROMOTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePromotionDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePromotionDiscount is not null
        defaultOrderPackagesShouldBeFound("packagePromotionDiscount.specified=true");

        // Get all the orderPackagesList where packagePromotionDiscount is null
        defaultOrderPackagesShouldNotBeFound("packagePromotionDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePromotionDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePromotionDiscount is greater than or equal to DEFAULT_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldBeFound("packagePromotionDiscount.greaterThanOrEqual=" + DEFAULT_PACKAGE_PROMOTION_DISCOUNT);

        // Get all the orderPackagesList where packagePromotionDiscount is greater than or equal to UPDATED_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packagePromotionDiscount.greaterThanOrEqual=" + UPDATED_PACKAGE_PROMOTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePromotionDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePromotionDiscount is less than or equal to DEFAULT_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldBeFound("packagePromotionDiscount.lessThanOrEqual=" + DEFAULT_PACKAGE_PROMOTION_DISCOUNT);

        // Get all the orderPackagesList where packagePromotionDiscount is less than or equal to SMALLER_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packagePromotionDiscount.lessThanOrEqual=" + SMALLER_PACKAGE_PROMOTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePromotionDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePromotionDiscount is less than DEFAULT_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packagePromotionDiscount.lessThan=" + DEFAULT_PACKAGE_PROMOTION_DISCOUNT);

        // Get all the orderPackagesList where packagePromotionDiscount is less than UPDATED_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldBeFound("packagePromotionDiscount.lessThan=" + UPDATED_PACKAGE_PROMOTION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPackagePromotionDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where packagePromotionDiscount is greater than DEFAULT_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldNotBeFound("packagePromotionDiscount.greaterThan=" + DEFAULT_PACKAGE_PROMOTION_DISCOUNT);

        // Get all the orderPackagesList where packagePromotionDiscount is greater than SMALLER_PACKAGE_PROMOTION_DISCOUNT
        defaultOrderPackagesShouldBeFound("packagePromotionDiscount.greaterThan=" + SMALLER_PACKAGE_PROMOTION_DISCOUNT);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByPickingCompletedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where pickingCompletedWhen equals to DEFAULT_PICKING_COMPLETED_WHEN
        defaultOrderPackagesShouldBeFound("pickingCompletedWhen.equals=" + DEFAULT_PICKING_COMPLETED_WHEN);

        // Get all the orderPackagesList where pickingCompletedWhen equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrderPackagesShouldNotBeFound("pickingCompletedWhen.equals=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPickingCompletedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where pickingCompletedWhen not equals to DEFAULT_PICKING_COMPLETED_WHEN
        defaultOrderPackagesShouldNotBeFound("pickingCompletedWhen.notEquals=" + DEFAULT_PICKING_COMPLETED_WHEN);

        // Get all the orderPackagesList where pickingCompletedWhen not equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrderPackagesShouldBeFound("pickingCompletedWhen.notEquals=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPickingCompletedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where pickingCompletedWhen in DEFAULT_PICKING_COMPLETED_WHEN or UPDATED_PICKING_COMPLETED_WHEN
        defaultOrderPackagesShouldBeFound("pickingCompletedWhen.in=" + DEFAULT_PICKING_COMPLETED_WHEN + "," + UPDATED_PICKING_COMPLETED_WHEN);

        // Get all the orderPackagesList where pickingCompletedWhen equals to UPDATED_PICKING_COMPLETED_WHEN
        defaultOrderPackagesShouldNotBeFound("pickingCompletedWhen.in=" + UPDATED_PICKING_COMPLETED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByPickingCompletedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where pickingCompletedWhen is not null
        defaultOrderPackagesShouldBeFound("pickingCompletedWhen.specified=true");

        // Get all the orderPackagesList where pickingCompletedWhen is null
        defaultOrderPackagesShouldNotBeFound("pickingCompletedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCustomerReviewedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerReviewedOn equals to DEFAULT_CUSTOMER_REVIEWED_ON
        defaultOrderPackagesShouldBeFound("customerReviewedOn.equals=" + DEFAULT_CUSTOMER_REVIEWED_ON);

        // Get all the orderPackagesList where customerReviewedOn equals to UPDATED_CUSTOMER_REVIEWED_ON
        defaultOrderPackagesShouldNotBeFound("customerReviewedOn.equals=" + UPDATED_CUSTOMER_REVIEWED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCustomerReviewedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerReviewedOn not equals to DEFAULT_CUSTOMER_REVIEWED_ON
        defaultOrderPackagesShouldNotBeFound("customerReviewedOn.notEquals=" + DEFAULT_CUSTOMER_REVIEWED_ON);

        // Get all the orderPackagesList where customerReviewedOn not equals to UPDATED_CUSTOMER_REVIEWED_ON
        defaultOrderPackagesShouldBeFound("customerReviewedOn.notEquals=" + UPDATED_CUSTOMER_REVIEWED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCustomerReviewedOnIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerReviewedOn in DEFAULT_CUSTOMER_REVIEWED_ON or UPDATED_CUSTOMER_REVIEWED_ON
        defaultOrderPackagesShouldBeFound("customerReviewedOn.in=" + DEFAULT_CUSTOMER_REVIEWED_ON + "," + UPDATED_CUSTOMER_REVIEWED_ON);

        // Get all the orderPackagesList where customerReviewedOn equals to UPDATED_CUSTOMER_REVIEWED_ON
        defaultOrderPackagesShouldNotBeFound("customerReviewedOn.in=" + UPDATED_CUSTOMER_REVIEWED_ON);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCustomerReviewedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerReviewedOn is not null
        defaultOrderPackagesShouldBeFound("customerReviewedOn.specified=true");

        // Get all the orderPackagesList where customerReviewedOn is null
        defaultOrderPackagesShouldNotBeFound("customerReviewedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesBySellerRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where sellerRating equals to DEFAULT_SELLER_RATING
        defaultOrderPackagesShouldBeFound("sellerRating.equals=" + DEFAULT_SELLER_RATING);

        // Get all the orderPackagesList where sellerRating equals to UPDATED_SELLER_RATING
        defaultOrderPackagesShouldNotBeFound("sellerRating.equals=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesBySellerRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where sellerRating not equals to DEFAULT_SELLER_RATING
        defaultOrderPackagesShouldNotBeFound("sellerRating.notEquals=" + DEFAULT_SELLER_RATING);

        // Get all the orderPackagesList where sellerRating not equals to UPDATED_SELLER_RATING
        defaultOrderPackagesShouldBeFound("sellerRating.notEquals=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesBySellerRatingIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where sellerRating in DEFAULT_SELLER_RATING or UPDATED_SELLER_RATING
        defaultOrderPackagesShouldBeFound("sellerRating.in=" + DEFAULT_SELLER_RATING + "," + UPDATED_SELLER_RATING);

        // Get all the orderPackagesList where sellerRating equals to UPDATED_SELLER_RATING
        defaultOrderPackagesShouldNotBeFound("sellerRating.in=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesBySellerRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where sellerRating is not null
        defaultOrderPackagesShouldBeFound("sellerRating.specified=true");

        // Get all the orderPackagesList where sellerRating is null
        defaultOrderPackagesShouldNotBeFound("sellerRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesBySellerRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where sellerRating is greater than or equal to DEFAULT_SELLER_RATING
        defaultOrderPackagesShouldBeFound("sellerRating.greaterThanOrEqual=" + DEFAULT_SELLER_RATING);

        // Get all the orderPackagesList where sellerRating is greater than or equal to UPDATED_SELLER_RATING
        defaultOrderPackagesShouldNotBeFound("sellerRating.greaterThanOrEqual=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesBySellerRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where sellerRating is less than or equal to DEFAULT_SELLER_RATING
        defaultOrderPackagesShouldBeFound("sellerRating.lessThanOrEqual=" + DEFAULT_SELLER_RATING);

        // Get all the orderPackagesList where sellerRating is less than or equal to SMALLER_SELLER_RATING
        defaultOrderPackagesShouldNotBeFound("sellerRating.lessThanOrEqual=" + SMALLER_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesBySellerRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where sellerRating is less than DEFAULT_SELLER_RATING
        defaultOrderPackagesShouldNotBeFound("sellerRating.lessThan=" + DEFAULT_SELLER_RATING);

        // Get all the orderPackagesList where sellerRating is less than UPDATED_SELLER_RATING
        defaultOrderPackagesShouldBeFound("sellerRating.lessThan=" + UPDATED_SELLER_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesBySellerRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where sellerRating is greater than DEFAULT_SELLER_RATING
        defaultOrderPackagesShouldNotBeFound("sellerRating.greaterThan=" + DEFAULT_SELLER_RATING);

        // Get all the orderPackagesList where sellerRating is greater than SMALLER_SELLER_RATING
        defaultOrderPackagesShouldBeFound("sellerRating.greaterThan=" + SMALLER_SELLER_RATING);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryRatingIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryRating equals to DEFAULT_DELIVERY_RATING
        defaultOrderPackagesShouldBeFound("deliveryRating.equals=" + DEFAULT_DELIVERY_RATING);

        // Get all the orderPackagesList where deliveryRating equals to UPDATED_DELIVERY_RATING
        defaultOrderPackagesShouldNotBeFound("deliveryRating.equals=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryRatingIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryRating not equals to DEFAULT_DELIVERY_RATING
        defaultOrderPackagesShouldNotBeFound("deliveryRating.notEquals=" + DEFAULT_DELIVERY_RATING);

        // Get all the orderPackagesList where deliveryRating not equals to UPDATED_DELIVERY_RATING
        defaultOrderPackagesShouldBeFound("deliveryRating.notEquals=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryRatingIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryRating in DEFAULT_DELIVERY_RATING or UPDATED_DELIVERY_RATING
        defaultOrderPackagesShouldBeFound("deliveryRating.in=" + DEFAULT_DELIVERY_RATING + "," + UPDATED_DELIVERY_RATING);

        // Get all the orderPackagesList where deliveryRating equals to UPDATED_DELIVERY_RATING
        defaultOrderPackagesShouldNotBeFound("deliveryRating.in=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryRatingIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryRating is not null
        defaultOrderPackagesShouldBeFound("deliveryRating.specified=true");

        // Get all the orderPackagesList where deliveryRating is null
        defaultOrderPackagesShouldNotBeFound("deliveryRating.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryRatingIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryRating is greater than or equal to DEFAULT_DELIVERY_RATING
        defaultOrderPackagesShouldBeFound("deliveryRating.greaterThanOrEqual=" + DEFAULT_DELIVERY_RATING);

        // Get all the orderPackagesList where deliveryRating is greater than or equal to UPDATED_DELIVERY_RATING
        defaultOrderPackagesShouldNotBeFound("deliveryRating.greaterThanOrEqual=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryRatingIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryRating is less than or equal to DEFAULT_DELIVERY_RATING
        defaultOrderPackagesShouldBeFound("deliveryRating.lessThanOrEqual=" + DEFAULT_DELIVERY_RATING);

        // Get all the orderPackagesList where deliveryRating is less than or equal to SMALLER_DELIVERY_RATING
        defaultOrderPackagesShouldNotBeFound("deliveryRating.lessThanOrEqual=" + SMALLER_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryRatingIsLessThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryRating is less than DEFAULT_DELIVERY_RATING
        defaultOrderPackagesShouldNotBeFound("deliveryRating.lessThan=" + DEFAULT_DELIVERY_RATING);

        // Get all the orderPackagesList where deliveryRating is less than UPDATED_DELIVERY_RATING
        defaultOrderPackagesShouldBeFound("deliveryRating.lessThan=" + UPDATED_DELIVERY_RATING);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryRatingIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where deliveryRating is greater than DEFAULT_DELIVERY_RATING
        defaultOrderPackagesShouldNotBeFound("deliveryRating.greaterThan=" + DEFAULT_DELIVERY_RATING);

        // Get all the orderPackagesList where deliveryRating is greater than SMALLER_DELIVERY_RATING
        defaultOrderPackagesShouldBeFound("deliveryRating.greaterThan=" + SMALLER_DELIVERY_RATING);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByReviewAsAnonymousIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where reviewAsAnonymous equals to DEFAULT_REVIEW_AS_ANONYMOUS
        defaultOrderPackagesShouldBeFound("reviewAsAnonymous.equals=" + DEFAULT_REVIEW_AS_ANONYMOUS);

        // Get all the orderPackagesList where reviewAsAnonymous equals to UPDATED_REVIEW_AS_ANONYMOUS
        defaultOrderPackagesShouldNotBeFound("reviewAsAnonymous.equals=" + UPDATED_REVIEW_AS_ANONYMOUS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByReviewAsAnonymousIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where reviewAsAnonymous not equals to DEFAULT_REVIEW_AS_ANONYMOUS
        defaultOrderPackagesShouldNotBeFound("reviewAsAnonymous.notEquals=" + DEFAULT_REVIEW_AS_ANONYMOUS);

        // Get all the orderPackagesList where reviewAsAnonymous not equals to UPDATED_REVIEW_AS_ANONYMOUS
        defaultOrderPackagesShouldBeFound("reviewAsAnonymous.notEquals=" + UPDATED_REVIEW_AS_ANONYMOUS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByReviewAsAnonymousIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where reviewAsAnonymous in DEFAULT_REVIEW_AS_ANONYMOUS or UPDATED_REVIEW_AS_ANONYMOUS
        defaultOrderPackagesShouldBeFound("reviewAsAnonymous.in=" + DEFAULT_REVIEW_AS_ANONYMOUS + "," + UPDATED_REVIEW_AS_ANONYMOUS);

        // Get all the orderPackagesList where reviewAsAnonymous equals to UPDATED_REVIEW_AS_ANONYMOUS
        defaultOrderPackagesShouldNotBeFound("reviewAsAnonymous.in=" + UPDATED_REVIEW_AS_ANONYMOUS);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByReviewAsAnonymousIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where reviewAsAnonymous is not null
        defaultOrderPackagesShouldBeFound("reviewAsAnonymous.specified=true");

        // Get all the orderPackagesList where reviewAsAnonymous is null
        defaultOrderPackagesShouldNotBeFound("reviewAsAnonymous.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCompletedReviewIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where completedReview equals to DEFAULT_COMPLETED_REVIEW
        defaultOrderPackagesShouldBeFound("completedReview.equals=" + DEFAULT_COMPLETED_REVIEW);

        // Get all the orderPackagesList where completedReview equals to UPDATED_COMPLETED_REVIEW
        defaultOrderPackagesShouldNotBeFound("completedReview.equals=" + UPDATED_COMPLETED_REVIEW);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCompletedReviewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where completedReview not equals to DEFAULT_COMPLETED_REVIEW
        defaultOrderPackagesShouldNotBeFound("completedReview.notEquals=" + DEFAULT_COMPLETED_REVIEW);

        // Get all the orderPackagesList where completedReview not equals to UPDATED_COMPLETED_REVIEW
        defaultOrderPackagesShouldBeFound("completedReview.notEquals=" + UPDATED_COMPLETED_REVIEW);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCompletedReviewIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where completedReview in DEFAULT_COMPLETED_REVIEW or UPDATED_COMPLETED_REVIEW
        defaultOrderPackagesShouldBeFound("completedReview.in=" + DEFAULT_COMPLETED_REVIEW + "," + UPDATED_COMPLETED_REVIEW);

        // Get all the orderPackagesList where completedReview equals to UPDATED_COMPLETED_REVIEW
        defaultOrderPackagesShouldNotBeFound("completedReview.in=" + UPDATED_COMPLETED_REVIEW);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCompletedReviewIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where completedReview is not null
        defaultOrderPackagesShouldBeFound("completedReview.specified=true");

        // Get all the orderPackagesList where completedReview is null
        defaultOrderPackagesShouldNotBeFound("completedReview.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultOrderPackagesShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the orderPackagesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultOrderPackagesShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultOrderPackagesShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the orderPackagesList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultOrderPackagesShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultOrderPackagesShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the orderPackagesList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultOrderPackagesShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedBy is not null
        defaultOrderPackagesShouldBeFound("lastEditedBy.specified=true");

        // Get all the orderPackagesList where lastEditedBy is null
        defaultOrderPackagesShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultOrderPackagesShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the orderPackagesList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultOrderPackagesShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultOrderPackagesShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the orderPackagesList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultOrderPackagesShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultOrderPackagesShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the orderPackagesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultOrderPackagesShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultOrderPackagesShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the orderPackagesList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultOrderPackagesShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultOrderPackagesShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the orderPackagesList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultOrderPackagesShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where lastEditedWhen is not null
        defaultOrderPackagesShouldBeFound("lastEditedWhen.specified=true");

        // Get all the orderPackagesList where lastEditedWhen is null
        defaultOrderPackagesShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByOrderLineListIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);
        OrderLines orderLineList = OrderLinesResourceIT.createEntity(em);
        em.persist(orderLineList);
        em.flush();
        orderPackages.addOrderLineList(orderLineList);
        orderPackagesRepository.saveAndFlush(orderPackages);
        Long orderLineListId = orderLineList.getId();

        // Get all the orderPackagesList where orderLineList equals to orderLineListId
        defaultOrderPackagesShouldBeFound("orderLineListId.equals=" + orderLineListId);

        // Get all the orderPackagesList where orderLineList equals to orderLineListId + 1
        defaultOrderPackagesShouldNotBeFound("orderLineListId.equals=" + (orderLineListId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderPackagesBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        orderPackages.setSupplier(supplier);
        orderPackagesRepository.saveAndFlush(orderPackages);
        Long supplierId = supplier.getId();

        // Get all the orderPackagesList where supplier equals to supplierId
        defaultOrderPackagesShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the orderPackagesList where supplier equals to supplierId + 1
        defaultOrderPackagesShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);
        DeliveryMethods deliveryMethod = DeliveryMethodsResourceIT.createEntity(em);
        em.persist(deliveryMethod);
        em.flush();
        orderPackages.setDeliveryMethod(deliveryMethod);
        orderPackagesRepository.saveAndFlush(orderPackages);
        Long deliveryMethodId = deliveryMethod.getId();

        // Get all the orderPackagesList where deliveryMethod equals to deliveryMethodId
        defaultOrderPackagesShouldBeFound("deliveryMethodId.equals=" + deliveryMethodId);

        // Get all the orderPackagesList where deliveryMethod equals to deliveryMethodId + 1
        defaultOrderPackagesShouldNotBeFound("deliveryMethodId.equals=" + (deliveryMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderPackagesBySpecialDealsIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);
        SpecialDeals specialDeals = SpecialDealsResourceIT.createEntity(em);
        em.persist(specialDeals);
        em.flush();
        orderPackages.setSpecialDeals(specialDeals);
        orderPackagesRepository.saveAndFlush(orderPackages);
        Long specialDealsId = specialDeals.getId();

        // Get all the orderPackagesList where specialDeals equals to specialDealsId
        defaultOrderPackagesShouldBeFound("specialDealsId.equals=" + specialDealsId);

        // Get all the orderPackagesList where specialDeals equals to specialDealsId + 1
        defaultOrderPackagesShouldNotBeFound("specialDealsId.equals=" + (specialDealsId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderPackagesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);
        Orders order = OrdersResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        orderPackages.setOrder(order);
        orderPackagesRepository.saveAndFlush(orderPackages);
        Long orderId = order.getId();

        // Get all the orderPackagesList where order equals to orderId
        defaultOrderPackagesShouldBeFound("orderId.equals=" + orderId);

        // Get all the orderPackagesList where order equals to orderId + 1
        defaultOrderPackagesShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderPackagesShouldBeFound(String filter) throws Exception {
        restOrderPackagesMockMvc.perform(get("/api/order-packages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderPackages.getId().intValue())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderPlacedOn").value(hasItem(DEFAULT_ORDER_PLACED_ON.toString())))
            .andExpect(jsonPath("$.[*].orderDeliveredOn").value(hasItem(DEFAULT_ORDER_DELIVERED_ON.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].packageShippingFee").value(hasItem(DEFAULT_PACKAGE_SHIPPING_FEE.intValue())))
            .andExpect(jsonPath("$.[*].packageShippingFeeDiscount").value(hasItem(DEFAULT_PACKAGE_SHIPPING_FEE_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].packagePrice").value(hasItem(DEFAULT_PACKAGE_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].packageSubTotal").value(hasItem(DEFAULT_PACKAGE_SUB_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].packageTaxAmount").value(hasItem(DEFAULT_PACKAGE_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].packageVoucherDiscount").value(hasItem(DEFAULT_PACKAGE_VOUCHER_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].packagePromotionDiscount").value(hasItem(DEFAULT_PACKAGE_PROMOTION_DISCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].pickingCompletedWhen").value(hasItem(DEFAULT_PICKING_COMPLETED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].customerReviewedOn").value(hasItem(DEFAULT_CUSTOMER_REVIEWED_ON.toString())))
            .andExpect(jsonPath("$.[*].sellerRating").value(hasItem(DEFAULT_SELLER_RATING)))
            .andExpect(jsonPath("$.[*].sellerReview").value(hasItem(DEFAULT_SELLER_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].deliveryRating").value(hasItem(DEFAULT_DELIVERY_RATING)))
            .andExpect(jsonPath("$.[*].deliveryReview").value(hasItem(DEFAULT_DELIVERY_REVIEW.toString())))
            .andExpect(jsonPath("$.[*].reviewAsAnonymous").value(hasItem(DEFAULT_REVIEW_AS_ANONYMOUS.booleanValue())))
            .andExpect(jsonPath("$.[*].completedReview").value(hasItem(DEFAULT_COMPLETED_REVIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].orderPackageDetails").value(hasItem(DEFAULT_ORDER_PACKAGE_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restOrderPackagesMockMvc.perform(get("/api/order-packages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderPackagesShouldNotBeFound(String filter) throws Exception {
        restOrderPackagesMockMvc.perform(get("/api/order-packages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderPackagesMockMvc.perform(get("/api/order-packages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOrderPackages() throws Exception {
        // Get the orderPackages
        restOrderPackagesMockMvc.perform(get("/api/order-packages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderPackages() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        int databaseSizeBeforeUpdate = orderPackagesRepository.findAll().size();

        // Update the orderPackages
        OrderPackages updatedOrderPackages = orderPackagesRepository.findById(orderPackages.getId()).get();
        // Disconnect from session so that the updates on updatedOrderPackages are not directly saved in db
        em.detach(updatedOrderPackages);
        updatedOrderPackages
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .orderPlacedOn(UPDATED_ORDER_PLACED_ON)
            .orderDeliveredOn(UPDATED_ORDER_DELIVERED_ON)
            .comments(UPDATED_COMMENTS)
            .deliveryInstructions(UPDATED_DELIVERY_INSTRUCTIONS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .packageShippingFee(UPDATED_PACKAGE_SHIPPING_FEE)
            .packageShippingFeeDiscount(UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT)
            .packagePrice(UPDATED_PACKAGE_PRICE)
            .packageSubTotal(UPDATED_PACKAGE_SUB_TOTAL)
            .packageTaxAmount(UPDATED_PACKAGE_TAX_AMOUNT)
            .packageVoucherDiscount(UPDATED_PACKAGE_VOUCHER_DISCOUNT)
            .packagePromotionDiscount(UPDATED_PACKAGE_PROMOTION_DISCOUNT)
            .pickingCompletedWhen(UPDATED_PICKING_COMPLETED_WHEN)
            .customerReviewedOn(UPDATED_CUSTOMER_REVIEWED_ON)
            .sellerRating(UPDATED_SELLER_RATING)
            .sellerReview(UPDATED_SELLER_REVIEW)
            .deliveryRating(UPDATED_DELIVERY_RATING)
            .deliveryReview(UPDATED_DELIVERY_REVIEW)
            .reviewAsAnonymous(UPDATED_REVIEW_AS_ANONYMOUS)
            .completedReview(UPDATED_COMPLETED_REVIEW)
            .orderPackageDetails(UPDATED_ORDER_PACKAGE_DETAILS)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        OrderPackagesDTO orderPackagesDTO = orderPackagesMapper.toDto(updatedOrderPackages);

        restOrderPackagesMockMvc.perform(put("/api/order-packages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPackagesDTO)))
            .andExpect(status().isOk());

        // Validate the OrderPackages in the database
        List<OrderPackages> orderPackagesList = orderPackagesRepository.findAll();
        assertThat(orderPackagesList).hasSize(databaseSizeBeforeUpdate);
        OrderPackages testOrderPackages = orderPackagesList.get(orderPackagesList.size() - 1);
        assertThat(testOrderPackages.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testOrderPackages.getOrderPlacedOn()).isEqualTo(UPDATED_ORDER_PLACED_ON);
        assertThat(testOrderPackages.getOrderDeliveredOn()).isEqualTo(UPDATED_ORDER_DELIVERED_ON);
        assertThat(testOrderPackages.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testOrderPackages.getDeliveryInstructions()).isEqualTo(UPDATED_DELIVERY_INSTRUCTIONS);
        assertThat(testOrderPackages.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testOrderPackages.getPackageShippingFee()).isEqualTo(UPDATED_PACKAGE_SHIPPING_FEE);
        assertThat(testOrderPackages.getPackageShippingFeeDiscount()).isEqualTo(UPDATED_PACKAGE_SHIPPING_FEE_DISCOUNT);
        assertThat(testOrderPackages.getPackagePrice()).isEqualTo(UPDATED_PACKAGE_PRICE);
        assertThat(testOrderPackages.getPackageSubTotal()).isEqualTo(UPDATED_PACKAGE_SUB_TOTAL);
        assertThat(testOrderPackages.getPackageTaxAmount()).isEqualTo(UPDATED_PACKAGE_TAX_AMOUNT);
        assertThat(testOrderPackages.getPackageVoucherDiscount()).isEqualTo(UPDATED_PACKAGE_VOUCHER_DISCOUNT);
        assertThat(testOrderPackages.getPackagePromotionDiscount()).isEqualTo(UPDATED_PACKAGE_PROMOTION_DISCOUNT);
        assertThat(testOrderPackages.getPickingCompletedWhen()).isEqualTo(UPDATED_PICKING_COMPLETED_WHEN);
        assertThat(testOrderPackages.getCustomerReviewedOn()).isEqualTo(UPDATED_CUSTOMER_REVIEWED_ON);
        assertThat(testOrderPackages.getSellerRating()).isEqualTo(UPDATED_SELLER_RATING);
        assertThat(testOrderPackages.getSellerReview()).isEqualTo(UPDATED_SELLER_REVIEW);
        assertThat(testOrderPackages.getDeliveryRating()).isEqualTo(UPDATED_DELIVERY_RATING);
        assertThat(testOrderPackages.getDeliveryReview()).isEqualTo(UPDATED_DELIVERY_REVIEW);
        assertThat(testOrderPackages.isReviewAsAnonymous()).isEqualTo(UPDATED_REVIEW_AS_ANONYMOUS);
        assertThat(testOrderPackages.isCompletedReview()).isEqualTo(UPDATED_COMPLETED_REVIEW);
        assertThat(testOrderPackages.getOrderPackageDetails()).isEqualTo(UPDATED_ORDER_PACKAGE_DETAILS);
        assertThat(testOrderPackages.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testOrderPackages.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderPackages() throws Exception {
        int databaseSizeBeforeUpdate = orderPackagesRepository.findAll().size();

        // Create the OrderPackages
        OrderPackagesDTO orderPackagesDTO = orderPackagesMapper.toDto(orderPackages);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderPackagesMockMvc.perform(put("/api/order-packages").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderPackagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderPackages in the database
        List<OrderPackages> orderPackagesList = orderPackagesRepository.findAll();
        assertThat(orderPackagesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderPackages() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        int databaseSizeBeforeDelete = orderPackagesRepository.findAll().size();

        // Delete the orderPackages
        restOrderPackagesMockMvc.perform(delete("/api/order-packages/{id}", orderPackages.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderPackages> orderPackagesList = orderPackagesRepository.findAll();
        assertThat(orderPackagesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
