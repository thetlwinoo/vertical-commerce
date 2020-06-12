package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CustomerPayment;
import com.vertical.commerce.domain.CustomerTransactions;
import com.vertical.commerce.domain.PaymentMethods;
import com.vertical.commerce.domain.Currency;
import com.vertical.commerce.domain.CurrencyRate;
import com.vertical.commerce.domain.CustomerPaymentCreditCard;
import com.vertical.commerce.domain.CustomerPaymentVoucher;
import com.vertical.commerce.domain.CustomerPaymentBankTransfer;
import com.vertical.commerce.domain.CustomerPaymentPaypal;
import com.vertical.commerce.repository.CustomerPaymentRepository;
import com.vertical.commerce.service.CustomerPaymentService;
import com.vertical.commerce.service.dto.CustomerPaymentDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentMapper;
import com.vertical.commerce.service.dto.CustomerPaymentCriteria;
import com.vertical.commerce.service.CustomerPaymentQueryService;

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
 * Integration tests for the {@link CustomerPaymentResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPaymentResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT_EXCLUDING_TAX = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_EXCLUDING_TAX = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_EXCLUDING_TAX = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TAX_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAX_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAX_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSACTION_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_TRANSACTION_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OUTSTANDING_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_OUTSTANDING_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_OUTSTANDING_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerPaymentRepository customerPaymentRepository;

    @Autowired
    private CustomerPaymentMapper customerPaymentMapper;

    @Autowired
    private CustomerPaymentService customerPaymentService;

    @Autowired
    private CustomerPaymentQueryService customerPaymentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPaymentMockMvc;

    private CustomerPayment customerPayment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPayment createEntity(EntityManager em) {
        CustomerPayment customerPayment = new CustomerPayment()
            .amountExcludingTax(DEFAULT_AMOUNT_EXCLUDING_TAX)
            .taxAmount(DEFAULT_TAX_AMOUNT)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT)
            .outstandingAmount(DEFAULT_OUTSTANDING_AMOUNT)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return customerPayment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPayment createUpdatedEntity(EntityManager em) {
        CustomerPayment customerPayment = new CustomerPayment()
            .amountExcludingTax(UPDATED_AMOUNT_EXCLUDING_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return customerPayment;
    }

    @BeforeEach
    public void initTest() {
        customerPayment = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPayment() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentRepository.findAll().size();
        // Create the CustomerPayment
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(customerPayment);
        restCustomerPaymentMockMvc.perform(post("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerPayment in the database
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPayment testCustomerPayment = customerPaymentList.get(customerPaymentList.size() - 1);
        assertThat(testCustomerPayment.getAmountExcludingTax()).isEqualTo(DEFAULT_AMOUNT_EXCLUDING_TAX);
        assertThat(testCustomerPayment.getTaxAmount()).isEqualTo(DEFAULT_TAX_AMOUNT);
        assertThat(testCustomerPayment.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);
        assertThat(testCustomerPayment.getOutstandingAmount()).isEqualTo(DEFAULT_OUTSTANDING_AMOUNT);
        assertThat(testCustomerPayment.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testCustomerPayment.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createCustomerPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentRepository.findAll().size();

        // Create the CustomerPayment with an existing ID
        customerPayment.setId(1L);
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(customerPayment);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPaymentMockMvc.perform(post("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPayment in the database
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountExcludingTaxIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentRepository.findAll().size();
        // set the field null
        customerPayment.setAmountExcludingTax(null);

        // Create the CustomerPayment, which fails.
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(customerPayment);


        restCustomerPaymentMockMvc.perform(post("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTaxAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentRepository.findAll().size();
        // set the field null
        customerPayment.setTaxAmount(null);

        // Create the CustomerPayment, which fails.
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(customerPayment);


        restCustomerPaymentMockMvc.perform(post("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentRepository.findAll().size();
        // set the field null
        customerPayment.setTransactionAmount(null);

        // Create the CustomerPayment, which fails.
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(customerPayment);


        restCustomerPaymentMockMvc.perform(post("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentRepository.findAll().size();
        // set the field null
        customerPayment.setLastEditedBy(null);

        // Create the CustomerPayment, which fails.
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(customerPayment);


        restCustomerPaymentMockMvc.perform(post("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentRepository.findAll().size();
        // set the field null
        customerPayment.setLastEditedWhen(null);

        // Create the CustomerPayment, which fails.
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(customerPayment);


        restCustomerPaymentMockMvc.perform(post("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerPayments() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountExcludingTax").value(hasItem(DEFAULT_AMOUNT_EXCLUDING_TAX.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].outstandingAmount").value(hasItem(DEFAULT_OUTSTANDING_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerPayment() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get the customerPayment
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments/{id}", customerPayment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPayment.getId().intValue()))
            .andExpect(jsonPath("$.amountExcludingTax").value(DEFAULT_AMOUNT_EXCLUDING_TAX.intValue()))
            .andExpect(jsonPath("$.taxAmount").value(DEFAULT_TAX_AMOUNT.intValue()))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.intValue()))
            .andExpect(jsonPath("$.outstandingAmount").value(DEFAULT_OUTSTANDING_AMOUNT.intValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getCustomerPaymentsByIdFiltering() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        Long id = customerPayment.getId();

        defaultCustomerPaymentShouldBeFound("id.equals=" + id);
        defaultCustomerPaymentShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerPaymentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerPaymentShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerPaymentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerPaymentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByAmountExcludingTaxIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where amountExcludingTax equals to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldBeFound("amountExcludingTax.equals=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerPaymentList where amountExcludingTax equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldNotBeFound("amountExcludingTax.equals=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByAmountExcludingTaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where amountExcludingTax not equals to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldNotBeFound("amountExcludingTax.notEquals=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerPaymentList where amountExcludingTax not equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldBeFound("amountExcludingTax.notEquals=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByAmountExcludingTaxIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where amountExcludingTax in DEFAULT_AMOUNT_EXCLUDING_TAX or UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldBeFound("amountExcludingTax.in=" + DEFAULT_AMOUNT_EXCLUDING_TAX + "," + UPDATED_AMOUNT_EXCLUDING_TAX);

        // Get all the customerPaymentList where amountExcludingTax equals to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldNotBeFound("amountExcludingTax.in=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByAmountExcludingTaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where amountExcludingTax is not null
        defaultCustomerPaymentShouldBeFound("amountExcludingTax.specified=true");

        // Get all the customerPaymentList where amountExcludingTax is null
        defaultCustomerPaymentShouldNotBeFound("amountExcludingTax.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByAmountExcludingTaxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where amountExcludingTax is greater than or equal to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldBeFound("amountExcludingTax.greaterThanOrEqual=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerPaymentList where amountExcludingTax is greater than or equal to UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldNotBeFound("amountExcludingTax.greaterThanOrEqual=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByAmountExcludingTaxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where amountExcludingTax is less than or equal to DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldBeFound("amountExcludingTax.lessThanOrEqual=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerPaymentList where amountExcludingTax is less than or equal to SMALLER_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldNotBeFound("amountExcludingTax.lessThanOrEqual=" + SMALLER_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByAmountExcludingTaxIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where amountExcludingTax is less than DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldNotBeFound("amountExcludingTax.lessThan=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerPaymentList where amountExcludingTax is less than UPDATED_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldBeFound("amountExcludingTax.lessThan=" + UPDATED_AMOUNT_EXCLUDING_TAX);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByAmountExcludingTaxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where amountExcludingTax is greater than DEFAULT_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldNotBeFound("amountExcludingTax.greaterThan=" + DEFAULT_AMOUNT_EXCLUDING_TAX);

        // Get all the customerPaymentList where amountExcludingTax is greater than SMALLER_AMOUNT_EXCLUDING_TAX
        defaultCustomerPaymentShouldBeFound("amountExcludingTax.greaterThan=" + SMALLER_AMOUNT_EXCLUDING_TAX);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByTaxAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where taxAmount equals to DEFAULT_TAX_AMOUNT
        defaultCustomerPaymentShouldBeFound("taxAmount.equals=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerPaymentList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("taxAmount.equals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTaxAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where taxAmount not equals to DEFAULT_TAX_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("taxAmount.notEquals=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerPaymentList where taxAmount not equals to UPDATED_TAX_AMOUNT
        defaultCustomerPaymentShouldBeFound("taxAmount.notEquals=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTaxAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where taxAmount in DEFAULT_TAX_AMOUNT or UPDATED_TAX_AMOUNT
        defaultCustomerPaymentShouldBeFound("taxAmount.in=" + DEFAULT_TAX_AMOUNT + "," + UPDATED_TAX_AMOUNT);

        // Get all the customerPaymentList where taxAmount equals to UPDATED_TAX_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("taxAmount.in=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTaxAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where taxAmount is not null
        defaultCustomerPaymentShouldBeFound("taxAmount.specified=true");

        // Get all the customerPaymentList where taxAmount is null
        defaultCustomerPaymentShouldNotBeFound("taxAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTaxAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where taxAmount is greater than or equal to DEFAULT_TAX_AMOUNT
        defaultCustomerPaymentShouldBeFound("taxAmount.greaterThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerPaymentList where taxAmount is greater than or equal to UPDATED_TAX_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("taxAmount.greaterThanOrEqual=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTaxAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where taxAmount is less than or equal to DEFAULT_TAX_AMOUNT
        defaultCustomerPaymentShouldBeFound("taxAmount.lessThanOrEqual=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerPaymentList where taxAmount is less than or equal to SMALLER_TAX_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("taxAmount.lessThanOrEqual=" + SMALLER_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTaxAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where taxAmount is less than DEFAULT_TAX_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("taxAmount.lessThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerPaymentList where taxAmount is less than UPDATED_TAX_AMOUNT
        defaultCustomerPaymentShouldBeFound("taxAmount.lessThan=" + UPDATED_TAX_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTaxAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where taxAmount is greater than DEFAULT_TAX_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("taxAmount.greaterThan=" + DEFAULT_TAX_AMOUNT);

        // Get all the customerPaymentList where taxAmount is greater than SMALLER_TAX_AMOUNT
        defaultCustomerPaymentShouldBeFound("taxAmount.greaterThan=" + SMALLER_TAX_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where transactionAmount equals to DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldBeFound("transactionAmount.equals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerPaymentList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("transactionAmount.equals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTransactionAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where transactionAmount not equals to DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("transactionAmount.notEquals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerPaymentList where transactionAmount not equals to UPDATED_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldBeFound("transactionAmount.notEquals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where transactionAmount in DEFAULT_TRANSACTION_AMOUNT or UPDATED_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldBeFound("transactionAmount.in=" + DEFAULT_TRANSACTION_AMOUNT + "," + UPDATED_TRANSACTION_AMOUNT);

        // Get all the customerPaymentList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("transactionAmount.in=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where transactionAmount is not null
        defaultCustomerPaymentShouldBeFound("transactionAmount.specified=true");

        // Get all the customerPaymentList where transactionAmount is null
        defaultCustomerPaymentShouldNotBeFound("transactionAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTransactionAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where transactionAmount is greater than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldBeFound("transactionAmount.greaterThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerPaymentList where transactionAmount is greater than or equal to UPDATED_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("transactionAmount.greaterThanOrEqual=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTransactionAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where transactionAmount is less than or equal to DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldBeFound("transactionAmount.lessThanOrEqual=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerPaymentList where transactionAmount is less than or equal to SMALLER_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("transactionAmount.lessThanOrEqual=" + SMALLER_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTransactionAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where transactionAmount is less than DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("transactionAmount.lessThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerPaymentList where transactionAmount is less than UPDATED_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldBeFound("transactionAmount.lessThan=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByTransactionAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where transactionAmount is greater than DEFAULT_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("transactionAmount.greaterThan=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the customerPaymentList where transactionAmount is greater than SMALLER_TRANSACTION_AMOUNT
        defaultCustomerPaymentShouldBeFound("transactionAmount.greaterThan=" + SMALLER_TRANSACTION_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByOutstandingAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where outstandingAmount equals to DEFAULT_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldBeFound("outstandingAmount.equals=" + DEFAULT_OUTSTANDING_AMOUNT);

        // Get all the customerPaymentList where outstandingAmount equals to UPDATED_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("outstandingAmount.equals=" + UPDATED_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByOutstandingAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where outstandingAmount not equals to DEFAULT_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("outstandingAmount.notEquals=" + DEFAULT_OUTSTANDING_AMOUNT);

        // Get all the customerPaymentList where outstandingAmount not equals to UPDATED_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldBeFound("outstandingAmount.notEquals=" + UPDATED_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByOutstandingAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where outstandingAmount in DEFAULT_OUTSTANDING_AMOUNT or UPDATED_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldBeFound("outstandingAmount.in=" + DEFAULT_OUTSTANDING_AMOUNT + "," + UPDATED_OUTSTANDING_AMOUNT);

        // Get all the customerPaymentList where outstandingAmount equals to UPDATED_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("outstandingAmount.in=" + UPDATED_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByOutstandingAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where outstandingAmount is not null
        defaultCustomerPaymentShouldBeFound("outstandingAmount.specified=true");

        // Get all the customerPaymentList where outstandingAmount is null
        defaultCustomerPaymentShouldNotBeFound("outstandingAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByOutstandingAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where outstandingAmount is greater than or equal to DEFAULT_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldBeFound("outstandingAmount.greaterThanOrEqual=" + DEFAULT_OUTSTANDING_AMOUNT);

        // Get all the customerPaymentList where outstandingAmount is greater than or equal to UPDATED_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("outstandingAmount.greaterThanOrEqual=" + UPDATED_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByOutstandingAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where outstandingAmount is less than or equal to DEFAULT_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldBeFound("outstandingAmount.lessThanOrEqual=" + DEFAULT_OUTSTANDING_AMOUNT);

        // Get all the customerPaymentList where outstandingAmount is less than or equal to SMALLER_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("outstandingAmount.lessThanOrEqual=" + SMALLER_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByOutstandingAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where outstandingAmount is less than DEFAULT_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("outstandingAmount.lessThan=" + DEFAULT_OUTSTANDING_AMOUNT);

        // Get all the customerPaymentList where outstandingAmount is less than UPDATED_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldBeFound("outstandingAmount.lessThan=" + UPDATED_OUTSTANDING_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByOutstandingAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where outstandingAmount is greater than DEFAULT_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldNotBeFound("outstandingAmount.greaterThan=" + DEFAULT_OUTSTANDING_AMOUNT);

        // Get all the customerPaymentList where outstandingAmount is greater than SMALLER_OUTSTANDING_AMOUNT
        defaultCustomerPaymentShouldBeFound("outstandingAmount.greaterThan=" + SMALLER_OUTSTANDING_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the customerPaymentList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedBy is not null
        defaultCustomerPaymentShouldBeFound("lastEditedBy.specified=true");

        // Get all the customerPaymentList where lastEditedBy is null
        defaultCustomerPaymentShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the customerPaymentList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        // Get all the customerPaymentList where lastEditedWhen is not null
        defaultCustomerPaymentShouldBeFound("lastEditedWhen.specified=true");

        // Get all the customerPaymentList where lastEditedWhen is null
        defaultCustomerPaymentShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentsByCustomerTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);
        CustomerTransactions customerTransaction = CustomerTransactionsResourceIT.createEntity(em);
        em.persist(customerTransaction);
        em.flush();
        customerPayment.setCustomerTransaction(customerTransaction);
        customerPaymentRepository.saveAndFlush(customerPayment);
        Long customerTransactionId = customerTransaction.getId();

        // Get all the customerPaymentList where customerTransaction equals to customerTransactionId
        defaultCustomerPaymentShouldBeFound("customerTransactionId.equals=" + customerTransactionId);

        // Get all the customerPaymentList where customerTransaction equals to customerTransactionId + 1
        defaultCustomerPaymentShouldNotBeFound("customerTransactionId.equals=" + (customerTransactionId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);
        PaymentMethods paymentMethod = PaymentMethodsResourceIT.createEntity(em);
        em.persist(paymentMethod);
        em.flush();
        customerPayment.setPaymentMethod(paymentMethod);
        customerPaymentRepository.saveAndFlush(customerPayment);
        Long paymentMethodId = paymentMethod.getId();

        // Get all the customerPaymentList where paymentMethod equals to paymentMethodId
        defaultCustomerPaymentShouldBeFound("paymentMethodId.equals=" + paymentMethodId);

        // Get all the customerPaymentList where paymentMethod equals to paymentMethodId + 1
        defaultCustomerPaymentShouldNotBeFound("paymentMethodId.equals=" + (paymentMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);
        Currency currency = CurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        customerPayment.setCurrency(currency);
        customerPaymentRepository.saveAndFlush(customerPayment);
        Long currencyId = currency.getId();

        // Get all the customerPaymentList where currency equals to currencyId
        defaultCustomerPaymentShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the customerPaymentList where currency equals to currencyId + 1
        defaultCustomerPaymentShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByCurrencyRateIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);
        CurrencyRate currencyRate = CurrencyRateResourceIT.createEntity(em);
        em.persist(currencyRate);
        em.flush();
        customerPayment.setCurrencyRate(currencyRate);
        customerPaymentRepository.saveAndFlush(customerPayment);
        Long currencyRateId = currencyRate.getId();

        // Get all the customerPaymentList where currencyRate equals to currencyRateId
        defaultCustomerPaymentShouldBeFound("currencyRateId.equals=" + currencyRateId);

        // Get all the customerPaymentList where currencyRate equals to currencyRateId + 1
        defaultCustomerPaymentShouldNotBeFound("currencyRateId.equals=" + (currencyRateId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByCustomerPaymentCreditCardIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);
        CustomerPaymentCreditCard customerPaymentCreditCard = CustomerPaymentCreditCardResourceIT.createEntity(em);
        em.persist(customerPaymentCreditCard);
        em.flush();
        customerPayment.setCustomerPaymentCreditCard(customerPaymentCreditCard);
        customerPaymentCreditCard.setCustomerPayment(customerPayment);
        customerPaymentRepository.saveAndFlush(customerPayment);
        Long customerPaymentCreditCardId = customerPaymentCreditCard.getId();

        // Get all the customerPaymentList where customerPaymentCreditCard equals to customerPaymentCreditCardId
        defaultCustomerPaymentShouldBeFound("customerPaymentCreditCardId.equals=" + customerPaymentCreditCardId);

        // Get all the customerPaymentList where customerPaymentCreditCard equals to customerPaymentCreditCardId + 1
        defaultCustomerPaymentShouldNotBeFound("customerPaymentCreditCardId.equals=" + (customerPaymentCreditCardId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByCustomerPaymentVoucherIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);
        CustomerPaymentVoucher customerPaymentVoucher = CustomerPaymentVoucherResourceIT.createEntity(em);
        em.persist(customerPaymentVoucher);
        em.flush();
        customerPayment.setCustomerPaymentVoucher(customerPaymentVoucher);
        customerPaymentVoucher.setCustomerPayment(customerPayment);
        customerPaymentRepository.saveAndFlush(customerPayment);
        Long customerPaymentVoucherId = customerPaymentVoucher.getId();

        // Get all the customerPaymentList where customerPaymentVoucher equals to customerPaymentVoucherId
        defaultCustomerPaymentShouldBeFound("customerPaymentVoucherId.equals=" + customerPaymentVoucherId);

        // Get all the customerPaymentList where customerPaymentVoucher equals to customerPaymentVoucherId + 1
        defaultCustomerPaymentShouldNotBeFound("customerPaymentVoucherId.equals=" + (customerPaymentVoucherId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByCustomerPaymentBankTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);
        CustomerPaymentBankTransfer customerPaymentBankTransfer = CustomerPaymentBankTransferResourceIT.createEntity(em);
        em.persist(customerPaymentBankTransfer);
        em.flush();
        customerPayment.setCustomerPaymentBankTransfer(customerPaymentBankTransfer);
        customerPaymentBankTransfer.setCustomerPayment(customerPayment);
        customerPaymentRepository.saveAndFlush(customerPayment);
        Long customerPaymentBankTransferId = customerPaymentBankTransfer.getId();

        // Get all the customerPaymentList where customerPaymentBankTransfer equals to customerPaymentBankTransferId
        defaultCustomerPaymentShouldBeFound("customerPaymentBankTransferId.equals=" + customerPaymentBankTransferId);

        // Get all the customerPaymentList where customerPaymentBankTransfer equals to customerPaymentBankTransferId + 1
        defaultCustomerPaymentShouldNotBeFound("customerPaymentBankTransferId.equals=" + (customerPaymentBankTransferId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentsByCustomerPaymentPaypalIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);
        CustomerPaymentPaypal customerPaymentPaypal = CustomerPaymentPaypalResourceIT.createEntity(em);
        em.persist(customerPaymentPaypal);
        em.flush();
        customerPayment.setCustomerPaymentPaypal(customerPaymentPaypal);
        customerPaymentPaypal.setCustomerPayment(customerPayment);
        customerPaymentRepository.saveAndFlush(customerPayment);
        Long customerPaymentPaypalId = customerPaymentPaypal.getId();

        // Get all the customerPaymentList where customerPaymentPaypal equals to customerPaymentPaypalId
        defaultCustomerPaymentShouldBeFound("customerPaymentPaypalId.equals=" + customerPaymentPaypalId);

        // Get all the customerPaymentList where customerPaymentPaypal equals to customerPaymentPaypalId + 1
        defaultCustomerPaymentShouldNotBeFound("customerPaymentPaypalId.equals=" + (customerPaymentPaypalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerPaymentShouldBeFound(String filter) throws Exception {
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPayment.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountExcludingTax").value(hasItem(DEFAULT_AMOUNT_EXCLUDING_TAX.intValue())))
            .andExpect(jsonPath("$.[*].taxAmount").value(hasItem(DEFAULT_TAX_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].outstandingAmount").value(hasItem(DEFAULT_OUTSTANDING_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerPaymentShouldNotBeFound(String filter) throws Exception {
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerPayment() throws Exception {
        // Get the customerPayment
        restCustomerPaymentMockMvc.perform(get("/api/customer-payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPayment() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        int databaseSizeBeforeUpdate = customerPaymentRepository.findAll().size();

        // Update the customerPayment
        CustomerPayment updatedCustomerPayment = customerPaymentRepository.findById(customerPayment.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPayment are not directly saved in db
        em.detach(updatedCustomerPayment);
        updatedCustomerPayment
            .amountExcludingTax(UPDATED_AMOUNT_EXCLUDING_TAX)
            .taxAmount(UPDATED_TAX_AMOUNT)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(updatedCustomerPayment);

        restCustomerPaymentMockMvc.perform(put("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerPayment in the database
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeUpdate);
        CustomerPayment testCustomerPayment = customerPaymentList.get(customerPaymentList.size() - 1);
        assertThat(testCustomerPayment.getAmountExcludingTax()).isEqualTo(UPDATED_AMOUNT_EXCLUDING_TAX);
        assertThat(testCustomerPayment.getTaxAmount()).isEqualTo(UPDATED_TAX_AMOUNT);
        assertThat(testCustomerPayment.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);
        assertThat(testCustomerPayment.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testCustomerPayment.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testCustomerPayment.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPayment() throws Exception {
        int databaseSizeBeforeUpdate = customerPaymentRepository.findAll().size();

        // Create the CustomerPayment
        CustomerPaymentDTO customerPaymentDTO = customerPaymentMapper.toDto(customerPayment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPaymentMockMvc.perform(put("/api/customer-payments").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPayment in the database
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPayment() throws Exception {
        // Initialize the database
        customerPaymentRepository.saveAndFlush(customerPayment);

        int databaseSizeBeforeDelete = customerPaymentRepository.findAll().size();

        // Delete the customerPayment
        restCustomerPaymentMockMvc.perform(delete("/api/customer-payments/{id}", customerPayment.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPayment> customerPaymentList = customerPaymentRepository.findAll();
        assertThat(customerPaymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
