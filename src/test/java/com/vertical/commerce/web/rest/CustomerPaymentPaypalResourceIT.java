package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CustomerPaymentPaypal;
import com.vertical.commerce.domain.CustomerPayment;
import com.vertical.commerce.domain.Currency;
import com.vertical.commerce.repository.CustomerPaymentPaypalRepository;
import com.vertical.commerce.service.CustomerPaymentPaypalService;
import com.vertical.commerce.service.dto.CustomerPaymentPaypalDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentPaypalMapper;
import com.vertical.commerce.service.dto.CustomerPaymentPaypalCriteria;
import com.vertical.commerce.service.CustomerPaymentPaypalQueryService;

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
 * Integration tests for the {@link CustomerPaymentPaypalResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPaymentPaypalResourceIT {

    private static final String DEFAULT_PAYPAL_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_PAYPAL_ACCOUNT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_RESPONSE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_APPROVAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONSE_DATA = "AAAAAAAAAA";
    private static final String UPDATED_RESPONSE_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerPaymentPaypalRepository customerPaymentPaypalRepository;

    @Autowired
    private CustomerPaymentPaypalMapper customerPaymentPaypalMapper;

    @Autowired
    private CustomerPaymentPaypalService customerPaymentPaypalService;

    @Autowired
    private CustomerPaymentPaypalQueryService customerPaymentPaypalQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPaymentPaypalMockMvc;

    private CustomerPaymentPaypal customerPaymentPaypal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentPaypal createEntity(EntityManager em) {
        CustomerPaymentPaypal customerPaymentPaypal = new CustomerPaymentPaypal()
            .paypalAccount(DEFAULT_PAYPAL_ACCOUNT)
            .amount(DEFAULT_AMOUNT)
            .responseCode(DEFAULT_RESPONSE_CODE)
            .approvalCode(DEFAULT_APPROVAL_CODE)
            .responseData(DEFAULT_RESPONSE_DATA)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return customerPaymentPaypal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentPaypal createUpdatedEntity(EntityManager em) {
        CustomerPaymentPaypal customerPaymentPaypal = new CustomerPaymentPaypal()
            .paypalAccount(UPDATED_PAYPAL_ACCOUNT)
            .amount(UPDATED_AMOUNT)
            .responseCode(UPDATED_RESPONSE_CODE)
            .approvalCode(UPDATED_APPROVAL_CODE)
            .responseData(UPDATED_RESPONSE_DATA)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return customerPaymentPaypal;
    }

    @BeforeEach
    public void initTest() {
        customerPaymentPaypal = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPaymentPaypal() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentPaypalRepository.findAll().size();
        // Create the CustomerPaymentPaypal
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);
        restCustomerPaymentPaypalMockMvc.perform(post("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerPaymentPaypal in the database
        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPaymentPaypal testCustomerPaymentPaypal = customerPaymentPaypalList.get(customerPaymentPaypalList.size() - 1);
        assertThat(testCustomerPaymentPaypal.getPaypalAccount()).isEqualTo(DEFAULT_PAYPAL_ACCOUNT);
        assertThat(testCustomerPaymentPaypal.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCustomerPaymentPaypal.getResponseCode()).isEqualTo(DEFAULT_RESPONSE_CODE);
        assertThat(testCustomerPaymentPaypal.getApprovalCode()).isEqualTo(DEFAULT_APPROVAL_CODE);
        assertThat(testCustomerPaymentPaypal.getResponseData()).isEqualTo(DEFAULT_RESPONSE_DATA);
        assertThat(testCustomerPaymentPaypal.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testCustomerPaymentPaypal.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createCustomerPaymentPaypalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentPaypalRepository.findAll().size();

        // Create the CustomerPaymentPaypal with an existing ID
        customerPaymentPaypal.setId(1L);
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPaymentPaypalMockMvc.perform(post("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentPaypal in the database
        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPaypalAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentPaypalRepository.findAll().size();
        // set the field null
        customerPaymentPaypal.setPaypalAccount(null);

        // Create the CustomerPaymentPaypal, which fails.
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);


        restCustomerPaymentPaypalMockMvc.perform(post("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentPaypalRepository.findAll().size();
        // set the field null
        customerPaymentPaypal.setAmount(null);

        // Create the CustomerPaymentPaypal, which fails.
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);


        restCustomerPaymentPaypalMockMvc.perform(post("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponseCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentPaypalRepository.findAll().size();
        // set the field null
        customerPaymentPaypal.setResponseCode(null);

        // Create the CustomerPaymentPaypal, which fails.
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);


        restCustomerPaymentPaypalMockMvc.perform(post("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApprovalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentPaypalRepository.findAll().size();
        // set the field null
        customerPaymentPaypal.setApprovalCode(null);

        // Create the CustomerPaymentPaypal, which fails.
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);


        restCustomerPaymentPaypalMockMvc.perform(post("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentPaypalRepository.findAll().size();
        // set the field null
        customerPaymentPaypal.setLastEditedBy(null);

        // Create the CustomerPaymentPaypal, which fails.
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);


        restCustomerPaymentPaypalMockMvc.perform(post("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentPaypalRepository.findAll().size();
        // set the field null
        customerPaymentPaypal.setLastEditedWhen(null);

        // Create the CustomerPaymentPaypal, which fails.
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);


        restCustomerPaymentPaypalMockMvc.perform(post("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypals() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList
        restCustomerPaymentPaypalMockMvc.perform(get("/api/customer-payment-paypals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentPaypal.getId().intValue())))
            .andExpect(jsonPath("$.[*].paypalAccount").value(hasItem(DEFAULT_PAYPAL_ACCOUNT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].approvalCode").value(hasItem(DEFAULT_APPROVAL_CODE)))
            .andExpect(jsonPath("$.[*].responseData").value(hasItem(DEFAULT_RESPONSE_DATA.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerPaymentPaypal() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get the customerPaymentPaypal
        restCustomerPaymentPaypalMockMvc.perform(get("/api/customer-payment-paypals/{id}", customerPaymentPaypal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPaymentPaypal.getId().intValue()))
            .andExpect(jsonPath("$.paypalAccount").value(DEFAULT_PAYPAL_ACCOUNT))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.responseCode").value(DEFAULT_RESPONSE_CODE))
            .andExpect(jsonPath("$.approvalCode").value(DEFAULT_APPROVAL_CODE))
            .andExpect(jsonPath("$.responseData").value(DEFAULT_RESPONSE_DATA.toString()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getCustomerPaymentPaypalsByIdFiltering() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        Long id = customerPaymentPaypal.getId();

        defaultCustomerPaymentPaypalShouldBeFound("id.equals=" + id);
        defaultCustomerPaymentPaypalShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerPaymentPaypalShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerPaymentPaypalShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerPaymentPaypalShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerPaymentPaypalShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByPaypalAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where paypalAccount equals to DEFAULT_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldBeFound("paypalAccount.equals=" + DEFAULT_PAYPAL_ACCOUNT);

        // Get all the customerPaymentPaypalList where paypalAccount equals to UPDATED_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("paypalAccount.equals=" + UPDATED_PAYPAL_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByPaypalAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where paypalAccount not equals to DEFAULT_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("paypalAccount.notEquals=" + DEFAULT_PAYPAL_ACCOUNT);

        // Get all the customerPaymentPaypalList where paypalAccount not equals to UPDATED_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldBeFound("paypalAccount.notEquals=" + UPDATED_PAYPAL_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByPaypalAccountIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where paypalAccount in DEFAULT_PAYPAL_ACCOUNT or UPDATED_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldBeFound("paypalAccount.in=" + DEFAULT_PAYPAL_ACCOUNT + "," + UPDATED_PAYPAL_ACCOUNT);

        // Get all the customerPaymentPaypalList where paypalAccount equals to UPDATED_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("paypalAccount.in=" + UPDATED_PAYPAL_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByPaypalAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where paypalAccount is not null
        defaultCustomerPaymentPaypalShouldBeFound("paypalAccount.specified=true");

        // Get all the customerPaymentPaypalList where paypalAccount is null
        defaultCustomerPaymentPaypalShouldNotBeFound("paypalAccount.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByPaypalAccountContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where paypalAccount contains DEFAULT_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldBeFound("paypalAccount.contains=" + DEFAULT_PAYPAL_ACCOUNT);

        // Get all the customerPaymentPaypalList where paypalAccount contains UPDATED_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("paypalAccount.contains=" + UPDATED_PAYPAL_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByPaypalAccountNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where paypalAccount does not contain DEFAULT_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("paypalAccount.doesNotContain=" + DEFAULT_PAYPAL_ACCOUNT);

        // Get all the customerPaymentPaypalList where paypalAccount does not contain UPDATED_PAYPAL_ACCOUNT
        defaultCustomerPaymentPaypalShouldBeFound("paypalAccount.doesNotContain=" + UPDATED_PAYPAL_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where amount equals to DEFAULT_AMOUNT
        defaultCustomerPaymentPaypalShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentPaypalList where amount equals to UPDATED_AMOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where amount not equals to DEFAULT_AMOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentPaypalList where amount not equals to UPDATED_AMOUNT
        defaultCustomerPaymentPaypalShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCustomerPaymentPaypalShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the customerPaymentPaypalList where amount equals to UPDATED_AMOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where amount is not null
        defaultCustomerPaymentPaypalShouldBeFound("amount.specified=true");

        // Get all the customerPaymentPaypalList where amount is null
        defaultCustomerPaymentPaypalShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCustomerPaymentPaypalShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentPaypalList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCustomerPaymentPaypalShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentPaypalList where amount is less than or equal to SMALLER_AMOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where amount is less than DEFAULT_AMOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentPaypalList where amount is less than UPDATED_AMOUNT
        defaultCustomerPaymentPaypalShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where amount is greater than DEFAULT_AMOUNT
        defaultCustomerPaymentPaypalShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentPaypalList where amount is greater than SMALLER_AMOUNT
        defaultCustomerPaymentPaypalShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByResponseCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where responseCode equals to DEFAULT_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldBeFound("responseCode.equals=" + DEFAULT_RESPONSE_CODE);

        // Get all the customerPaymentPaypalList where responseCode equals to UPDATED_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("responseCode.equals=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByResponseCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where responseCode not equals to DEFAULT_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("responseCode.notEquals=" + DEFAULT_RESPONSE_CODE);

        // Get all the customerPaymentPaypalList where responseCode not equals to UPDATED_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldBeFound("responseCode.notEquals=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByResponseCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where responseCode in DEFAULT_RESPONSE_CODE or UPDATED_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldBeFound("responseCode.in=" + DEFAULT_RESPONSE_CODE + "," + UPDATED_RESPONSE_CODE);

        // Get all the customerPaymentPaypalList where responseCode equals to UPDATED_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("responseCode.in=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByResponseCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where responseCode is not null
        defaultCustomerPaymentPaypalShouldBeFound("responseCode.specified=true");

        // Get all the customerPaymentPaypalList where responseCode is null
        defaultCustomerPaymentPaypalShouldNotBeFound("responseCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByResponseCodeContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where responseCode contains DEFAULT_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldBeFound("responseCode.contains=" + DEFAULT_RESPONSE_CODE);

        // Get all the customerPaymentPaypalList where responseCode contains UPDATED_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("responseCode.contains=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByResponseCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where responseCode does not contain DEFAULT_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("responseCode.doesNotContain=" + DEFAULT_RESPONSE_CODE);

        // Get all the customerPaymentPaypalList where responseCode does not contain UPDATED_RESPONSE_CODE
        defaultCustomerPaymentPaypalShouldBeFound("responseCode.doesNotContain=" + UPDATED_RESPONSE_CODE);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByApprovalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where approvalCode equals to DEFAULT_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldBeFound("approvalCode.equals=" + DEFAULT_APPROVAL_CODE);

        // Get all the customerPaymentPaypalList where approvalCode equals to UPDATED_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("approvalCode.equals=" + UPDATED_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByApprovalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where approvalCode not equals to DEFAULT_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("approvalCode.notEquals=" + DEFAULT_APPROVAL_CODE);

        // Get all the customerPaymentPaypalList where approvalCode not equals to UPDATED_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldBeFound("approvalCode.notEquals=" + UPDATED_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByApprovalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where approvalCode in DEFAULT_APPROVAL_CODE or UPDATED_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldBeFound("approvalCode.in=" + DEFAULT_APPROVAL_CODE + "," + UPDATED_APPROVAL_CODE);

        // Get all the customerPaymentPaypalList where approvalCode equals to UPDATED_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("approvalCode.in=" + UPDATED_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByApprovalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where approvalCode is not null
        defaultCustomerPaymentPaypalShouldBeFound("approvalCode.specified=true");

        // Get all the customerPaymentPaypalList where approvalCode is null
        defaultCustomerPaymentPaypalShouldNotBeFound("approvalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByApprovalCodeContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where approvalCode contains DEFAULT_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldBeFound("approvalCode.contains=" + DEFAULT_APPROVAL_CODE);

        // Get all the customerPaymentPaypalList where approvalCode contains UPDATED_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("approvalCode.contains=" + UPDATED_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByApprovalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where approvalCode does not contain DEFAULT_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldNotBeFound("approvalCode.doesNotContain=" + DEFAULT_APPROVAL_CODE);

        // Get all the customerPaymentPaypalList where approvalCode does not contain UPDATED_APPROVAL_CODE
        defaultCustomerPaymentPaypalShouldBeFound("approvalCode.doesNotContain=" + UPDATED_APPROVAL_CODE);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentPaypalList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentPaypalList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the customerPaymentPaypalList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedBy is not null
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedBy.specified=true");

        // Get all the customerPaymentPaypalList where lastEditedBy is null
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentPaypalList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentPaypalList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentPaypalList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentPaypalList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the customerPaymentPaypalList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        // Get all the customerPaymentPaypalList where lastEditedWhen is not null
        defaultCustomerPaymentPaypalShouldBeFound("lastEditedWhen.specified=true");

        // Get all the customerPaymentPaypalList where lastEditedWhen is null
        defaultCustomerPaymentPaypalShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByCustomerPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);
        CustomerPayment customerPayment = CustomerPaymentResourceIT.createEntity(em);
        em.persist(customerPayment);
        em.flush();
        customerPaymentPaypal.setCustomerPayment(customerPayment);
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);
        Long customerPaymentId = customerPayment.getId();

        // Get all the customerPaymentPaypalList where customerPayment equals to customerPaymentId
        defaultCustomerPaymentPaypalShouldBeFound("customerPaymentId.equals=" + customerPaymentId);

        // Get all the customerPaymentPaypalList where customerPayment equals to customerPaymentId + 1
        defaultCustomerPaymentPaypalShouldNotBeFound("customerPaymentId.equals=" + (customerPaymentId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentPaypalsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);
        Currency currency = CurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        customerPaymentPaypal.setCurrency(currency);
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);
        Long currencyId = currency.getId();

        // Get all the customerPaymentPaypalList where currency equals to currencyId
        defaultCustomerPaymentPaypalShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the customerPaymentPaypalList where currency equals to currencyId + 1
        defaultCustomerPaymentPaypalShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerPaymentPaypalShouldBeFound(String filter) throws Exception {
        restCustomerPaymentPaypalMockMvc.perform(get("/api/customer-payment-paypals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentPaypal.getId().intValue())))
            .andExpect(jsonPath("$.[*].paypalAccount").value(hasItem(DEFAULT_PAYPAL_ACCOUNT)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].approvalCode").value(hasItem(DEFAULT_APPROVAL_CODE)))
            .andExpect(jsonPath("$.[*].responseData").value(hasItem(DEFAULT_RESPONSE_DATA.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restCustomerPaymentPaypalMockMvc.perform(get("/api/customer-payment-paypals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerPaymentPaypalShouldNotBeFound(String filter) throws Exception {
        restCustomerPaymentPaypalMockMvc.perform(get("/api/customer-payment-paypals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerPaymentPaypalMockMvc.perform(get("/api/customer-payment-paypals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerPaymentPaypal() throws Exception {
        // Get the customerPaymentPaypal
        restCustomerPaymentPaypalMockMvc.perform(get("/api/customer-payment-paypals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPaymentPaypal() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        int databaseSizeBeforeUpdate = customerPaymentPaypalRepository.findAll().size();

        // Update the customerPaymentPaypal
        CustomerPaymentPaypal updatedCustomerPaymentPaypal = customerPaymentPaypalRepository.findById(customerPaymentPaypal.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPaymentPaypal are not directly saved in db
        em.detach(updatedCustomerPaymentPaypal);
        updatedCustomerPaymentPaypal
            .paypalAccount(UPDATED_PAYPAL_ACCOUNT)
            .amount(UPDATED_AMOUNT)
            .responseCode(UPDATED_RESPONSE_CODE)
            .approvalCode(UPDATED_APPROVAL_CODE)
            .responseData(UPDATED_RESPONSE_DATA)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(updatedCustomerPaymentPaypal);

        restCustomerPaymentPaypalMockMvc.perform(put("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerPaymentPaypal in the database
        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeUpdate);
        CustomerPaymentPaypal testCustomerPaymentPaypal = customerPaymentPaypalList.get(customerPaymentPaypalList.size() - 1);
        assertThat(testCustomerPaymentPaypal.getPaypalAccount()).isEqualTo(UPDATED_PAYPAL_ACCOUNT);
        assertThat(testCustomerPaymentPaypal.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCustomerPaymentPaypal.getResponseCode()).isEqualTo(UPDATED_RESPONSE_CODE);
        assertThat(testCustomerPaymentPaypal.getApprovalCode()).isEqualTo(UPDATED_APPROVAL_CODE);
        assertThat(testCustomerPaymentPaypal.getResponseData()).isEqualTo(UPDATED_RESPONSE_DATA);
        assertThat(testCustomerPaymentPaypal.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testCustomerPaymentPaypal.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPaymentPaypal() throws Exception {
        int databaseSizeBeforeUpdate = customerPaymentPaypalRepository.findAll().size();

        // Create the CustomerPaymentPaypal
        CustomerPaymentPaypalDTO customerPaymentPaypalDTO = customerPaymentPaypalMapper.toDto(customerPaymentPaypal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPaymentPaypalMockMvc.perform(put("/api/customer-payment-paypals").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentPaypalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentPaypal in the database
        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPaymentPaypal() throws Exception {
        // Initialize the database
        customerPaymentPaypalRepository.saveAndFlush(customerPaymentPaypal);

        int databaseSizeBeforeDelete = customerPaymentPaypalRepository.findAll().size();

        // Delete the customerPaymentPaypal
        restCustomerPaymentPaypalMockMvc.perform(delete("/api/customer-payment-paypals/{id}", customerPaymentPaypal.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPaymentPaypal> customerPaymentPaypalList = customerPaymentPaypalRepository.findAll();
        assertThat(customerPaymentPaypalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
