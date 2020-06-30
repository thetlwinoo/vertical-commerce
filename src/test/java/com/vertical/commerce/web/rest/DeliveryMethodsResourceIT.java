package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.DeliveryMethods;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.repository.DeliveryMethodsRepository;
import com.vertical.commerce.service.DeliveryMethodsService;
import com.vertical.commerce.service.dto.DeliveryMethodsDTO;
import com.vertical.commerce.service.mapper.DeliveryMethodsMapper;
import com.vertical.commerce.service.dto.DeliveryMethodsCriteria;
import com.vertical.commerce.service.DeliveryMethodsQueryService;

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
 * Integration tests for the {@link DeliveryMethodsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DeliveryMethodsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_PARTY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_PARTY_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS = 1;
    private static final Integer UPDATED_EXPECTED_MIN_ARRIVAL_DAYS = 2;
    private static final Integer SMALLER_EXPECTED_MIN_ARRIVAL_DAYS = 1 - 1;

    private static final Integer DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS = 1;
    private static final Integer UPDATED_EXPECTED_MAX_ARRIVAL_DAYS = 2;
    private static final Integer SMALLER_EXPECTED_MAX_ARRIVAL_DAYS = 1 - 1;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final Boolean DEFAULT_DEFAULT_IND = false;
    private static final Boolean UPDATED_DEFAULT_IND = true;

    private static final String DEFAULT_DELIVERY_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DeliveryMethodsRepository deliveryMethodsRepository;

    @Autowired
    private DeliveryMethodsMapper deliveryMethodsMapper;

    @Autowired
    private DeliveryMethodsService deliveryMethodsService;

    @Autowired
    private DeliveryMethodsQueryService deliveryMethodsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeliveryMethodsMockMvc;

    private DeliveryMethods deliveryMethods;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryMethods createEntity(EntityManager em) {
        DeliveryMethods deliveryMethods = new DeliveryMethods()
            .name(DEFAULT_NAME)
            .thirdPartyName(DEFAULT_THIRD_PARTY_NAME)
            .expectedMinArrivalDays(DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS)
            .expectedMaxArrivalDays(DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS)
            .activeInd(DEFAULT_ACTIVE_IND)
            .defaultInd(DEFAULT_DEFAULT_IND)
            .deliveryNote(DEFAULT_DELIVERY_NOTE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return deliveryMethods;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DeliveryMethods createUpdatedEntity(EntityManager em) {
        DeliveryMethods deliveryMethods = new DeliveryMethods()
            .name(UPDATED_NAME)
            .thirdPartyName(UPDATED_THIRD_PARTY_NAME)
            .expectedMinArrivalDays(UPDATED_EXPECTED_MIN_ARRIVAL_DAYS)
            .expectedMaxArrivalDays(UPDATED_EXPECTED_MAX_ARRIVAL_DAYS)
            .activeInd(UPDATED_ACTIVE_IND)
            .defaultInd(UPDATED_DEFAULT_IND)
            .deliveryNote(UPDATED_DELIVERY_NOTE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return deliveryMethods;
    }

    @BeforeEach
    public void initTest() {
        deliveryMethods = createEntity(em);
    }

    @Test
    @Transactional
    public void createDeliveryMethods() throws Exception {
        int databaseSizeBeforeCreate = deliveryMethodsRepository.findAll().size();
        // Create the DeliveryMethods
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);
        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isCreated());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeCreate + 1);
        DeliveryMethods testDeliveryMethods = deliveryMethodsList.get(deliveryMethodsList.size() - 1);
        assertThat(testDeliveryMethods.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDeliveryMethods.getThirdPartyName()).isEqualTo(DEFAULT_THIRD_PARTY_NAME);
        assertThat(testDeliveryMethods.getExpectedMinArrivalDays()).isEqualTo(DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS);
        assertThat(testDeliveryMethods.getExpectedMaxArrivalDays()).isEqualTo(DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS);
        assertThat(testDeliveryMethods.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testDeliveryMethods.isDefaultInd()).isEqualTo(DEFAULT_DEFAULT_IND);
        assertThat(testDeliveryMethods.getDeliveryNote()).isEqualTo(DEFAULT_DELIVERY_NOTE);
        assertThat(testDeliveryMethods.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testDeliveryMethods.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createDeliveryMethodsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = deliveryMethodsRepository.findAll().size();

        // Create the DeliveryMethods with an existing ID
        deliveryMethods.setId(1L);
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setName(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);


        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setValidFrom(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);


        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = deliveryMethodsRepository.findAll().size();
        // set the field null
        deliveryMethods.setValidTo(null);

        // Create the DeliveryMethods, which fails.
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);


        restDeliveryMethodsMockMvc.perform(post("/api/delivery-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].thirdPartyName").value(hasItem(DEFAULT_THIRD_PARTY_NAME)))
            .andExpect(jsonPath("$.[*].expectedMinArrivalDays").value(hasItem(DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS)))
            .andExpect(jsonPath("$.[*].expectedMaxArrivalDays").value(hasItem(DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryNote").value(hasItem(DEFAULT_DELIVERY_NOTE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get the deliveryMethods
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/{id}", deliveryMethods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(deliveryMethods.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.thirdPartyName").value(DEFAULT_THIRD_PARTY_NAME))
            .andExpect(jsonPath("$.expectedMinArrivalDays").value(DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS))
            .andExpect(jsonPath("$.expectedMaxArrivalDays").value(DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.defaultInd").value(DEFAULT_DEFAULT_IND.booleanValue()))
            .andExpect(jsonPath("$.deliveryNote").value(DEFAULT_DELIVERY_NOTE))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getDeliveryMethodsByIdFiltering() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        Long id = deliveryMethods.getId();

        defaultDeliveryMethodsShouldBeFound("id.equals=" + id);
        defaultDeliveryMethodsShouldNotBeFound("id.notEquals=" + id);

        defaultDeliveryMethodsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDeliveryMethodsShouldNotBeFound("id.greaterThan=" + id);

        defaultDeliveryMethodsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDeliveryMethodsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name equals to DEFAULT_NAME
        defaultDeliveryMethodsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the deliveryMethodsList where name equals to UPDATED_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name not equals to DEFAULT_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the deliveryMethodsList where name not equals to UPDATED_NAME
        defaultDeliveryMethodsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDeliveryMethodsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the deliveryMethodsList where name equals to UPDATED_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name is not null
        defaultDeliveryMethodsShouldBeFound("name.specified=true");

        // Get all the deliveryMethodsList where name is null
        defaultDeliveryMethodsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeliveryMethodsByNameContainsSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name contains DEFAULT_NAME
        defaultDeliveryMethodsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the deliveryMethodsList where name contains UPDATED_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where name does not contain DEFAULT_NAME
        defaultDeliveryMethodsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the deliveryMethodsList where name does not contain UPDATED_NAME
        defaultDeliveryMethodsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDeliveryMethodsByThirdPartyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where thirdPartyName equals to DEFAULT_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldBeFound("thirdPartyName.equals=" + DEFAULT_THIRD_PARTY_NAME);

        // Get all the deliveryMethodsList where thirdPartyName equals to UPDATED_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldNotBeFound("thirdPartyName.equals=" + UPDATED_THIRD_PARTY_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByThirdPartyNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where thirdPartyName not equals to DEFAULT_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldNotBeFound("thirdPartyName.notEquals=" + DEFAULT_THIRD_PARTY_NAME);

        // Get all the deliveryMethodsList where thirdPartyName not equals to UPDATED_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldBeFound("thirdPartyName.notEquals=" + UPDATED_THIRD_PARTY_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByThirdPartyNameIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where thirdPartyName in DEFAULT_THIRD_PARTY_NAME or UPDATED_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldBeFound("thirdPartyName.in=" + DEFAULT_THIRD_PARTY_NAME + "," + UPDATED_THIRD_PARTY_NAME);

        // Get all the deliveryMethodsList where thirdPartyName equals to UPDATED_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldNotBeFound("thirdPartyName.in=" + UPDATED_THIRD_PARTY_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByThirdPartyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where thirdPartyName is not null
        defaultDeliveryMethodsShouldBeFound("thirdPartyName.specified=true");

        // Get all the deliveryMethodsList where thirdPartyName is null
        defaultDeliveryMethodsShouldNotBeFound("thirdPartyName.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeliveryMethodsByThirdPartyNameContainsSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where thirdPartyName contains DEFAULT_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldBeFound("thirdPartyName.contains=" + DEFAULT_THIRD_PARTY_NAME);

        // Get all the deliveryMethodsList where thirdPartyName contains UPDATED_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldNotBeFound("thirdPartyName.contains=" + UPDATED_THIRD_PARTY_NAME);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByThirdPartyNameNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where thirdPartyName does not contain DEFAULT_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldNotBeFound("thirdPartyName.doesNotContain=" + DEFAULT_THIRD_PARTY_NAME);

        // Get all the deliveryMethodsList where thirdPartyName does not contain UPDATED_THIRD_PARTY_NAME
        defaultDeliveryMethodsShouldBeFound("thirdPartyName.doesNotContain=" + UPDATED_THIRD_PARTY_NAME);
    }


    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMinArrivalDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMinArrivalDays equals to DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMinArrivalDays.equals=" + DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMinArrivalDays equals to UPDATED_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMinArrivalDays.equals=" + UPDATED_EXPECTED_MIN_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMinArrivalDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMinArrivalDays not equals to DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMinArrivalDays.notEquals=" + DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMinArrivalDays not equals to UPDATED_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMinArrivalDays.notEquals=" + UPDATED_EXPECTED_MIN_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMinArrivalDaysIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMinArrivalDays in DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS or UPDATED_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMinArrivalDays.in=" + DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS + "," + UPDATED_EXPECTED_MIN_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMinArrivalDays equals to UPDATED_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMinArrivalDays.in=" + UPDATED_EXPECTED_MIN_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMinArrivalDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is not null
        defaultDeliveryMethodsShouldBeFound("expectedMinArrivalDays.specified=true");

        // Get all the deliveryMethodsList where expectedMinArrivalDays is null
        defaultDeliveryMethodsShouldNotBeFound("expectedMinArrivalDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMinArrivalDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is greater than or equal to DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMinArrivalDays.greaterThanOrEqual=" + DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is greater than or equal to UPDATED_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMinArrivalDays.greaterThanOrEqual=" + UPDATED_EXPECTED_MIN_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMinArrivalDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is less than or equal to DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMinArrivalDays.lessThanOrEqual=" + DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is less than or equal to SMALLER_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMinArrivalDays.lessThanOrEqual=" + SMALLER_EXPECTED_MIN_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMinArrivalDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is less than DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMinArrivalDays.lessThan=" + DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is less than UPDATED_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMinArrivalDays.lessThan=" + UPDATED_EXPECTED_MIN_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMinArrivalDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is greater than DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMinArrivalDays.greaterThan=" + DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMinArrivalDays is greater than SMALLER_EXPECTED_MIN_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMinArrivalDays.greaterThan=" + SMALLER_EXPECTED_MIN_ARRIVAL_DAYS);
    }


    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMaxArrivalDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays equals to DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMaxArrivalDays.equals=" + DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays equals to UPDATED_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMaxArrivalDays.equals=" + UPDATED_EXPECTED_MAX_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMaxArrivalDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays not equals to DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMaxArrivalDays.notEquals=" + DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays not equals to UPDATED_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMaxArrivalDays.notEquals=" + UPDATED_EXPECTED_MAX_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMaxArrivalDaysIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays in DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS or UPDATED_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMaxArrivalDays.in=" + DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS + "," + UPDATED_EXPECTED_MAX_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays equals to UPDATED_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMaxArrivalDays.in=" + UPDATED_EXPECTED_MAX_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMaxArrivalDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is not null
        defaultDeliveryMethodsShouldBeFound("expectedMaxArrivalDays.specified=true");

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is null
        defaultDeliveryMethodsShouldNotBeFound("expectedMaxArrivalDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMaxArrivalDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is greater than or equal to DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMaxArrivalDays.greaterThanOrEqual=" + DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is greater than or equal to UPDATED_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMaxArrivalDays.greaterThanOrEqual=" + UPDATED_EXPECTED_MAX_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMaxArrivalDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is less than or equal to DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMaxArrivalDays.lessThanOrEqual=" + DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is less than or equal to SMALLER_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMaxArrivalDays.lessThanOrEqual=" + SMALLER_EXPECTED_MAX_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMaxArrivalDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is less than DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMaxArrivalDays.lessThan=" + DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is less than UPDATED_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMaxArrivalDays.lessThan=" + UPDATED_EXPECTED_MAX_ARRIVAL_DAYS);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByExpectedMaxArrivalDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is greater than DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldNotBeFound("expectedMaxArrivalDays.greaterThan=" + DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS);

        // Get all the deliveryMethodsList where expectedMaxArrivalDays is greater than SMALLER_EXPECTED_MAX_ARRIVAL_DAYS
        defaultDeliveryMethodsShouldBeFound("expectedMaxArrivalDays.greaterThan=" + SMALLER_EXPECTED_MAX_ARRIVAL_DAYS);
    }


    @Test
    @Transactional
    public void getAllDeliveryMethodsByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultDeliveryMethodsShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the deliveryMethodsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultDeliveryMethodsShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultDeliveryMethodsShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the deliveryMethodsList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultDeliveryMethodsShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultDeliveryMethodsShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the deliveryMethodsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultDeliveryMethodsShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where activeInd is not null
        defaultDeliveryMethodsShouldBeFound("activeInd.specified=true");

        // Get all the deliveryMethodsList where activeInd is null
        defaultDeliveryMethodsShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDefaultIndIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where defaultInd equals to DEFAULT_DEFAULT_IND
        defaultDeliveryMethodsShouldBeFound("defaultInd.equals=" + DEFAULT_DEFAULT_IND);

        // Get all the deliveryMethodsList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultDeliveryMethodsShouldNotBeFound("defaultInd.equals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDefaultIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where defaultInd not equals to DEFAULT_DEFAULT_IND
        defaultDeliveryMethodsShouldNotBeFound("defaultInd.notEquals=" + DEFAULT_DEFAULT_IND);

        // Get all the deliveryMethodsList where defaultInd not equals to UPDATED_DEFAULT_IND
        defaultDeliveryMethodsShouldBeFound("defaultInd.notEquals=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDefaultIndIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where defaultInd in DEFAULT_DEFAULT_IND or UPDATED_DEFAULT_IND
        defaultDeliveryMethodsShouldBeFound("defaultInd.in=" + DEFAULT_DEFAULT_IND + "," + UPDATED_DEFAULT_IND);

        // Get all the deliveryMethodsList where defaultInd equals to UPDATED_DEFAULT_IND
        defaultDeliveryMethodsShouldNotBeFound("defaultInd.in=" + UPDATED_DEFAULT_IND);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDefaultIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where defaultInd is not null
        defaultDeliveryMethodsShouldBeFound("defaultInd.specified=true");

        // Get all the deliveryMethodsList where defaultInd is null
        defaultDeliveryMethodsShouldNotBeFound("defaultInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDeliveryNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where deliveryNote equals to DEFAULT_DELIVERY_NOTE
        defaultDeliveryMethodsShouldBeFound("deliveryNote.equals=" + DEFAULT_DELIVERY_NOTE);

        // Get all the deliveryMethodsList where deliveryNote equals to UPDATED_DELIVERY_NOTE
        defaultDeliveryMethodsShouldNotBeFound("deliveryNote.equals=" + UPDATED_DELIVERY_NOTE);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDeliveryNoteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where deliveryNote not equals to DEFAULT_DELIVERY_NOTE
        defaultDeliveryMethodsShouldNotBeFound("deliveryNote.notEquals=" + DEFAULT_DELIVERY_NOTE);

        // Get all the deliveryMethodsList where deliveryNote not equals to UPDATED_DELIVERY_NOTE
        defaultDeliveryMethodsShouldBeFound("deliveryNote.notEquals=" + UPDATED_DELIVERY_NOTE);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDeliveryNoteIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where deliveryNote in DEFAULT_DELIVERY_NOTE or UPDATED_DELIVERY_NOTE
        defaultDeliveryMethodsShouldBeFound("deliveryNote.in=" + DEFAULT_DELIVERY_NOTE + "," + UPDATED_DELIVERY_NOTE);

        // Get all the deliveryMethodsList where deliveryNote equals to UPDATED_DELIVERY_NOTE
        defaultDeliveryMethodsShouldNotBeFound("deliveryNote.in=" + UPDATED_DELIVERY_NOTE);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDeliveryNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where deliveryNote is not null
        defaultDeliveryMethodsShouldBeFound("deliveryNote.specified=true");

        // Get all the deliveryMethodsList where deliveryNote is null
        defaultDeliveryMethodsShouldNotBeFound("deliveryNote.specified=false");
    }
                @Test
    @Transactional
    public void getAllDeliveryMethodsByDeliveryNoteContainsSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where deliveryNote contains DEFAULT_DELIVERY_NOTE
        defaultDeliveryMethodsShouldBeFound("deliveryNote.contains=" + DEFAULT_DELIVERY_NOTE);

        // Get all the deliveryMethodsList where deliveryNote contains UPDATED_DELIVERY_NOTE
        defaultDeliveryMethodsShouldNotBeFound("deliveryNote.contains=" + UPDATED_DELIVERY_NOTE);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByDeliveryNoteNotContainsSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where deliveryNote does not contain DEFAULT_DELIVERY_NOTE
        defaultDeliveryMethodsShouldNotBeFound("deliveryNote.doesNotContain=" + DEFAULT_DELIVERY_NOTE);

        // Get all the deliveryMethodsList where deliveryNote does not contain UPDATED_DELIVERY_NOTE
        defaultDeliveryMethodsShouldBeFound("deliveryNote.doesNotContain=" + UPDATED_DELIVERY_NOTE);
    }


    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validFrom equals to DEFAULT_VALID_FROM
        defaultDeliveryMethodsShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the deliveryMethodsList where validFrom equals to UPDATED_VALID_FROM
        defaultDeliveryMethodsShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validFrom not equals to DEFAULT_VALID_FROM
        defaultDeliveryMethodsShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the deliveryMethodsList where validFrom not equals to UPDATED_VALID_FROM
        defaultDeliveryMethodsShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultDeliveryMethodsShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the deliveryMethodsList where validFrom equals to UPDATED_VALID_FROM
        defaultDeliveryMethodsShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validFrom is not null
        defaultDeliveryMethodsShouldBeFound("validFrom.specified=true");

        // Get all the deliveryMethodsList where validFrom is null
        defaultDeliveryMethodsShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validTo equals to DEFAULT_VALID_TO
        defaultDeliveryMethodsShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the deliveryMethodsList where validTo equals to UPDATED_VALID_TO
        defaultDeliveryMethodsShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validTo not equals to DEFAULT_VALID_TO
        defaultDeliveryMethodsShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the deliveryMethodsList where validTo not equals to UPDATED_VALID_TO
        defaultDeliveryMethodsShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultDeliveryMethodsShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the deliveryMethodsList where validTo equals to UPDATED_VALID_TO
        defaultDeliveryMethodsShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        // Get all the deliveryMethodsList where validTo is not null
        defaultDeliveryMethodsShouldBeFound("validTo.specified=true");

        // Get all the deliveryMethodsList where validTo is null
        defaultDeliveryMethodsShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllDeliveryMethodsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        deliveryMethods.addSupplier(supplier);
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);
        Long supplierId = supplier.getId();

        // Get all the deliveryMethodsList where supplier equals to supplierId
        defaultDeliveryMethodsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the deliveryMethodsList where supplier equals to supplierId + 1
        defaultDeliveryMethodsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDeliveryMethodsShouldBeFound(String filter) throws Exception {
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(deliveryMethods.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].thirdPartyName").value(hasItem(DEFAULT_THIRD_PARTY_NAME)))
            .andExpect(jsonPath("$.[*].expectedMinArrivalDays").value(hasItem(DEFAULT_EXPECTED_MIN_ARRIVAL_DAYS)))
            .andExpect(jsonPath("$.[*].expectedMaxArrivalDays").value(hasItem(DEFAULT_EXPECTED_MAX_ARRIVAL_DAYS)))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultInd").value(hasItem(DEFAULT_DEFAULT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].deliveryNote").value(hasItem(DEFAULT_DELIVERY_NOTE)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDeliveryMethodsShouldNotBeFound(String filter) throws Exception {
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDeliveryMethods() throws Exception {
        // Get the deliveryMethods
        restDeliveryMethodsMockMvc.perform(get("/api/delivery-methods/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        int databaseSizeBeforeUpdate = deliveryMethodsRepository.findAll().size();

        // Update the deliveryMethods
        DeliveryMethods updatedDeliveryMethods = deliveryMethodsRepository.findById(deliveryMethods.getId()).get();
        // Disconnect from session so that the updates on updatedDeliveryMethods are not directly saved in db
        em.detach(updatedDeliveryMethods);
        updatedDeliveryMethods
            .name(UPDATED_NAME)
            .thirdPartyName(UPDATED_THIRD_PARTY_NAME)
            .expectedMinArrivalDays(UPDATED_EXPECTED_MIN_ARRIVAL_DAYS)
            .expectedMaxArrivalDays(UPDATED_EXPECTED_MAX_ARRIVAL_DAYS)
            .activeInd(UPDATED_ACTIVE_IND)
            .defaultInd(UPDATED_DEFAULT_IND)
            .deliveryNote(UPDATED_DELIVERY_NOTE)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(updatedDeliveryMethods);

        restDeliveryMethodsMockMvc.perform(put("/api/delivery-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isOk());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeUpdate);
        DeliveryMethods testDeliveryMethods = deliveryMethodsList.get(deliveryMethodsList.size() - 1);
        assertThat(testDeliveryMethods.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDeliveryMethods.getThirdPartyName()).isEqualTo(UPDATED_THIRD_PARTY_NAME);
        assertThat(testDeliveryMethods.getExpectedMinArrivalDays()).isEqualTo(UPDATED_EXPECTED_MIN_ARRIVAL_DAYS);
        assertThat(testDeliveryMethods.getExpectedMaxArrivalDays()).isEqualTo(UPDATED_EXPECTED_MAX_ARRIVAL_DAYS);
        assertThat(testDeliveryMethods.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testDeliveryMethods.isDefaultInd()).isEqualTo(UPDATED_DEFAULT_IND);
        assertThat(testDeliveryMethods.getDeliveryNote()).isEqualTo(UPDATED_DELIVERY_NOTE);
        assertThat(testDeliveryMethods.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testDeliveryMethods.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingDeliveryMethods() throws Exception {
        int databaseSizeBeforeUpdate = deliveryMethodsRepository.findAll().size();

        // Create the DeliveryMethods
        DeliveryMethodsDTO deliveryMethodsDTO = deliveryMethodsMapper.toDto(deliveryMethods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeliveryMethodsMockMvc.perform(put("/api/delivery-methods").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(deliveryMethodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DeliveryMethods in the database
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDeliveryMethods() throws Exception {
        // Initialize the database
        deliveryMethodsRepository.saveAndFlush(deliveryMethods);

        int databaseSizeBeforeDelete = deliveryMethodsRepository.findAll().size();

        // Delete the deliveryMethods
        restDeliveryMethodsMockMvc.perform(delete("/api/delivery-methods/{id}", deliveryMethods.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DeliveryMethods> deliveryMethodsList = deliveryMethodsRepository.findAll();
        assertThat(deliveryMethodsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
