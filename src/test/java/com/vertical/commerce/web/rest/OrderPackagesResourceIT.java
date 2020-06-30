package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.OrderPackages;
import com.vertical.commerce.domain.OrderLines;
import com.vertical.commerce.domain.Suppliers;
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

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_INSTRUCTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_INSTRUCTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER = "BBBBBBBBBB";

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
            .comments(DEFAULT_COMMENTS)
            .deliveryInstructions(DEFAULT_DELIVERY_INSTRUCTIONS)
            .internalComments(DEFAULT_INTERNAL_COMMENTS)
            .customerPurchaseOrderNumber(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)
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
            .comments(UPDATED_COMMENTS)
            .deliveryInstructions(UPDATED_DELIVERY_INSTRUCTIONS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .customerPurchaseOrderNumber(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER)
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
        assertThat(testOrderPackages.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testOrderPackages.getDeliveryInstructions()).isEqualTo(DEFAULT_DELIVERY_INSTRUCTIONS);
        assertThat(testOrderPackages.getInternalComments()).isEqualTo(DEFAULT_INTERNAL_COMMENTS);
        assertThat(testOrderPackages.getCustomerPurchaseOrderNumber()).isEqualTo(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);
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
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].customerPurchaseOrderNumber").value(hasItem(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)))
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
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.deliveryInstructions").value(DEFAULT_DELIVERY_INSTRUCTIONS))
            .andExpect(jsonPath("$.internalComments").value(DEFAULT_INTERNAL_COMMENTS))
            .andExpect(jsonPath("$.customerPurchaseOrderNumber").value(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER))
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
    public void getAllOrderPackagesByCustomerPurchaseOrderNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerPurchaseOrderNumber equals to DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldBeFound("customerPurchaseOrderNumber.equals=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the orderPackagesList where customerPurchaseOrderNumber equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldNotBeFound("customerPurchaseOrderNumber.equals=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCustomerPurchaseOrderNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerPurchaseOrderNumber not equals to DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldNotBeFound("customerPurchaseOrderNumber.notEquals=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the orderPackagesList where customerPurchaseOrderNumber not equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldBeFound("customerPurchaseOrderNumber.notEquals=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCustomerPurchaseOrderNumberIsInShouldWork() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerPurchaseOrderNumber in DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER or UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldBeFound("customerPurchaseOrderNumber.in=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER + "," + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the orderPackagesList where customerPurchaseOrderNumber equals to UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldNotBeFound("customerPurchaseOrderNumber.in=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCustomerPurchaseOrderNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerPurchaseOrderNumber is not null
        defaultOrderPackagesShouldBeFound("customerPurchaseOrderNumber.specified=true");

        // Get all the orderPackagesList where customerPurchaseOrderNumber is null
        defaultOrderPackagesShouldNotBeFound("customerPurchaseOrderNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderPackagesByCustomerPurchaseOrderNumberContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerPurchaseOrderNumber contains DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldBeFound("customerPurchaseOrderNumber.contains=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the orderPackagesList where customerPurchaseOrderNumber contains UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldNotBeFound("customerPurchaseOrderNumber.contains=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderPackagesByCustomerPurchaseOrderNumberNotContainsSomething() throws Exception {
        // Initialize the database
        orderPackagesRepository.saveAndFlush(orderPackages);

        // Get all the orderPackagesList where customerPurchaseOrderNumber does not contain DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldNotBeFound("customerPurchaseOrderNumber.doesNotContain=" + DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER);

        // Get all the orderPackagesList where customerPurchaseOrderNumber does not contain UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER
        defaultOrderPackagesShouldBeFound("customerPurchaseOrderNumber.doesNotContain=" + UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
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
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].deliveryInstructions").value(hasItem(DEFAULT_DELIVERY_INSTRUCTIONS)))
            .andExpect(jsonPath("$.[*].internalComments").value(hasItem(DEFAULT_INTERNAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].customerPurchaseOrderNumber").value(hasItem(DEFAULT_CUSTOMER_PURCHASE_ORDER_NUMBER)))
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
            .comments(UPDATED_COMMENTS)
            .deliveryInstructions(UPDATED_DELIVERY_INSTRUCTIONS)
            .internalComments(UPDATED_INTERNAL_COMMENTS)
            .customerPurchaseOrderNumber(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER)
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
        assertThat(testOrderPackages.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testOrderPackages.getDeliveryInstructions()).isEqualTo(UPDATED_DELIVERY_INSTRUCTIONS);
        assertThat(testOrderPackages.getInternalComments()).isEqualTo(UPDATED_INTERNAL_COMMENTS);
        assertThat(testOrderPackages.getCustomerPurchaseOrderNumber()).isEqualTo(UPDATED_CUSTOMER_PURCHASE_ORDER_NUMBER);
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
