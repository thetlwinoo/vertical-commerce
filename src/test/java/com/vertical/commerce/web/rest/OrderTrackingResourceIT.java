package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.OrderTracking;
import com.vertical.commerce.domain.Orders;
import com.vertical.commerce.domain.TrackingEvent;
import com.vertical.commerce.repository.OrderTrackingRepository;
import com.vertical.commerce.service.OrderTrackingService;
import com.vertical.commerce.service.dto.OrderTrackingDTO;
import com.vertical.commerce.service.mapper.OrderTrackingMapper;
import com.vertical.commerce.service.dto.OrderTrackingCriteria;
import com.vertical.commerce.service.OrderTrackingQueryService;

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
 * Integration tests for the {@link OrderTrackingResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class OrderTrackingResourceIT {

    private static final String DEFAULT_CARRIER_TRACKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER_TRACKING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_DETAILS = "BBBBBBBBBB";

    private static final Instant DEFAULT_EVENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EVENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private OrderTrackingRepository orderTrackingRepository;

    @Autowired
    private OrderTrackingMapper orderTrackingMapper;

    @Autowired
    private OrderTrackingService orderTrackingService;

    @Autowired
    private OrderTrackingQueryService orderTrackingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTrackingMockMvc;

    private OrderTracking orderTracking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTracking createEntity(EntityManager em) {
        OrderTracking orderTracking = new OrderTracking()
            .carrierTrackingNumber(DEFAULT_CARRIER_TRACKING_NUMBER)
            .eventDetails(DEFAULT_EVENT_DETAILS)
            .eventDate(DEFAULT_EVENT_DATE);
        return orderTracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTracking createUpdatedEntity(EntityManager em) {
        OrderTracking orderTracking = new OrderTracking()
            .carrierTrackingNumber(UPDATED_CARRIER_TRACKING_NUMBER)
            .eventDetails(UPDATED_EVENT_DETAILS)
            .eventDate(UPDATED_EVENT_DATE);
        return orderTracking;
    }

    @BeforeEach
    public void initTest() {
        orderTracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderTracking() throws Exception {
        int databaseSizeBeforeCreate = orderTrackingRepository.findAll().size();
        // Create the OrderTracking
        OrderTrackingDTO orderTrackingDTO = orderTrackingMapper.toDto(orderTracking);
        restOrderTrackingMockMvc.perform(post("/api/order-trackings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTrackingDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderTracking in the database
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTracking testOrderTracking = orderTrackingList.get(orderTrackingList.size() - 1);
        assertThat(testOrderTracking.getCarrierTrackingNumber()).isEqualTo(DEFAULT_CARRIER_TRACKING_NUMBER);
        assertThat(testOrderTracking.getEventDetails()).isEqualTo(DEFAULT_EVENT_DETAILS);
        assertThat(testOrderTracking.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
    }

    @Test
    @Transactional
    public void createOrderTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTrackingRepository.findAll().size();

        // Create the OrderTracking with an existing ID
        orderTracking.setId(1L);
        OrderTrackingDTO orderTrackingDTO = orderTrackingMapper.toDto(orderTracking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTrackingMockMvc.perform(post("/api/order-trackings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTracking in the database
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEventDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderTrackingRepository.findAll().size();
        // set the field null
        orderTracking.setEventDate(null);

        // Create the OrderTracking, which fails.
        OrderTrackingDTO orderTrackingDTO = orderTrackingMapper.toDto(orderTracking);


        restOrderTrackingMockMvc.perform(post("/api/order-trackings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTrackingDTO)))
            .andExpect(status().isBadRequest());

        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrderTrackings() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList
        restOrderTrackingMockMvc.perform(get("/api/order-trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].carrierTrackingNumber").value(hasItem(DEFAULT_CARRIER_TRACKING_NUMBER)))
            .andExpect(jsonPath("$.[*].eventDetails").value(hasItem(DEFAULT_EVENT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOrderTracking() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get the orderTracking
        restOrderTrackingMockMvc.perform(get("/api/order-trackings/{id}", orderTracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderTracking.getId().intValue()))
            .andExpect(jsonPath("$.carrierTrackingNumber").value(DEFAULT_CARRIER_TRACKING_NUMBER))
            .andExpect(jsonPath("$.eventDetails").value(DEFAULT_EVENT_DETAILS.toString()))
            .andExpect(jsonPath("$.eventDate").value(DEFAULT_EVENT_DATE.toString()));
    }


    @Test
    @Transactional
    public void getOrderTrackingsByIdFiltering() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        Long id = orderTracking.getId();

        defaultOrderTrackingShouldBeFound("id.equals=" + id);
        defaultOrderTrackingShouldNotBeFound("id.notEquals=" + id);

        defaultOrderTrackingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderTrackingShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderTrackingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderTrackingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderTrackingsByCarrierTrackingNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where carrierTrackingNumber equals to DEFAULT_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldBeFound("carrierTrackingNumber.equals=" + DEFAULT_CARRIER_TRACKING_NUMBER);

        // Get all the orderTrackingList where carrierTrackingNumber equals to UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldNotBeFound("carrierTrackingNumber.equals=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderTrackingsByCarrierTrackingNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where carrierTrackingNumber not equals to DEFAULT_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldNotBeFound("carrierTrackingNumber.notEquals=" + DEFAULT_CARRIER_TRACKING_NUMBER);

        // Get all the orderTrackingList where carrierTrackingNumber not equals to UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldBeFound("carrierTrackingNumber.notEquals=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderTrackingsByCarrierTrackingNumberIsInShouldWork() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where carrierTrackingNumber in DEFAULT_CARRIER_TRACKING_NUMBER or UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldBeFound("carrierTrackingNumber.in=" + DEFAULT_CARRIER_TRACKING_NUMBER + "," + UPDATED_CARRIER_TRACKING_NUMBER);

        // Get all the orderTrackingList where carrierTrackingNumber equals to UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldNotBeFound("carrierTrackingNumber.in=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderTrackingsByCarrierTrackingNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where carrierTrackingNumber is not null
        defaultOrderTrackingShouldBeFound("carrierTrackingNumber.specified=true");

        // Get all the orderTrackingList where carrierTrackingNumber is null
        defaultOrderTrackingShouldNotBeFound("carrierTrackingNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrderTrackingsByCarrierTrackingNumberContainsSomething() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where carrierTrackingNumber contains DEFAULT_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldBeFound("carrierTrackingNumber.contains=" + DEFAULT_CARRIER_TRACKING_NUMBER);

        // Get all the orderTrackingList where carrierTrackingNumber contains UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldNotBeFound("carrierTrackingNumber.contains=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }

    @Test
    @Transactional
    public void getAllOrderTrackingsByCarrierTrackingNumberNotContainsSomething() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where carrierTrackingNumber does not contain DEFAULT_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldNotBeFound("carrierTrackingNumber.doesNotContain=" + DEFAULT_CARRIER_TRACKING_NUMBER);

        // Get all the orderTrackingList where carrierTrackingNumber does not contain UPDATED_CARRIER_TRACKING_NUMBER
        defaultOrderTrackingShouldBeFound("carrierTrackingNumber.doesNotContain=" + UPDATED_CARRIER_TRACKING_NUMBER);
    }


    @Test
    @Transactional
    public void getAllOrderTrackingsByEventDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where eventDate equals to DEFAULT_EVENT_DATE
        defaultOrderTrackingShouldBeFound("eventDate.equals=" + DEFAULT_EVENT_DATE);

        // Get all the orderTrackingList where eventDate equals to UPDATED_EVENT_DATE
        defaultOrderTrackingShouldNotBeFound("eventDate.equals=" + UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderTrackingsByEventDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where eventDate not equals to DEFAULT_EVENT_DATE
        defaultOrderTrackingShouldNotBeFound("eventDate.notEquals=" + DEFAULT_EVENT_DATE);

        // Get all the orderTrackingList where eventDate not equals to UPDATED_EVENT_DATE
        defaultOrderTrackingShouldBeFound("eventDate.notEquals=" + UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderTrackingsByEventDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where eventDate in DEFAULT_EVENT_DATE or UPDATED_EVENT_DATE
        defaultOrderTrackingShouldBeFound("eventDate.in=" + DEFAULT_EVENT_DATE + "," + UPDATED_EVENT_DATE);

        // Get all the orderTrackingList where eventDate equals to UPDATED_EVENT_DATE
        defaultOrderTrackingShouldNotBeFound("eventDate.in=" + UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderTrackingsByEventDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        // Get all the orderTrackingList where eventDate is not null
        defaultOrderTrackingShouldBeFound("eventDate.specified=true");

        // Get all the orderTrackingList where eventDate is null
        defaultOrderTrackingShouldNotBeFound("eventDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderTrackingsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);
        Orders order = OrdersResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        orderTracking.setOrder(order);
        orderTrackingRepository.saveAndFlush(orderTracking);
        Long orderId = order.getId();

        // Get all the orderTrackingList where order equals to orderId
        defaultOrderTrackingShouldBeFound("orderId.equals=" + orderId);

        // Get all the orderTrackingList where order equals to orderId + 1
        defaultOrderTrackingShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderTrackingsByTrackingEventIsEqualToSomething() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);
        TrackingEvent trackingEvent = TrackingEventResourceIT.createEntity(em);
        em.persist(trackingEvent);
        em.flush();
        orderTracking.setTrackingEvent(trackingEvent);
        orderTrackingRepository.saveAndFlush(orderTracking);
        Long trackingEventId = trackingEvent.getId();

        // Get all the orderTrackingList where trackingEvent equals to trackingEventId
        defaultOrderTrackingShouldBeFound("trackingEventId.equals=" + trackingEventId);

        // Get all the orderTrackingList where trackingEvent equals to trackingEventId + 1
        defaultOrderTrackingShouldNotBeFound("trackingEventId.equals=" + (trackingEventId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderTrackingShouldBeFound(String filter) throws Exception {
        restOrderTrackingMockMvc.perform(get("/api/order-trackings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].carrierTrackingNumber").value(hasItem(DEFAULT_CARRIER_TRACKING_NUMBER)))
            .andExpect(jsonPath("$.[*].eventDetails").value(hasItem(DEFAULT_EVENT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE.toString())));

        // Check, that the count call also returns 1
        restOrderTrackingMockMvc.perform(get("/api/order-trackings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderTrackingShouldNotBeFound(String filter) throws Exception {
        restOrderTrackingMockMvc.perform(get("/api/order-trackings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderTrackingMockMvc.perform(get("/api/order-trackings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOrderTracking() throws Exception {
        // Get the orderTracking
        restOrderTrackingMockMvc.perform(get("/api/order-trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTracking() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        int databaseSizeBeforeUpdate = orderTrackingRepository.findAll().size();

        // Update the orderTracking
        OrderTracking updatedOrderTracking = orderTrackingRepository.findById(orderTracking.getId()).get();
        // Disconnect from session so that the updates on updatedOrderTracking are not directly saved in db
        em.detach(updatedOrderTracking);
        updatedOrderTracking
            .carrierTrackingNumber(UPDATED_CARRIER_TRACKING_NUMBER)
            .eventDetails(UPDATED_EVENT_DETAILS)
            .eventDate(UPDATED_EVENT_DATE);
        OrderTrackingDTO orderTrackingDTO = orderTrackingMapper.toDto(updatedOrderTracking);

        restOrderTrackingMockMvc.perform(put("/api/order-trackings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTrackingDTO)))
            .andExpect(status().isOk());

        // Validate the OrderTracking in the database
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeUpdate);
        OrderTracking testOrderTracking = orderTrackingList.get(orderTrackingList.size() - 1);
        assertThat(testOrderTracking.getCarrierTrackingNumber()).isEqualTo(UPDATED_CARRIER_TRACKING_NUMBER);
        assertThat(testOrderTracking.getEventDetails()).isEqualTo(UPDATED_EVENT_DETAILS);
        assertThat(testOrderTracking.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderTracking() throws Exception {
        int databaseSizeBeforeUpdate = orderTrackingRepository.findAll().size();

        // Create the OrderTracking
        OrderTrackingDTO orderTrackingDTO = orderTrackingMapper.toDto(orderTracking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTrackingMockMvc.perform(put("/api/order-trackings").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTrackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTracking in the database
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderTracking() throws Exception {
        // Initialize the database
        orderTrackingRepository.saveAndFlush(orderTracking);

        int databaseSizeBeforeDelete = orderTrackingRepository.findAll().size();

        // Delete the orderTracking
        restOrderTrackingMockMvc.perform(delete("/api/order-trackings/{id}", orderTracking.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderTracking> orderTrackingList = orderTrackingRepository.findAll();
        assertThat(orderTrackingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
