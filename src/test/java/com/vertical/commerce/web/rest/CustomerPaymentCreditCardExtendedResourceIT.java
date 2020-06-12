package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CustomerPaymentCreditCardExtended;
import com.vertical.commerce.repository.CustomerPaymentCreditCardExtendedRepository;
import com.vertical.commerce.service.CustomerPaymentCreditCardExtendedService;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentCreditCardExtendedMapper;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardExtendedCriteria;
import com.vertical.commerce.service.CustomerPaymentCreditCardExtendedQueryService;

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
 * Integration tests for the {@link CustomerPaymentCreditCardExtendedResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPaymentCreditCardExtendedResourceIT {

    private static final String DEFAULT_ERROR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITE_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITE_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerPaymentCreditCardExtendedRepository customerPaymentCreditCardExtendedRepository;

    @Autowired
    private CustomerPaymentCreditCardExtendedMapper customerPaymentCreditCardExtendedMapper;

    @Autowired
    private CustomerPaymentCreditCardExtendedService customerPaymentCreditCardExtendedService;

    @Autowired
    private CustomerPaymentCreditCardExtendedQueryService customerPaymentCreditCardExtendedQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPaymentCreditCardExtendedMockMvc;

    private CustomerPaymentCreditCardExtended customerPaymentCreditCardExtended;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentCreditCardExtended createEntity(EntityManager em) {
        CustomerPaymentCreditCardExtended customerPaymentCreditCardExtended = new CustomerPaymentCreditCardExtended()
            .errorCode(DEFAULT_ERROR_CODE)
            .errorMessage(DEFAULT_ERROR_MESSAGE)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditeWhen(DEFAULT_LAST_EDITE_WHEN);
        return customerPaymentCreditCardExtended;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentCreditCardExtended createUpdatedEntity(EntityManager em) {
        CustomerPaymentCreditCardExtended customerPaymentCreditCardExtended = new CustomerPaymentCreditCardExtended()
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditeWhen(UPDATED_LAST_EDITE_WHEN);
        return customerPaymentCreditCardExtended;
    }

    @BeforeEach
    public void initTest() {
        customerPaymentCreditCardExtended = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPaymentCreditCardExtended() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentCreditCardExtendedRepository.findAll().size();
        // Create the CustomerPaymentCreditCardExtended
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO = customerPaymentCreditCardExtendedMapper.toDto(customerPaymentCreditCardExtended);
        restCustomerPaymentCreditCardExtendedMockMvc.perform(post("/api/customer-payment-credit-card-extendeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardExtendedDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerPaymentCreditCardExtended in the database
        List<CustomerPaymentCreditCardExtended> customerPaymentCreditCardExtendedList = customerPaymentCreditCardExtendedRepository.findAll();
        assertThat(customerPaymentCreditCardExtendedList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPaymentCreditCardExtended testCustomerPaymentCreditCardExtended = customerPaymentCreditCardExtendedList.get(customerPaymentCreditCardExtendedList.size() - 1);
        assertThat(testCustomerPaymentCreditCardExtended.getErrorCode()).isEqualTo(DEFAULT_ERROR_CODE);
        assertThat(testCustomerPaymentCreditCardExtended.getErrorMessage()).isEqualTo(DEFAULT_ERROR_MESSAGE);
        assertThat(testCustomerPaymentCreditCardExtended.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testCustomerPaymentCreditCardExtended.getLastEditeWhen()).isEqualTo(DEFAULT_LAST_EDITE_WHEN);
    }

    @Test
    @Transactional
    public void createCustomerPaymentCreditCardExtendedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentCreditCardExtendedRepository.findAll().size();

        // Create the CustomerPaymentCreditCardExtended with an existing ID
        customerPaymentCreditCardExtended.setId(1L);
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO = customerPaymentCreditCardExtendedMapper.toDto(customerPaymentCreditCardExtended);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPaymentCreditCardExtendedMockMvc.perform(post("/api/customer-payment-credit-card-extendeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardExtendedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentCreditCardExtended in the database
        List<CustomerPaymentCreditCardExtended> customerPaymentCreditCardExtendedList = customerPaymentCreditCardExtendedRepository.findAll();
        assertThat(customerPaymentCreditCardExtendedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkErrorCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardExtendedRepository.findAll().size();
        // set the field null
        customerPaymentCreditCardExtended.setErrorCode(null);

        // Create the CustomerPaymentCreditCardExtended, which fails.
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO = customerPaymentCreditCardExtendedMapper.toDto(customerPaymentCreditCardExtended);


        restCustomerPaymentCreditCardExtendedMockMvc.perform(post("/api/customer-payment-credit-card-extendeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardExtendedDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCardExtended> customerPaymentCreditCardExtendedList = customerPaymentCreditCardExtendedRepository.findAll();
        assertThat(customerPaymentCreditCardExtendedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardExtendedRepository.findAll().size();
        // set the field null
        customerPaymentCreditCardExtended.setLastEditedBy(null);

        // Create the CustomerPaymentCreditCardExtended, which fails.
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO = customerPaymentCreditCardExtendedMapper.toDto(customerPaymentCreditCardExtended);


        restCustomerPaymentCreditCardExtendedMockMvc.perform(post("/api/customer-payment-credit-card-extendeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardExtendedDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCardExtended> customerPaymentCreditCardExtendedList = customerPaymentCreditCardExtendedRepository.findAll();
        assertThat(customerPaymentCreditCardExtendedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditeWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardExtendedRepository.findAll().size();
        // set the field null
        customerPaymentCreditCardExtended.setLastEditeWhen(null);

        // Create the CustomerPaymentCreditCardExtended, which fails.
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO = customerPaymentCreditCardExtendedMapper.toDto(customerPaymentCreditCardExtended);


        restCustomerPaymentCreditCardExtendedMockMvc.perform(post("/api/customer-payment-credit-card-extendeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardExtendedDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCardExtended> customerPaymentCreditCardExtendedList = customerPaymentCreditCardExtendedRepository.findAll();
        assertThat(customerPaymentCreditCardExtendedList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendeds() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList
        restCustomerPaymentCreditCardExtendedMockMvc.perform(get("/api/customer-payment-credit-card-extendeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentCreditCardExtended.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE)))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditeWhen").value(hasItem(DEFAULT_LAST_EDITE_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerPaymentCreditCardExtended() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get the customerPaymentCreditCardExtended
        restCustomerPaymentCreditCardExtendedMockMvc.perform(get("/api/customer-payment-credit-card-extendeds/{id}", customerPaymentCreditCardExtended.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPaymentCreditCardExtended.getId().intValue()))
            .andExpect(jsonPath("$.errorCode").value(DEFAULT_ERROR_CODE))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditeWhen").value(DEFAULT_LAST_EDITE_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getCustomerPaymentCreditCardExtendedsByIdFiltering() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        Long id = customerPaymentCreditCardExtended.getId();

        defaultCustomerPaymentCreditCardExtendedShouldBeFound("id.equals=" + id);
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerPaymentCreditCardExtendedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerPaymentCreditCardExtendedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorCode equals to DEFAULT_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorCode.equals=" + DEFAULT_ERROR_CODE);

        // Get all the customerPaymentCreditCardExtendedList where errorCode equals to UPDATED_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorCode.equals=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorCode not equals to DEFAULT_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorCode.notEquals=" + DEFAULT_ERROR_CODE);

        // Get all the customerPaymentCreditCardExtendedList where errorCode not equals to UPDATED_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorCode.notEquals=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorCode in DEFAULT_ERROR_CODE or UPDATED_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorCode.in=" + DEFAULT_ERROR_CODE + "," + UPDATED_ERROR_CODE);

        // Get all the customerPaymentCreditCardExtendedList where errorCode equals to UPDATED_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorCode.in=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorCode is not null
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorCode.specified=true");

        // Get all the customerPaymentCreditCardExtendedList where errorCode is null
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorCodeContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorCode contains DEFAULT_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorCode.contains=" + DEFAULT_ERROR_CODE);

        // Get all the customerPaymentCreditCardExtendedList where errorCode contains UPDATED_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorCode.contains=" + UPDATED_ERROR_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorCode does not contain DEFAULT_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorCode.doesNotContain=" + DEFAULT_ERROR_CODE);

        // Get all the customerPaymentCreditCardExtendedList where errorCode does not contain UPDATED_ERROR_CODE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorCode.doesNotContain=" + UPDATED_ERROR_CODE);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage equals to DEFAULT_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorMessage.equals=" + DEFAULT_ERROR_MESSAGE);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage equals to UPDATED_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorMessage.equals=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage not equals to DEFAULT_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorMessage.notEquals=" + DEFAULT_ERROR_MESSAGE);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage not equals to UPDATED_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorMessage.notEquals=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorMessageIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage in DEFAULT_ERROR_MESSAGE or UPDATED_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorMessage.in=" + DEFAULT_ERROR_MESSAGE + "," + UPDATED_ERROR_MESSAGE);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage equals to UPDATED_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorMessage.in=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage is not null
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorMessage.specified=true");

        // Get all the customerPaymentCreditCardExtendedList where errorMessage is null
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorMessage.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorMessageContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage contains DEFAULT_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorMessage.contains=" + DEFAULT_ERROR_MESSAGE);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage contains UPDATED_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorMessage.contains=" + UPDATED_ERROR_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByErrorMessageNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage does not contain DEFAULT_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("errorMessage.doesNotContain=" + DEFAULT_ERROR_MESSAGE);

        // Get all the customerPaymentCreditCardExtendedList where errorMessage does not contain UPDATED_ERROR_MESSAGE
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("errorMessage.doesNotContain=" + UPDATED_ERROR_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy is not null
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditedBy.specified=true");

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy is null
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardExtendedList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditeWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditeWhen equals to DEFAULT_LAST_EDITE_WHEN
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditeWhen.equals=" + DEFAULT_LAST_EDITE_WHEN);

        // Get all the customerPaymentCreditCardExtendedList where lastEditeWhen equals to UPDATED_LAST_EDITE_WHEN
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditeWhen.equals=" + UPDATED_LAST_EDITE_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditeWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditeWhen not equals to DEFAULT_LAST_EDITE_WHEN
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditeWhen.notEquals=" + DEFAULT_LAST_EDITE_WHEN);

        // Get all the customerPaymentCreditCardExtendedList where lastEditeWhen not equals to UPDATED_LAST_EDITE_WHEN
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditeWhen.notEquals=" + UPDATED_LAST_EDITE_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditeWhenIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditeWhen in DEFAULT_LAST_EDITE_WHEN or UPDATED_LAST_EDITE_WHEN
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditeWhen.in=" + DEFAULT_LAST_EDITE_WHEN + "," + UPDATED_LAST_EDITE_WHEN);

        // Get all the customerPaymentCreditCardExtendedList where lastEditeWhen equals to UPDATED_LAST_EDITE_WHEN
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditeWhen.in=" + UPDATED_LAST_EDITE_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardExtendedsByLastEditeWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        // Get all the customerPaymentCreditCardExtendedList where lastEditeWhen is not null
        defaultCustomerPaymentCreditCardExtendedShouldBeFound("lastEditeWhen.specified=true");

        // Get all the customerPaymentCreditCardExtendedList where lastEditeWhen is null
        defaultCustomerPaymentCreditCardExtendedShouldNotBeFound("lastEditeWhen.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerPaymentCreditCardExtendedShouldBeFound(String filter) throws Exception {
        restCustomerPaymentCreditCardExtendedMockMvc.perform(get("/api/customer-payment-credit-card-extendeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentCreditCardExtended.getId().intValue())))
            .andExpect(jsonPath("$.[*].errorCode").value(hasItem(DEFAULT_ERROR_CODE)))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditeWhen").value(hasItem(DEFAULT_LAST_EDITE_WHEN.toString())));

        // Check, that the count call also returns 1
        restCustomerPaymentCreditCardExtendedMockMvc.perform(get("/api/customer-payment-credit-card-extendeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerPaymentCreditCardExtendedShouldNotBeFound(String filter) throws Exception {
        restCustomerPaymentCreditCardExtendedMockMvc.perform(get("/api/customer-payment-credit-card-extendeds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerPaymentCreditCardExtendedMockMvc.perform(get("/api/customer-payment-credit-card-extendeds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerPaymentCreditCardExtended() throws Exception {
        // Get the customerPaymentCreditCardExtended
        restCustomerPaymentCreditCardExtendedMockMvc.perform(get("/api/customer-payment-credit-card-extendeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPaymentCreditCardExtended() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        int databaseSizeBeforeUpdate = customerPaymentCreditCardExtendedRepository.findAll().size();

        // Update the customerPaymentCreditCardExtended
        CustomerPaymentCreditCardExtended updatedCustomerPaymentCreditCardExtended = customerPaymentCreditCardExtendedRepository.findById(customerPaymentCreditCardExtended.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPaymentCreditCardExtended are not directly saved in db
        em.detach(updatedCustomerPaymentCreditCardExtended);
        updatedCustomerPaymentCreditCardExtended
            .errorCode(UPDATED_ERROR_CODE)
            .errorMessage(UPDATED_ERROR_MESSAGE)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditeWhen(UPDATED_LAST_EDITE_WHEN);
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO = customerPaymentCreditCardExtendedMapper.toDto(updatedCustomerPaymentCreditCardExtended);

        restCustomerPaymentCreditCardExtendedMockMvc.perform(put("/api/customer-payment-credit-card-extendeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardExtendedDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerPaymentCreditCardExtended in the database
        List<CustomerPaymentCreditCardExtended> customerPaymentCreditCardExtendedList = customerPaymentCreditCardExtendedRepository.findAll();
        assertThat(customerPaymentCreditCardExtendedList).hasSize(databaseSizeBeforeUpdate);
        CustomerPaymentCreditCardExtended testCustomerPaymentCreditCardExtended = customerPaymentCreditCardExtendedList.get(customerPaymentCreditCardExtendedList.size() - 1);
        assertThat(testCustomerPaymentCreditCardExtended.getErrorCode()).isEqualTo(UPDATED_ERROR_CODE);
        assertThat(testCustomerPaymentCreditCardExtended.getErrorMessage()).isEqualTo(UPDATED_ERROR_MESSAGE);
        assertThat(testCustomerPaymentCreditCardExtended.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testCustomerPaymentCreditCardExtended.getLastEditeWhen()).isEqualTo(UPDATED_LAST_EDITE_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPaymentCreditCardExtended() throws Exception {
        int databaseSizeBeforeUpdate = customerPaymentCreditCardExtendedRepository.findAll().size();

        // Create the CustomerPaymentCreditCardExtended
        CustomerPaymentCreditCardExtendedDTO customerPaymentCreditCardExtendedDTO = customerPaymentCreditCardExtendedMapper.toDto(customerPaymentCreditCardExtended);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPaymentCreditCardExtendedMockMvc.perform(put("/api/customer-payment-credit-card-extendeds").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardExtendedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentCreditCardExtended in the database
        List<CustomerPaymentCreditCardExtended> customerPaymentCreditCardExtendedList = customerPaymentCreditCardExtendedRepository.findAll();
        assertThat(customerPaymentCreditCardExtendedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPaymentCreditCardExtended() throws Exception {
        // Initialize the database
        customerPaymentCreditCardExtendedRepository.saveAndFlush(customerPaymentCreditCardExtended);

        int databaseSizeBeforeDelete = customerPaymentCreditCardExtendedRepository.findAll().size();

        // Delete the customerPaymentCreditCardExtended
        restCustomerPaymentCreditCardExtendedMockMvc.perform(delete("/api/customer-payment-credit-card-extendeds/{id}", customerPaymentCreditCardExtended.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPaymentCreditCardExtended> customerPaymentCreditCardExtendedList = customerPaymentCreditCardExtendedRepository.findAll();
        assertThat(customerPaymentCreditCardExtendedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
