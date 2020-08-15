package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Subscriptions;
import com.vertical.commerce.repository.SubscriptionsRepository;
import com.vertical.commerce.service.SubscriptionsService;
import com.vertical.commerce.service.dto.SubscriptionsDTO;
import com.vertical.commerce.service.mapper.SubscriptionsMapper;
import com.vertical.commerce.service.dto.SubscriptionsCriteria;
import com.vertical.commerce.service.SubscriptionsQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
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
 * Integration tests for the {@link SubscriptionsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SubscriptionsResourceIT {

    private static final String DEFAULT_EMAIL_ADDRESS = "K@O=.[V7m2}";
    private static final String UPDATED_EMAIL_ADDRESS = "(HFF@b;9.>";

    private static final Instant DEFAULT_SUBSCRIBED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SUBSCRIBED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACTIVE_FLAG = false;
    private static final Boolean UPDATED_ACTIVE_FLAG = true;

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SubscriptionsRepository subscriptionsRepository;

    @Autowired
    private SubscriptionsMapper subscriptionsMapper;

    @Autowired
    private SubscriptionsService subscriptionsService;

    @Autowired
    private SubscriptionsQueryService subscriptionsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubscriptionsMockMvc;

    private Subscriptions subscriptions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subscriptions createEntity(EntityManager em) {
        Subscriptions subscriptions = new Subscriptions()
            .emailAddress(DEFAULT_EMAIL_ADDRESS)
            .subscribedOn(DEFAULT_SUBSCRIBED_ON)
            .activeFlag(DEFAULT_ACTIVE_FLAG)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return subscriptions;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Subscriptions createUpdatedEntity(EntityManager em) {
        Subscriptions subscriptions = new Subscriptions()
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .subscribedOn(UPDATED_SUBSCRIBED_ON)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return subscriptions;
    }

    @BeforeEach
    public void initTest() {
        subscriptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createSubscriptions() throws Exception {
        int databaseSizeBeforeCreate = subscriptionsRepository.findAll().size();
        // Create the Subscriptions
        SubscriptionsDTO subscriptionsDTO = subscriptionsMapper.toDto(subscriptions);
        restSubscriptionsMockMvc.perform(post("/api/subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionsDTO)))
            .andExpect(status().isCreated());

        // Validate the Subscriptions in the database
        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeCreate + 1);
        Subscriptions testSubscriptions = subscriptionsList.get(subscriptionsList.size() - 1);
        assertThat(testSubscriptions.getEmailAddress()).isEqualTo(DEFAULT_EMAIL_ADDRESS);
        assertThat(testSubscriptions.getSubscribedOn()).isEqualTo(DEFAULT_SUBSCRIBED_ON);
        assertThat(testSubscriptions.isActiveFlag()).isEqualTo(DEFAULT_ACTIVE_FLAG);
        assertThat(testSubscriptions.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testSubscriptions.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createSubscriptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = subscriptionsRepository.findAll().size();

        // Create the Subscriptions with an existing ID
        subscriptions.setId(1L);
        SubscriptionsDTO subscriptionsDTO = subscriptionsMapper.toDto(subscriptions);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubscriptionsMockMvc.perform(post("/api/subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subscriptions in the database
        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkEmailAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionsRepository.findAll().size();
        // set the field null
        subscriptions.setEmailAddress(null);

        // Create the Subscriptions, which fails.
        SubscriptionsDTO subscriptionsDTO = subscriptionsMapper.toDto(subscriptions);


        restSubscriptionsMockMvc.perform(post("/api/subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionsDTO)))
            .andExpect(status().isBadRequest());

        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSubscribedOnIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionsRepository.findAll().size();
        // set the field null
        subscriptions.setSubscribedOn(null);

        // Create the Subscriptions, which fails.
        SubscriptionsDTO subscriptionsDTO = subscriptionsMapper.toDto(subscriptions);


        restSubscriptionsMockMvc.perform(post("/api/subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionsDTO)))
            .andExpect(status().isBadRequest());

        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveFlagIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionsRepository.findAll().size();
        // set the field null
        subscriptions.setActiveFlag(null);

        // Create the Subscriptions, which fails.
        SubscriptionsDTO subscriptionsDTO = subscriptionsMapper.toDto(subscriptions);


        restSubscriptionsMockMvc.perform(post("/api/subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionsDTO)))
            .andExpect(status().isBadRequest());

        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = subscriptionsRepository.findAll().size();
        // set the field null
        subscriptions.setValidFrom(null);

        // Create the Subscriptions, which fails.
        SubscriptionsDTO subscriptionsDTO = subscriptionsMapper.toDto(subscriptions);


        restSubscriptionsMockMvc.perform(post("/api/subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionsDTO)))
            .andExpect(status().isBadRequest());

        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubscriptions() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList
        restSubscriptionsMockMvc.perform(get("/api/subscriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].subscribedOn").value(hasItem(DEFAULT_SUBSCRIBED_ON.toString())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getSubscriptions() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get the subscriptions
        restSubscriptionsMockMvc.perform(get("/api/subscriptions/{id}", subscriptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(subscriptions.getId().intValue()))
            .andExpect(jsonPath("$.emailAddress").value(DEFAULT_EMAIL_ADDRESS))
            .andExpect(jsonPath("$.subscribedOn").value(DEFAULT_SUBSCRIBED_ON.toString()))
            .andExpect(jsonPath("$.activeFlag").value(DEFAULT_ACTIVE_FLAG.booleanValue()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getSubscriptionsByIdFiltering() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        Long id = subscriptions.getId();

        defaultSubscriptionsShouldBeFound("id.equals=" + id);
        defaultSubscriptionsShouldNotBeFound("id.notEquals=" + id);

        defaultSubscriptionsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSubscriptionsShouldNotBeFound("id.greaterThan=" + id);

        defaultSubscriptionsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSubscriptionsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSubscriptionsByEmailAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where emailAddress equals to DEFAULT_EMAIL_ADDRESS
        defaultSubscriptionsShouldBeFound("emailAddress.equals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the subscriptionsList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultSubscriptionsShouldNotBeFound("emailAddress.equals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByEmailAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where emailAddress not equals to DEFAULT_EMAIL_ADDRESS
        defaultSubscriptionsShouldNotBeFound("emailAddress.notEquals=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the subscriptionsList where emailAddress not equals to UPDATED_EMAIL_ADDRESS
        defaultSubscriptionsShouldBeFound("emailAddress.notEquals=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByEmailAddressIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where emailAddress in DEFAULT_EMAIL_ADDRESS or UPDATED_EMAIL_ADDRESS
        defaultSubscriptionsShouldBeFound("emailAddress.in=" + DEFAULT_EMAIL_ADDRESS + "," + UPDATED_EMAIL_ADDRESS);

        // Get all the subscriptionsList where emailAddress equals to UPDATED_EMAIL_ADDRESS
        defaultSubscriptionsShouldNotBeFound("emailAddress.in=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByEmailAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where emailAddress is not null
        defaultSubscriptionsShouldBeFound("emailAddress.specified=true");

        // Get all the subscriptionsList where emailAddress is null
        defaultSubscriptionsShouldNotBeFound("emailAddress.specified=false");
    }
                @Test
    @Transactional
    public void getAllSubscriptionsByEmailAddressContainsSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where emailAddress contains DEFAULT_EMAIL_ADDRESS
        defaultSubscriptionsShouldBeFound("emailAddress.contains=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the subscriptionsList where emailAddress contains UPDATED_EMAIL_ADDRESS
        defaultSubscriptionsShouldNotBeFound("emailAddress.contains=" + UPDATED_EMAIL_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByEmailAddressNotContainsSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where emailAddress does not contain DEFAULT_EMAIL_ADDRESS
        defaultSubscriptionsShouldNotBeFound("emailAddress.doesNotContain=" + DEFAULT_EMAIL_ADDRESS);

        // Get all the subscriptionsList where emailAddress does not contain UPDATED_EMAIL_ADDRESS
        defaultSubscriptionsShouldBeFound("emailAddress.doesNotContain=" + UPDATED_EMAIL_ADDRESS);
    }


    @Test
    @Transactional
    public void getAllSubscriptionsBySubscribedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where subscribedOn equals to DEFAULT_SUBSCRIBED_ON
        defaultSubscriptionsShouldBeFound("subscribedOn.equals=" + DEFAULT_SUBSCRIBED_ON);

        // Get all the subscriptionsList where subscribedOn equals to UPDATED_SUBSCRIBED_ON
        defaultSubscriptionsShouldNotBeFound("subscribedOn.equals=" + UPDATED_SUBSCRIBED_ON);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsBySubscribedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where subscribedOn not equals to DEFAULT_SUBSCRIBED_ON
        defaultSubscriptionsShouldNotBeFound("subscribedOn.notEquals=" + DEFAULT_SUBSCRIBED_ON);

        // Get all the subscriptionsList where subscribedOn not equals to UPDATED_SUBSCRIBED_ON
        defaultSubscriptionsShouldBeFound("subscribedOn.notEquals=" + UPDATED_SUBSCRIBED_ON);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsBySubscribedOnIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where subscribedOn in DEFAULT_SUBSCRIBED_ON or UPDATED_SUBSCRIBED_ON
        defaultSubscriptionsShouldBeFound("subscribedOn.in=" + DEFAULT_SUBSCRIBED_ON + "," + UPDATED_SUBSCRIBED_ON);

        // Get all the subscriptionsList where subscribedOn equals to UPDATED_SUBSCRIBED_ON
        defaultSubscriptionsShouldNotBeFound("subscribedOn.in=" + UPDATED_SUBSCRIBED_ON);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsBySubscribedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where subscribedOn is not null
        defaultSubscriptionsShouldBeFound("subscribedOn.specified=true");

        // Get all the subscriptionsList where subscribedOn is null
        defaultSubscriptionsShouldNotBeFound("subscribedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByActiveFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where activeFlag equals to DEFAULT_ACTIVE_FLAG
        defaultSubscriptionsShouldBeFound("activeFlag.equals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the subscriptionsList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultSubscriptionsShouldNotBeFound("activeFlag.equals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByActiveFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where activeFlag not equals to DEFAULT_ACTIVE_FLAG
        defaultSubscriptionsShouldNotBeFound("activeFlag.notEquals=" + DEFAULT_ACTIVE_FLAG);

        // Get all the subscriptionsList where activeFlag not equals to UPDATED_ACTIVE_FLAG
        defaultSubscriptionsShouldBeFound("activeFlag.notEquals=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByActiveFlagIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where activeFlag in DEFAULT_ACTIVE_FLAG or UPDATED_ACTIVE_FLAG
        defaultSubscriptionsShouldBeFound("activeFlag.in=" + DEFAULT_ACTIVE_FLAG + "," + UPDATED_ACTIVE_FLAG);

        // Get all the subscriptionsList where activeFlag equals to UPDATED_ACTIVE_FLAG
        defaultSubscriptionsShouldNotBeFound("activeFlag.in=" + UPDATED_ACTIVE_FLAG);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByActiveFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where activeFlag is not null
        defaultSubscriptionsShouldBeFound("activeFlag.specified=true");

        // Get all the subscriptionsList where activeFlag is null
        defaultSubscriptionsShouldNotBeFound("activeFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where validFrom equals to DEFAULT_VALID_FROM
        defaultSubscriptionsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the subscriptionsList where validFrom equals to UPDATED_VALID_FROM
        defaultSubscriptionsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultSubscriptionsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the subscriptionsList where validFrom not equals to UPDATED_VALID_FROM
        defaultSubscriptionsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultSubscriptionsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the subscriptionsList where validFrom equals to UPDATED_VALID_FROM
        defaultSubscriptionsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where validFrom is not null
        defaultSubscriptionsShouldBeFound("validFrom.specified=true");

        // Get all the subscriptionsList where validFrom is null
        defaultSubscriptionsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where validTo equals to DEFAULT_VALID_TO
        defaultSubscriptionsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the subscriptionsList where validTo equals to UPDATED_VALID_TO
        defaultSubscriptionsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where validTo not equals to DEFAULT_VALID_TO
        defaultSubscriptionsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the subscriptionsList where validTo not equals to UPDATED_VALID_TO
        defaultSubscriptionsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultSubscriptionsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the subscriptionsList where validTo equals to UPDATED_VALID_TO
        defaultSubscriptionsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllSubscriptionsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        // Get all the subscriptionsList where validTo is not null
        defaultSubscriptionsShouldBeFound("validTo.specified=true");

        // Get all the subscriptionsList where validTo is null
        defaultSubscriptionsShouldNotBeFound("validTo.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubscriptionsShouldBeFound(String filter) throws Exception {
        restSubscriptionsMockMvc.perform(get("/api/subscriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subscriptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailAddress").value(hasItem(DEFAULT_EMAIL_ADDRESS)))
            .andExpect(jsonPath("$.[*].subscribedOn").value(hasItem(DEFAULT_SUBSCRIBED_ON.toString())))
            .andExpect(jsonPath("$.[*].activeFlag").value(hasItem(DEFAULT_ACTIVE_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restSubscriptionsMockMvc.perform(get("/api/subscriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubscriptionsShouldNotBeFound(String filter) throws Exception {
        restSubscriptionsMockMvc.perform(get("/api/subscriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubscriptionsMockMvc.perform(get("/api/subscriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSubscriptions() throws Exception {
        // Get the subscriptions
        restSubscriptionsMockMvc.perform(get("/api/subscriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubscriptions() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        int databaseSizeBeforeUpdate = subscriptionsRepository.findAll().size();

        // Update the subscriptions
        Subscriptions updatedSubscriptions = subscriptionsRepository.findById(subscriptions.getId()).get();
        // Disconnect from session so that the updates on updatedSubscriptions are not directly saved in db
        em.detach(updatedSubscriptions);
        updatedSubscriptions
            .emailAddress(UPDATED_EMAIL_ADDRESS)
            .subscribedOn(UPDATED_SUBSCRIBED_ON)
            .activeFlag(UPDATED_ACTIVE_FLAG)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        SubscriptionsDTO subscriptionsDTO = subscriptionsMapper.toDto(updatedSubscriptions);

        restSubscriptionsMockMvc.perform(put("/api/subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionsDTO)))
            .andExpect(status().isOk());

        // Validate the Subscriptions in the database
        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeUpdate);
        Subscriptions testSubscriptions = subscriptionsList.get(subscriptionsList.size() - 1);
        assertThat(testSubscriptions.getEmailAddress()).isEqualTo(UPDATED_EMAIL_ADDRESS);
        assertThat(testSubscriptions.getSubscribedOn()).isEqualTo(UPDATED_SUBSCRIBED_ON);
        assertThat(testSubscriptions.isActiveFlag()).isEqualTo(UPDATED_ACTIVE_FLAG);
        assertThat(testSubscriptions.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testSubscriptions.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingSubscriptions() throws Exception {
        int databaseSizeBeforeUpdate = subscriptionsRepository.findAll().size();

        // Create the Subscriptions
        SubscriptionsDTO subscriptionsDTO = subscriptionsMapper.toDto(subscriptions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubscriptionsMockMvc.perform(put("/api/subscriptions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(subscriptionsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Subscriptions in the database
        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSubscriptions() throws Exception {
        // Initialize the database
        subscriptionsRepository.saveAndFlush(subscriptions);

        int databaseSizeBeforeDelete = subscriptionsRepository.findAll().size();

        // Delete the subscriptions
        restSubscriptionsMockMvc.perform(delete("/api/subscriptions/{id}", subscriptions.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Subscriptions> subscriptionsList = subscriptionsRepository.findAll();
        assertThat(subscriptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
