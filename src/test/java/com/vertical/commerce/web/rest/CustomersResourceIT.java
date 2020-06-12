package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Customers;
import com.vertical.commerce.domain.People;
import com.vertical.commerce.domain.DeliveryMethods;
import com.vertical.commerce.domain.Addresses;
import com.vertical.commerce.repository.CustomersRepository;
import com.vertical.commerce.service.CustomersService;
import com.vertical.commerce.service.dto.CustomersDTO;
import com.vertical.commerce.service.mapper.CustomersMapper;
import com.vertical.commerce.service.dto.CustomersCriteria;
import com.vertical.commerce.service.CustomersQueryService;

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
 * Integration tests for the {@link CustomersResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomersResourceIT {

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_ACCOUNT_OPENED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ACCOUNT_OPENED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_STANDARD_DISCOUNT_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_STANDARD_DISCOUNT_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_STANDARD_DISCOUNT_PERCENTAGE = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_IS_STATEMENT_SENT = false;
    private static final Boolean UPDATED_IS_STATEMENT_SENT = true;

    private static final Boolean DEFAULT_IS_ON_CREDIT_HOLD = false;
    private static final Boolean UPDATED_IS_ON_CREDIT_HOLD = true;

    private static final Integer DEFAULT_PAYMENT_DAYS = 1;
    private static final Integer UPDATED_PAYMENT_DAYS = 2;
    private static final Integer SMALLER_PAYMENT_DAYS = 1 - 1;

    private static final String DEFAULT_DELIVERY_RUN = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_RUN = "BBBBBBBBBB";

    private static final String DEFAULT_RUN_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_RUN_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private CustomersMapper customersMapper;

    @Autowired
    private CustomersService customersService;

    @Autowired
    private CustomersQueryService customersQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomersMockMvc;

    private Customers customers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customers createEntity(EntityManager em) {
        Customers customers = new Customers()
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .accountOpenedDate(DEFAULT_ACCOUNT_OPENED_DATE)
            .standardDiscountPercentage(DEFAULT_STANDARD_DISCOUNT_PERCENTAGE)
            .isStatementSent(DEFAULT_IS_STATEMENT_SENT)
            .isOnCreditHold(DEFAULT_IS_ON_CREDIT_HOLD)
            .paymentDays(DEFAULT_PAYMENT_DAYS)
            .deliveryRun(DEFAULT_DELIVERY_RUN)
            .runPosition(DEFAULT_RUN_POSITION)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO);
        return customers;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Customers createUpdatedEntity(EntityManager em) {
        Customers customers = new Customers()
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountOpenedDate(UPDATED_ACCOUNT_OPENED_DATE)
            .standardDiscountPercentage(UPDATED_STANDARD_DISCOUNT_PERCENTAGE)
            .isStatementSent(UPDATED_IS_STATEMENT_SENT)
            .isOnCreditHold(UPDATED_IS_ON_CREDIT_HOLD)
            .paymentDays(UPDATED_PAYMENT_DAYS)
            .deliveryRun(UPDATED_DELIVERY_RUN)
            .runPosition(UPDATED_RUN_POSITION)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        return customers;
    }

    @BeforeEach
    public void initTest() {
        customers = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomers() throws Exception {
        int databaseSizeBeforeCreate = customersRepository.findAll().size();
        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);
        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isCreated());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate + 1);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testCustomers.getAccountOpenedDate()).isEqualTo(DEFAULT_ACCOUNT_OPENED_DATE);
        assertThat(testCustomers.getStandardDiscountPercentage()).isEqualTo(DEFAULT_STANDARD_DISCOUNT_PERCENTAGE);
        assertThat(testCustomers.isIsStatementSent()).isEqualTo(DEFAULT_IS_STATEMENT_SENT);
        assertThat(testCustomers.isIsOnCreditHold()).isEqualTo(DEFAULT_IS_ON_CREDIT_HOLD);
        assertThat(testCustomers.getPaymentDays()).isEqualTo(DEFAULT_PAYMENT_DAYS);
        assertThat(testCustomers.getDeliveryRun()).isEqualTo(DEFAULT_DELIVERY_RUN);
        assertThat(testCustomers.getRunPosition()).isEqualTo(DEFAULT_RUN_POSITION);
        assertThat(testCustomers.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testCustomers.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCustomers.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
    }

    @Test
    @Transactional
    public void createCustomersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customersRepository.findAll().size();

        // Create the Customers with an existing ID
        customers.setId(1L);
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAccountNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setAccountNumber(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAccountOpenedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setAccountOpenedDate(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStandardDiscountPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setStandardDiscountPercentage(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsStatementSentIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setIsStatementSent(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsOnCreditHoldIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setIsOnCreditHold(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPaymentDaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setPaymentDays(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setLastEditedBy(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setValidFrom(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidToIsRequired() throws Exception {
        int databaseSizeBeforeTest = customersRepository.findAll().size();
        // set the field null
        customers.setValidTo(null);

        // Create the Customers, which fails.
        CustomersDTO customersDTO = customersMapper.toDto(customers);


        restCustomersMockMvc.perform(post("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList
        restCustomersMockMvc.perform(get("/api/customers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountOpenedDate").value(hasItem(DEFAULT_ACCOUNT_OPENED_DATE.toString())))
            .andExpect(jsonPath("$.[*].standardDiscountPercentage").value(hasItem(DEFAULT_STANDARD_DISCOUNT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].isStatementSent").value(hasItem(DEFAULT_IS_STATEMENT_SENT.booleanValue())))
            .andExpect(jsonPath("$.[*].isOnCreditHold").value(hasItem(DEFAULT_IS_ON_CREDIT_HOLD.booleanValue())))
            .andExpect(jsonPath("$.[*].paymentDays").value(hasItem(DEFAULT_PAYMENT_DAYS)))
            .andExpect(jsonPath("$.[*].deliveryRun").value(hasItem(DEFAULT_DELIVERY_RUN)))
            .andExpect(jsonPath("$.[*].runPosition").value(hasItem(DEFAULT_RUN_POSITION)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get the customers
        restCustomersMockMvc.perform(get("/api/customers/{id}", customers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customers.getId().intValue()))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.accountOpenedDate").value(DEFAULT_ACCOUNT_OPENED_DATE.toString()))
            .andExpect(jsonPath("$.standardDiscountPercentage").value(DEFAULT_STANDARD_DISCOUNT_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.isStatementSent").value(DEFAULT_IS_STATEMENT_SENT.booleanValue()))
            .andExpect(jsonPath("$.isOnCreditHold").value(DEFAULT_IS_ON_CREDIT_HOLD.booleanValue()))
            .andExpect(jsonPath("$.paymentDays").value(DEFAULT_PAYMENT_DAYS))
            .andExpect(jsonPath("$.deliveryRun").value(DEFAULT_DELIVERY_RUN))
            .andExpect(jsonPath("$.runPosition").value(DEFAULT_RUN_POSITION))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()));
    }


    @Test
    @Transactional
    public void getCustomersByIdFiltering() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        Long id = customers.getId();

        defaultCustomersShouldBeFound("id.equals=" + id);
        defaultCustomersShouldNotBeFound("id.notEquals=" + id);

        defaultCustomersShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomersShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomersShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomersShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber not equals to DEFAULT_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.notEquals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber not equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.notEquals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber is not null
        defaultCustomersShouldBeFound("accountNumber.specified=true");

        // Get all the customersList where accountNumber is null
        defaultCustomersShouldNotBeFound("accountNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultCustomersShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the customersList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultCustomersShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCustomersByAccountOpenedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountOpenedDate equals to DEFAULT_ACCOUNT_OPENED_DATE
        defaultCustomersShouldBeFound("accountOpenedDate.equals=" + DEFAULT_ACCOUNT_OPENED_DATE);

        // Get all the customersList where accountOpenedDate equals to UPDATED_ACCOUNT_OPENED_DATE
        defaultCustomersShouldNotBeFound("accountOpenedDate.equals=" + UPDATED_ACCOUNT_OPENED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountOpenedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountOpenedDate not equals to DEFAULT_ACCOUNT_OPENED_DATE
        defaultCustomersShouldNotBeFound("accountOpenedDate.notEquals=" + DEFAULT_ACCOUNT_OPENED_DATE);

        // Get all the customersList where accountOpenedDate not equals to UPDATED_ACCOUNT_OPENED_DATE
        defaultCustomersShouldBeFound("accountOpenedDate.notEquals=" + UPDATED_ACCOUNT_OPENED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountOpenedDateIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountOpenedDate in DEFAULT_ACCOUNT_OPENED_DATE or UPDATED_ACCOUNT_OPENED_DATE
        defaultCustomersShouldBeFound("accountOpenedDate.in=" + DEFAULT_ACCOUNT_OPENED_DATE + "," + UPDATED_ACCOUNT_OPENED_DATE);

        // Get all the customersList where accountOpenedDate equals to UPDATED_ACCOUNT_OPENED_DATE
        defaultCustomersShouldNotBeFound("accountOpenedDate.in=" + UPDATED_ACCOUNT_OPENED_DATE);
    }

    @Test
    @Transactional
    public void getAllCustomersByAccountOpenedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where accountOpenedDate is not null
        defaultCustomersShouldBeFound("accountOpenedDate.specified=true");

        // Get all the customersList where accountOpenedDate is null
        defaultCustomersShouldNotBeFound("accountOpenedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByStandardDiscountPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where standardDiscountPercentage equals to DEFAULT_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldBeFound("standardDiscountPercentage.equals=" + DEFAULT_STANDARD_DISCOUNT_PERCENTAGE);

        // Get all the customersList where standardDiscountPercentage equals to UPDATED_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldNotBeFound("standardDiscountPercentage.equals=" + UPDATED_STANDARD_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByStandardDiscountPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where standardDiscountPercentage not equals to DEFAULT_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldNotBeFound("standardDiscountPercentage.notEquals=" + DEFAULT_STANDARD_DISCOUNT_PERCENTAGE);

        // Get all the customersList where standardDiscountPercentage not equals to UPDATED_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldBeFound("standardDiscountPercentage.notEquals=" + UPDATED_STANDARD_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByStandardDiscountPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where standardDiscountPercentage in DEFAULT_STANDARD_DISCOUNT_PERCENTAGE or UPDATED_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldBeFound("standardDiscountPercentage.in=" + DEFAULT_STANDARD_DISCOUNT_PERCENTAGE + "," + UPDATED_STANDARD_DISCOUNT_PERCENTAGE);

        // Get all the customersList where standardDiscountPercentage equals to UPDATED_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldNotBeFound("standardDiscountPercentage.in=" + UPDATED_STANDARD_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByStandardDiscountPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where standardDiscountPercentage is not null
        defaultCustomersShouldBeFound("standardDiscountPercentage.specified=true");

        // Get all the customersList where standardDiscountPercentage is null
        defaultCustomersShouldNotBeFound("standardDiscountPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByStandardDiscountPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where standardDiscountPercentage is greater than or equal to DEFAULT_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldBeFound("standardDiscountPercentage.greaterThanOrEqual=" + DEFAULT_STANDARD_DISCOUNT_PERCENTAGE);

        // Get all the customersList where standardDiscountPercentage is greater than or equal to UPDATED_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldNotBeFound("standardDiscountPercentage.greaterThanOrEqual=" + UPDATED_STANDARD_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByStandardDiscountPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where standardDiscountPercentage is less than or equal to DEFAULT_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldBeFound("standardDiscountPercentage.lessThanOrEqual=" + DEFAULT_STANDARD_DISCOUNT_PERCENTAGE);

        // Get all the customersList where standardDiscountPercentage is less than or equal to SMALLER_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldNotBeFound("standardDiscountPercentage.lessThanOrEqual=" + SMALLER_STANDARD_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByStandardDiscountPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where standardDiscountPercentage is less than DEFAULT_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldNotBeFound("standardDiscountPercentage.lessThan=" + DEFAULT_STANDARD_DISCOUNT_PERCENTAGE);

        // Get all the customersList where standardDiscountPercentage is less than UPDATED_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldBeFound("standardDiscountPercentage.lessThan=" + UPDATED_STANDARD_DISCOUNT_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllCustomersByStandardDiscountPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where standardDiscountPercentage is greater than DEFAULT_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldNotBeFound("standardDiscountPercentage.greaterThan=" + DEFAULT_STANDARD_DISCOUNT_PERCENTAGE);

        // Get all the customersList where standardDiscountPercentage is greater than SMALLER_STANDARD_DISCOUNT_PERCENTAGE
        defaultCustomersShouldBeFound("standardDiscountPercentage.greaterThan=" + SMALLER_STANDARD_DISCOUNT_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllCustomersByIsStatementSentIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where isStatementSent equals to DEFAULT_IS_STATEMENT_SENT
        defaultCustomersShouldBeFound("isStatementSent.equals=" + DEFAULT_IS_STATEMENT_SENT);

        // Get all the customersList where isStatementSent equals to UPDATED_IS_STATEMENT_SENT
        defaultCustomersShouldNotBeFound("isStatementSent.equals=" + UPDATED_IS_STATEMENT_SENT);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsStatementSentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where isStatementSent not equals to DEFAULT_IS_STATEMENT_SENT
        defaultCustomersShouldNotBeFound("isStatementSent.notEquals=" + DEFAULT_IS_STATEMENT_SENT);

        // Get all the customersList where isStatementSent not equals to UPDATED_IS_STATEMENT_SENT
        defaultCustomersShouldBeFound("isStatementSent.notEquals=" + UPDATED_IS_STATEMENT_SENT);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsStatementSentIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where isStatementSent in DEFAULT_IS_STATEMENT_SENT or UPDATED_IS_STATEMENT_SENT
        defaultCustomersShouldBeFound("isStatementSent.in=" + DEFAULT_IS_STATEMENT_SENT + "," + UPDATED_IS_STATEMENT_SENT);

        // Get all the customersList where isStatementSent equals to UPDATED_IS_STATEMENT_SENT
        defaultCustomersShouldNotBeFound("isStatementSent.in=" + UPDATED_IS_STATEMENT_SENT);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsStatementSentIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where isStatementSent is not null
        defaultCustomersShouldBeFound("isStatementSent.specified=true");

        // Get all the customersList where isStatementSent is null
        defaultCustomersShouldNotBeFound("isStatementSent.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByIsOnCreditHoldIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where isOnCreditHold equals to DEFAULT_IS_ON_CREDIT_HOLD
        defaultCustomersShouldBeFound("isOnCreditHold.equals=" + DEFAULT_IS_ON_CREDIT_HOLD);

        // Get all the customersList where isOnCreditHold equals to UPDATED_IS_ON_CREDIT_HOLD
        defaultCustomersShouldNotBeFound("isOnCreditHold.equals=" + UPDATED_IS_ON_CREDIT_HOLD);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsOnCreditHoldIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where isOnCreditHold not equals to DEFAULT_IS_ON_CREDIT_HOLD
        defaultCustomersShouldNotBeFound("isOnCreditHold.notEquals=" + DEFAULT_IS_ON_CREDIT_HOLD);

        // Get all the customersList where isOnCreditHold not equals to UPDATED_IS_ON_CREDIT_HOLD
        defaultCustomersShouldBeFound("isOnCreditHold.notEquals=" + UPDATED_IS_ON_CREDIT_HOLD);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsOnCreditHoldIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where isOnCreditHold in DEFAULT_IS_ON_CREDIT_HOLD or UPDATED_IS_ON_CREDIT_HOLD
        defaultCustomersShouldBeFound("isOnCreditHold.in=" + DEFAULT_IS_ON_CREDIT_HOLD + "," + UPDATED_IS_ON_CREDIT_HOLD);

        // Get all the customersList where isOnCreditHold equals to UPDATED_IS_ON_CREDIT_HOLD
        defaultCustomersShouldNotBeFound("isOnCreditHold.in=" + UPDATED_IS_ON_CREDIT_HOLD);
    }

    @Test
    @Transactional
    public void getAllCustomersByIsOnCreditHoldIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where isOnCreditHold is not null
        defaultCustomersShouldBeFound("isOnCreditHold.specified=true");

        // Get all the customersList where isOnCreditHold is null
        defaultCustomersShouldNotBeFound("isOnCreditHold.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByPaymentDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentDays equals to DEFAULT_PAYMENT_DAYS
        defaultCustomersShouldBeFound("paymentDays.equals=" + DEFAULT_PAYMENT_DAYS);

        // Get all the customersList where paymentDays equals to UPDATED_PAYMENT_DAYS
        defaultCustomersShouldNotBeFound("paymentDays.equals=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCustomersByPaymentDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentDays not equals to DEFAULT_PAYMENT_DAYS
        defaultCustomersShouldNotBeFound("paymentDays.notEquals=" + DEFAULT_PAYMENT_DAYS);

        // Get all the customersList where paymentDays not equals to UPDATED_PAYMENT_DAYS
        defaultCustomersShouldBeFound("paymentDays.notEquals=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCustomersByPaymentDaysIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentDays in DEFAULT_PAYMENT_DAYS or UPDATED_PAYMENT_DAYS
        defaultCustomersShouldBeFound("paymentDays.in=" + DEFAULT_PAYMENT_DAYS + "," + UPDATED_PAYMENT_DAYS);

        // Get all the customersList where paymentDays equals to UPDATED_PAYMENT_DAYS
        defaultCustomersShouldNotBeFound("paymentDays.in=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCustomersByPaymentDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentDays is not null
        defaultCustomersShouldBeFound("paymentDays.specified=true");

        // Get all the customersList where paymentDays is null
        defaultCustomersShouldNotBeFound("paymentDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByPaymentDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentDays is greater than or equal to DEFAULT_PAYMENT_DAYS
        defaultCustomersShouldBeFound("paymentDays.greaterThanOrEqual=" + DEFAULT_PAYMENT_DAYS);

        // Get all the customersList where paymentDays is greater than or equal to UPDATED_PAYMENT_DAYS
        defaultCustomersShouldNotBeFound("paymentDays.greaterThanOrEqual=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCustomersByPaymentDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentDays is less than or equal to DEFAULT_PAYMENT_DAYS
        defaultCustomersShouldBeFound("paymentDays.lessThanOrEqual=" + DEFAULT_PAYMENT_DAYS);

        // Get all the customersList where paymentDays is less than or equal to SMALLER_PAYMENT_DAYS
        defaultCustomersShouldNotBeFound("paymentDays.lessThanOrEqual=" + SMALLER_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCustomersByPaymentDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentDays is less than DEFAULT_PAYMENT_DAYS
        defaultCustomersShouldNotBeFound("paymentDays.lessThan=" + DEFAULT_PAYMENT_DAYS);

        // Get all the customersList where paymentDays is less than UPDATED_PAYMENT_DAYS
        defaultCustomersShouldBeFound("paymentDays.lessThan=" + UPDATED_PAYMENT_DAYS);
    }

    @Test
    @Transactional
    public void getAllCustomersByPaymentDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where paymentDays is greater than DEFAULT_PAYMENT_DAYS
        defaultCustomersShouldNotBeFound("paymentDays.greaterThan=" + DEFAULT_PAYMENT_DAYS);

        // Get all the customersList where paymentDays is greater than SMALLER_PAYMENT_DAYS
        defaultCustomersShouldBeFound("paymentDays.greaterThan=" + SMALLER_PAYMENT_DAYS);
    }


    @Test
    @Transactional
    public void getAllCustomersByDeliveryRunIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where deliveryRun equals to DEFAULT_DELIVERY_RUN
        defaultCustomersShouldBeFound("deliveryRun.equals=" + DEFAULT_DELIVERY_RUN);

        // Get all the customersList where deliveryRun equals to UPDATED_DELIVERY_RUN
        defaultCustomersShouldNotBeFound("deliveryRun.equals=" + UPDATED_DELIVERY_RUN);
    }

    @Test
    @Transactional
    public void getAllCustomersByDeliveryRunIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where deliveryRun not equals to DEFAULT_DELIVERY_RUN
        defaultCustomersShouldNotBeFound("deliveryRun.notEquals=" + DEFAULT_DELIVERY_RUN);

        // Get all the customersList where deliveryRun not equals to UPDATED_DELIVERY_RUN
        defaultCustomersShouldBeFound("deliveryRun.notEquals=" + UPDATED_DELIVERY_RUN);
    }

    @Test
    @Transactional
    public void getAllCustomersByDeliveryRunIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where deliveryRun in DEFAULT_DELIVERY_RUN or UPDATED_DELIVERY_RUN
        defaultCustomersShouldBeFound("deliveryRun.in=" + DEFAULT_DELIVERY_RUN + "," + UPDATED_DELIVERY_RUN);

        // Get all the customersList where deliveryRun equals to UPDATED_DELIVERY_RUN
        defaultCustomersShouldNotBeFound("deliveryRun.in=" + UPDATED_DELIVERY_RUN);
    }

    @Test
    @Transactional
    public void getAllCustomersByDeliveryRunIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where deliveryRun is not null
        defaultCustomersShouldBeFound("deliveryRun.specified=true");

        // Get all the customersList where deliveryRun is null
        defaultCustomersShouldNotBeFound("deliveryRun.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByDeliveryRunContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where deliveryRun contains DEFAULT_DELIVERY_RUN
        defaultCustomersShouldBeFound("deliveryRun.contains=" + DEFAULT_DELIVERY_RUN);

        // Get all the customersList where deliveryRun contains UPDATED_DELIVERY_RUN
        defaultCustomersShouldNotBeFound("deliveryRun.contains=" + UPDATED_DELIVERY_RUN);
    }

    @Test
    @Transactional
    public void getAllCustomersByDeliveryRunNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where deliveryRun does not contain DEFAULT_DELIVERY_RUN
        defaultCustomersShouldNotBeFound("deliveryRun.doesNotContain=" + DEFAULT_DELIVERY_RUN);

        // Get all the customersList where deliveryRun does not contain UPDATED_DELIVERY_RUN
        defaultCustomersShouldBeFound("deliveryRun.doesNotContain=" + UPDATED_DELIVERY_RUN);
    }


    @Test
    @Transactional
    public void getAllCustomersByRunPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where runPosition equals to DEFAULT_RUN_POSITION
        defaultCustomersShouldBeFound("runPosition.equals=" + DEFAULT_RUN_POSITION);

        // Get all the customersList where runPosition equals to UPDATED_RUN_POSITION
        defaultCustomersShouldNotBeFound("runPosition.equals=" + UPDATED_RUN_POSITION);
    }

    @Test
    @Transactional
    public void getAllCustomersByRunPositionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where runPosition not equals to DEFAULT_RUN_POSITION
        defaultCustomersShouldNotBeFound("runPosition.notEquals=" + DEFAULT_RUN_POSITION);

        // Get all the customersList where runPosition not equals to UPDATED_RUN_POSITION
        defaultCustomersShouldBeFound("runPosition.notEquals=" + UPDATED_RUN_POSITION);
    }

    @Test
    @Transactional
    public void getAllCustomersByRunPositionIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where runPosition in DEFAULT_RUN_POSITION or UPDATED_RUN_POSITION
        defaultCustomersShouldBeFound("runPosition.in=" + DEFAULT_RUN_POSITION + "," + UPDATED_RUN_POSITION);

        // Get all the customersList where runPosition equals to UPDATED_RUN_POSITION
        defaultCustomersShouldNotBeFound("runPosition.in=" + UPDATED_RUN_POSITION);
    }

    @Test
    @Transactional
    public void getAllCustomersByRunPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where runPosition is not null
        defaultCustomersShouldBeFound("runPosition.specified=true");

        // Get all the customersList where runPosition is null
        defaultCustomersShouldNotBeFound("runPosition.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByRunPositionContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where runPosition contains DEFAULT_RUN_POSITION
        defaultCustomersShouldBeFound("runPosition.contains=" + DEFAULT_RUN_POSITION);

        // Get all the customersList where runPosition contains UPDATED_RUN_POSITION
        defaultCustomersShouldNotBeFound("runPosition.contains=" + UPDATED_RUN_POSITION);
    }

    @Test
    @Transactional
    public void getAllCustomersByRunPositionNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where runPosition does not contain DEFAULT_RUN_POSITION
        defaultCustomersShouldNotBeFound("runPosition.doesNotContain=" + DEFAULT_RUN_POSITION);

        // Get all the customersList where runPosition does not contain UPDATED_RUN_POSITION
        defaultCustomersShouldBeFound("runPosition.doesNotContain=" + UPDATED_RUN_POSITION);
    }


    @Test
    @Transactional
    public void getAllCustomersByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultCustomersShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customersList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomersShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomersByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultCustomersShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customersList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultCustomersShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomersByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultCustomersShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the customersList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomersShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomersByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastEditedBy is not null
        defaultCustomersShouldBeFound("lastEditedBy.specified=true");

        // Get all the customersList where lastEditedBy is null
        defaultCustomersShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomersByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultCustomersShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customersList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultCustomersShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomersByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultCustomersShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customersList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultCustomersShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllCustomersByValidFromIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where validFrom equals to DEFAULT_VALID_FROM
        defaultCustomersShouldBeFound("validFrom.equals=" + DEFAULT_VALID_FROM);

        // Get all the customersList where validFrom equals to UPDATED_VALID_FROM
        defaultCustomersShouldNotBeFound("validFrom.equals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomersByValidFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where validFrom not equals to DEFAULT_VALID_FROM
        defaultCustomersShouldNotBeFound("validFrom.notEquals=" + DEFAULT_VALID_FROM);

        // Get all the customersList where validFrom not equals to UPDATED_VALID_FROM
        defaultCustomersShouldBeFound("validFrom.notEquals=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomersByValidFromIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where validFrom in DEFAULT_VALID_FROM or UPDATED_VALID_FROM
        defaultCustomersShouldBeFound("validFrom.in=" + DEFAULT_VALID_FROM + "," + UPDATED_VALID_FROM);

        // Get all the customersList where validFrom equals to UPDATED_VALID_FROM
        defaultCustomersShouldNotBeFound("validFrom.in=" + UPDATED_VALID_FROM);
    }

    @Test
    @Transactional
    public void getAllCustomersByValidFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where validFrom is not null
        defaultCustomersShouldBeFound("validFrom.specified=true");

        // Get all the customersList where validFrom is null
        defaultCustomersShouldNotBeFound("validFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByValidToIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where validTo equals to DEFAULT_VALID_TO
        defaultCustomersShouldBeFound("validTo.equals=" + DEFAULT_VALID_TO);

        // Get all the customersList where validTo equals to UPDATED_VALID_TO
        defaultCustomersShouldNotBeFound("validTo.equals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomersByValidToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where validTo not equals to DEFAULT_VALID_TO
        defaultCustomersShouldNotBeFound("validTo.notEquals=" + DEFAULT_VALID_TO);

        // Get all the customersList where validTo not equals to UPDATED_VALID_TO
        defaultCustomersShouldBeFound("validTo.notEquals=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomersByValidToIsInShouldWork() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where validTo in DEFAULT_VALID_TO or UPDATED_VALID_TO
        defaultCustomersShouldBeFound("validTo.in=" + DEFAULT_VALID_TO + "," + UPDATED_VALID_TO);

        // Get all the customersList where validTo equals to UPDATED_VALID_TO
        defaultCustomersShouldNotBeFound("validTo.in=" + UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void getAllCustomersByValidToIsNullOrNotNull() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        // Get all the customersList where validTo is not null
        defaultCustomersShouldBeFound("validTo.specified=true");

        // Get all the customersList where validTo is null
        defaultCustomersShouldNotBeFound("validTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomersByPeopleIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        People people = PeopleResourceIT.createEntity(em);
        em.persist(people);
        em.flush();
        customers.setPeople(people);
        customersRepository.saveAndFlush(customers);
        Long peopleId = people.getId();

        // Get all the customersList where people equals to peopleId
        defaultCustomersShouldBeFound("peopleId.equals=" + peopleId);

        // Get all the customersList where people equals to peopleId + 1
        defaultCustomersShouldNotBeFound("peopleId.equals=" + (peopleId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomersByDeliveryMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        DeliveryMethods deliveryMethod = DeliveryMethodsResourceIT.createEntity(em);
        em.persist(deliveryMethod);
        em.flush();
        customers.setDeliveryMethod(deliveryMethod);
        customersRepository.saveAndFlush(customers);
        Long deliveryMethodId = deliveryMethod.getId();

        // Get all the customersList where deliveryMethod equals to deliveryMethodId
        defaultCustomersShouldBeFound("deliveryMethodId.equals=" + deliveryMethodId);

        // Get all the customersList where deliveryMethod equals to deliveryMethodId + 1
        defaultCustomersShouldNotBeFound("deliveryMethodId.equals=" + (deliveryMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomersByDeliveryAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);
        Addresses deliveryAddress = AddressesResourceIT.createEntity(em);
        em.persist(deliveryAddress);
        em.flush();
        customers.setDeliveryAddress(deliveryAddress);
        customersRepository.saveAndFlush(customers);
        Long deliveryAddressId = deliveryAddress.getId();

        // Get all the customersList where deliveryAddress equals to deliveryAddressId
        defaultCustomersShouldBeFound("deliveryAddressId.equals=" + deliveryAddressId);

        // Get all the customersList where deliveryAddress equals to deliveryAddressId + 1
        defaultCustomersShouldNotBeFound("deliveryAddressId.equals=" + (deliveryAddressId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomersShouldBeFound(String filter) throws Exception {
        restCustomersMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customers.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].accountOpenedDate").value(hasItem(DEFAULT_ACCOUNT_OPENED_DATE.toString())))
            .andExpect(jsonPath("$.[*].standardDiscountPercentage").value(hasItem(DEFAULT_STANDARD_DISCOUNT_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].isStatementSent").value(hasItem(DEFAULT_IS_STATEMENT_SENT.booleanValue())))
            .andExpect(jsonPath("$.[*].isOnCreditHold").value(hasItem(DEFAULT_IS_ON_CREDIT_HOLD.booleanValue())))
            .andExpect(jsonPath("$.[*].paymentDays").value(hasItem(DEFAULT_PAYMENT_DAYS)))
            .andExpect(jsonPath("$.[*].deliveryRun").value(hasItem(DEFAULT_DELIVERY_RUN)))
            .andExpect(jsonPath("$.[*].runPosition").value(hasItem(DEFAULT_RUN_POSITION)))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())));

        // Check, that the count call also returns 1
        restCustomersMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomersShouldNotBeFound(String filter) throws Exception {
        restCustomersMockMvc.perform(get("/api/customers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomersMockMvc.perform(get("/api/customers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomers() throws Exception {
        // Get the customers
        restCustomersMockMvc.perform(get("/api/customers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Update the customers
        Customers updatedCustomers = customersRepository.findById(customers.getId()).get();
        // Disconnect from session so that the updates on updatedCustomers are not directly saved in db
        em.detach(updatedCustomers);
        updatedCustomers
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .accountOpenedDate(UPDATED_ACCOUNT_OPENED_DATE)
            .standardDiscountPercentage(UPDATED_STANDARD_DISCOUNT_PERCENTAGE)
            .isStatementSent(UPDATED_IS_STATEMENT_SENT)
            .isOnCreditHold(UPDATED_IS_ON_CREDIT_HOLD)
            .paymentDays(UPDATED_PAYMENT_DAYS)
            .deliveryRun(UPDATED_DELIVERY_RUN)
            .runPosition(UPDATED_RUN_POSITION)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO);
        CustomersDTO customersDTO = customersMapper.toDto(updatedCustomers);

        restCustomersMockMvc.perform(put("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isOk());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
        Customers testCustomers = customersList.get(customersList.size() - 1);
        assertThat(testCustomers.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testCustomers.getAccountOpenedDate()).isEqualTo(UPDATED_ACCOUNT_OPENED_DATE);
        assertThat(testCustomers.getStandardDiscountPercentage()).isEqualTo(UPDATED_STANDARD_DISCOUNT_PERCENTAGE);
        assertThat(testCustomers.isIsStatementSent()).isEqualTo(UPDATED_IS_STATEMENT_SENT);
        assertThat(testCustomers.isIsOnCreditHold()).isEqualTo(UPDATED_IS_ON_CREDIT_HOLD);
        assertThat(testCustomers.getPaymentDays()).isEqualTo(UPDATED_PAYMENT_DAYS);
        assertThat(testCustomers.getDeliveryRun()).isEqualTo(UPDATED_DELIVERY_RUN);
        assertThat(testCustomers.getRunPosition()).isEqualTo(UPDATED_RUN_POSITION);
        assertThat(testCustomers.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testCustomers.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCustomers.getValidTo()).isEqualTo(UPDATED_VALID_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomers() throws Exception {
        int databaseSizeBeforeUpdate = customersRepository.findAll().size();

        // Create the Customers
        CustomersDTO customersDTO = customersMapper.toDto(customers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomersMockMvc.perform(put("/api/customers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Customers in the database
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomers() throws Exception {
        // Initialize the database
        customersRepository.saveAndFlush(customers);

        int databaseSizeBeforeDelete = customersRepository.findAll().size();

        // Delete the customers
        restCustomersMockMvc.perform(delete("/api/customers/{id}", customers.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Customers> customersList = customersRepository.findAll();
        assertThat(customersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
