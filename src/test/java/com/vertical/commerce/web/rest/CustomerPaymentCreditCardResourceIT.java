package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CustomerPaymentCreditCard;
import com.vertical.commerce.domain.CustomerPayment;
import com.vertical.commerce.domain.Currency;
import com.vertical.commerce.repository.CustomerPaymentCreditCardRepository;
import com.vertical.commerce.service.CustomerPaymentCreditCardService;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentCreditCardMapper;
import com.vertical.commerce.service.dto.CustomerPaymentCreditCardCriteria;
import com.vertical.commerce.service.CustomerPaymentCreditCardQueryService;

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
 * Integration tests for the {@link CustomerPaymentCreditCardResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPaymentCreditCardResourceIT {

    private static final String DEFAULT_CREDIT_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREDIT_CARD_EXPIRY_MONTH = 1;
    private static final Integer UPDATED_CREDIT_CARD_EXPIRY_MONTH = 2;
    private static final Integer SMALLER_CREDIT_CARD_EXPIRY_MONTH = 1 - 1;

    private static final Integer DEFAULT_CREDIT_CARD_EXPIRY_YEAR = 1;
    private static final Integer UPDATED_CREDIT_CARD_EXPIRY_YEAR = 2;
    private static final Integer SMALLER_CREDIT_CARD_EXPIRY_YEAR = 1 - 1;

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_BATCH_ID = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_ID = "BBBBBBBBBB";

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
    private CustomerPaymentCreditCardRepository customerPaymentCreditCardRepository;

    @Autowired
    private CustomerPaymentCreditCardMapper customerPaymentCreditCardMapper;

    @Autowired
    private CustomerPaymentCreditCardService customerPaymentCreditCardService;

    @Autowired
    private CustomerPaymentCreditCardQueryService customerPaymentCreditCardQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPaymentCreditCardMockMvc;

    private CustomerPaymentCreditCard customerPaymentCreditCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentCreditCard createEntity(EntityManager em) {
        CustomerPaymentCreditCard customerPaymentCreditCard = new CustomerPaymentCreditCard()
            .creditCardNumber(DEFAULT_CREDIT_CARD_NUMBER)
            .creditCardExpiryMonth(DEFAULT_CREDIT_CARD_EXPIRY_MONTH)
            .creditCardExpiryYear(DEFAULT_CREDIT_CARD_EXPIRY_YEAR)
            .amount(DEFAULT_AMOUNT)
            .batchId(DEFAULT_BATCH_ID)
            .responseCode(DEFAULT_RESPONSE_CODE)
            .approvalCode(DEFAULT_APPROVAL_CODE)
            .responseData(DEFAULT_RESPONSE_DATA)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return customerPaymentCreditCard;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentCreditCard createUpdatedEntity(EntityManager em) {
        CustomerPaymentCreditCard customerPaymentCreditCard = new CustomerPaymentCreditCard()
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditCardExpiryMonth(UPDATED_CREDIT_CARD_EXPIRY_MONTH)
            .creditCardExpiryYear(UPDATED_CREDIT_CARD_EXPIRY_YEAR)
            .amount(UPDATED_AMOUNT)
            .batchId(UPDATED_BATCH_ID)
            .responseCode(UPDATED_RESPONSE_CODE)
            .approvalCode(UPDATED_APPROVAL_CODE)
            .responseData(UPDATED_RESPONSE_DATA)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return customerPaymentCreditCard;
    }

    @BeforeEach
    public void initTest() {
        customerPaymentCreditCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPaymentCreditCard() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentCreditCardRepository.findAll().size();
        // Create the CustomerPaymentCreditCard
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);
        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerPaymentCreditCard in the database
        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPaymentCreditCard testCustomerPaymentCreditCard = customerPaymentCreditCardList.get(customerPaymentCreditCardList.size() - 1);
        assertThat(testCustomerPaymentCreditCard.getCreditCardNumber()).isEqualTo(DEFAULT_CREDIT_CARD_NUMBER);
        assertThat(testCustomerPaymentCreditCard.getCreditCardExpiryMonth()).isEqualTo(DEFAULT_CREDIT_CARD_EXPIRY_MONTH);
        assertThat(testCustomerPaymentCreditCard.getCreditCardExpiryYear()).isEqualTo(DEFAULT_CREDIT_CARD_EXPIRY_YEAR);
        assertThat(testCustomerPaymentCreditCard.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCustomerPaymentCreditCard.getBatchId()).isEqualTo(DEFAULT_BATCH_ID);
        assertThat(testCustomerPaymentCreditCard.getResponseCode()).isEqualTo(DEFAULT_RESPONSE_CODE);
        assertThat(testCustomerPaymentCreditCard.getApprovalCode()).isEqualTo(DEFAULT_APPROVAL_CODE);
        assertThat(testCustomerPaymentCreditCard.getResponseData()).isEqualTo(DEFAULT_RESPONSE_DATA);
        assertThat(testCustomerPaymentCreditCard.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testCustomerPaymentCreditCard.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createCustomerPaymentCreditCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentCreditCardRepository.findAll().size();

        // Create the CustomerPaymentCreditCard with an existing ID
        customerPaymentCreditCard.setId(1L);
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentCreditCard in the database
        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreditCardNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setCreditCardNumber(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditCardExpiryMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setCreditCardExpiryMonth(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreditCardExpiryYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setCreditCardExpiryYear(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setAmount(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBatchIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setBatchId(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResponseCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setResponseCode(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApprovalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setApprovalCode(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setLastEditedBy(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentCreditCardRepository.findAll().size();
        // set the field null
        customerPaymentCreditCard.setLastEditedWhen(null);

        // Create the CustomerPaymentCreditCard, which fails.
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);


        restCustomerPaymentCreditCardMockMvc.perform(post("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCards() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList
        restCustomerPaymentCreditCardMockMvc.perform(get("/api/customer-payment-credit-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentCreditCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditCardNumber").value(hasItem(DEFAULT_CREDIT_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].creditCardExpiryMonth").value(hasItem(DEFAULT_CREDIT_CARD_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].creditCardExpiryYear").value(hasItem(DEFAULT_CREDIT_CARD_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].approvalCode").value(hasItem(DEFAULT_APPROVAL_CODE)))
            .andExpect(jsonPath("$.[*].responseData").value(hasItem(DEFAULT_RESPONSE_DATA.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerPaymentCreditCard() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get the customerPaymentCreditCard
        restCustomerPaymentCreditCardMockMvc.perform(get("/api/customer-payment-credit-cards/{id}", customerPaymentCreditCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPaymentCreditCard.getId().intValue()))
            .andExpect(jsonPath("$.creditCardNumber").value(DEFAULT_CREDIT_CARD_NUMBER))
            .andExpect(jsonPath("$.creditCardExpiryMonth").value(DEFAULT_CREDIT_CARD_EXPIRY_MONTH))
            .andExpect(jsonPath("$.creditCardExpiryYear").value(DEFAULT_CREDIT_CARD_EXPIRY_YEAR))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.batchId").value(DEFAULT_BATCH_ID))
            .andExpect(jsonPath("$.responseCode").value(DEFAULT_RESPONSE_CODE))
            .andExpect(jsonPath("$.approvalCode").value(DEFAULT_APPROVAL_CODE))
            .andExpect(jsonPath("$.responseData").value(DEFAULT_RESPONSE_DATA.toString()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getCustomerPaymentCreditCardsByIdFiltering() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        Long id = customerPaymentCreditCard.getId();

        defaultCustomerPaymentCreditCardShouldBeFound("id.equals=" + id);
        defaultCustomerPaymentCreditCardShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerPaymentCreditCardShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerPaymentCreditCardShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerPaymentCreditCardShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerPaymentCreditCardShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardNumber equals to DEFAULT_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardNumber.equals=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the customerPaymentCreditCardList where creditCardNumber equals to UPDATED_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardNumber.equals=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardNumber not equals to DEFAULT_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardNumber.notEquals=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the customerPaymentCreditCardList where creditCardNumber not equals to UPDATED_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardNumber.notEquals=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardNumberIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardNumber in DEFAULT_CREDIT_CARD_NUMBER or UPDATED_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardNumber.in=" + DEFAULT_CREDIT_CARD_NUMBER + "," + UPDATED_CREDIT_CARD_NUMBER);

        // Get all the customerPaymentCreditCardList where creditCardNumber equals to UPDATED_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardNumber.in=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardNumber is not null
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardNumber.specified=true");

        // Get all the customerPaymentCreditCardList where creditCardNumber is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardNumberContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardNumber contains DEFAULT_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardNumber.contains=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the customerPaymentCreditCardList where creditCardNumber contains UPDATED_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardNumber.contains=" + UPDATED_CREDIT_CARD_NUMBER);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardNumberNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardNumber does not contain DEFAULT_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardNumber.doesNotContain=" + DEFAULT_CREDIT_CARD_NUMBER);

        // Get all the customerPaymentCreditCardList where creditCardNumber does not contain UPDATED_CREDIT_CARD_NUMBER
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardNumber.doesNotContain=" + UPDATED_CREDIT_CARD_NUMBER);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth equals to DEFAULT_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryMonth.equals=" + DEFAULT_CREDIT_CARD_EXPIRY_MONTH);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth equals to UPDATED_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryMonth.equals=" + UPDATED_CREDIT_CARD_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth not equals to DEFAULT_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryMonth.notEquals=" + DEFAULT_CREDIT_CARD_EXPIRY_MONTH);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth not equals to UPDATED_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryMonth.notEquals=" + UPDATED_CREDIT_CARD_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryMonthIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth in DEFAULT_CREDIT_CARD_EXPIRY_MONTH or UPDATED_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryMonth.in=" + DEFAULT_CREDIT_CARD_EXPIRY_MONTH + "," + UPDATED_CREDIT_CARD_EXPIRY_MONTH);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth equals to UPDATED_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryMonth.in=" + UPDATED_CREDIT_CARD_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is not null
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryMonth.specified=true");

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is greater than or equal to DEFAULT_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryMonth.greaterThanOrEqual=" + DEFAULT_CREDIT_CARD_EXPIRY_MONTH);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is greater than or equal to UPDATED_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryMonth.greaterThanOrEqual=" + UPDATED_CREDIT_CARD_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryMonthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is less than or equal to DEFAULT_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryMonth.lessThanOrEqual=" + DEFAULT_CREDIT_CARD_EXPIRY_MONTH);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is less than or equal to SMALLER_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryMonth.lessThanOrEqual=" + SMALLER_CREDIT_CARD_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is less than DEFAULT_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryMonth.lessThan=" + DEFAULT_CREDIT_CARD_EXPIRY_MONTH);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is less than UPDATED_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryMonth.lessThan=" + UPDATED_CREDIT_CARD_EXPIRY_MONTH);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryMonthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is greater than DEFAULT_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryMonth.greaterThan=" + DEFAULT_CREDIT_CARD_EXPIRY_MONTH);

        // Get all the customerPaymentCreditCardList where creditCardExpiryMonth is greater than SMALLER_CREDIT_CARD_EXPIRY_MONTH
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryMonth.greaterThan=" + SMALLER_CREDIT_CARD_EXPIRY_MONTH);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryYearIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear equals to DEFAULT_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryYear.equals=" + DEFAULT_CREDIT_CARD_EXPIRY_YEAR);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear equals to UPDATED_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryYear.equals=" + UPDATED_CREDIT_CARD_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryYearIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear not equals to DEFAULT_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryYear.notEquals=" + DEFAULT_CREDIT_CARD_EXPIRY_YEAR);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear not equals to UPDATED_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryYear.notEquals=" + UPDATED_CREDIT_CARD_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryYearIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear in DEFAULT_CREDIT_CARD_EXPIRY_YEAR or UPDATED_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryYear.in=" + DEFAULT_CREDIT_CARD_EXPIRY_YEAR + "," + UPDATED_CREDIT_CARD_EXPIRY_YEAR);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear equals to UPDATED_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryYear.in=" + UPDATED_CREDIT_CARD_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryYearIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is not null
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryYear.specified=true");

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryYear.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryYearIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is greater than or equal to DEFAULT_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryYear.greaterThanOrEqual=" + DEFAULT_CREDIT_CARD_EXPIRY_YEAR);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is greater than or equal to UPDATED_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryYear.greaterThanOrEqual=" + UPDATED_CREDIT_CARD_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryYearIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is less than or equal to DEFAULT_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryYear.lessThanOrEqual=" + DEFAULT_CREDIT_CARD_EXPIRY_YEAR);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is less than or equal to SMALLER_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryYear.lessThanOrEqual=" + SMALLER_CREDIT_CARD_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryYearIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is less than DEFAULT_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryYear.lessThan=" + DEFAULT_CREDIT_CARD_EXPIRY_YEAR);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is less than UPDATED_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryYear.lessThan=" + UPDATED_CREDIT_CARD_EXPIRY_YEAR);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCreditCardExpiryYearIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is greater than DEFAULT_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldNotBeFound("creditCardExpiryYear.greaterThan=" + DEFAULT_CREDIT_CARD_EXPIRY_YEAR);

        // Get all the customerPaymentCreditCardList where creditCardExpiryYear is greater than SMALLER_CREDIT_CARD_EXPIRY_YEAR
        defaultCustomerPaymentCreditCardShouldBeFound("creditCardExpiryYear.greaterThan=" + SMALLER_CREDIT_CARD_EXPIRY_YEAR);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where amount equals to DEFAULT_AMOUNT
        defaultCustomerPaymentCreditCardShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentCreditCardList where amount equals to UPDATED_AMOUNT
        defaultCustomerPaymentCreditCardShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where amount not equals to DEFAULT_AMOUNT
        defaultCustomerPaymentCreditCardShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentCreditCardList where amount not equals to UPDATED_AMOUNT
        defaultCustomerPaymentCreditCardShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCustomerPaymentCreditCardShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the customerPaymentCreditCardList where amount equals to UPDATED_AMOUNT
        defaultCustomerPaymentCreditCardShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where amount is not null
        defaultCustomerPaymentCreditCardShouldBeFound("amount.specified=true");

        // Get all the customerPaymentCreditCardList where amount is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCustomerPaymentCreditCardShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentCreditCardList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCustomerPaymentCreditCardShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCustomerPaymentCreditCardShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentCreditCardList where amount is less than or equal to SMALLER_AMOUNT
        defaultCustomerPaymentCreditCardShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where amount is less than DEFAULT_AMOUNT
        defaultCustomerPaymentCreditCardShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentCreditCardList where amount is less than UPDATED_AMOUNT
        defaultCustomerPaymentCreditCardShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where amount is greater than DEFAULT_AMOUNT
        defaultCustomerPaymentCreditCardShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentCreditCardList where amount is greater than SMALLER_AMOUNT
        defaultCustomerPaymentCreditCardShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByBatchIdIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where batchId equals to DEFAULT_BATCH_ID
        defaultCustomerPaymentCreditCardShouldBeFound("batchId.equals=" + DEFAULT_BATCH_ID);

        // Get all the customerPaymentCreditCardList where batchId equals to UPDATED_BATCH_ID
        defaultCustomerPaymentCreditCardShouldNotBeFound("batchId.equals=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByBatchIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where batchId not equals to DEFAULT_BATCH_ID
        defaultCustomerPaymentCreditCardShouldNotBeFound("batchId.notEquals=" + DEFAULT_BATCH_ID);

        // Get all the customerPaymentCreditCardList where batchId not equals to UPDATED_BATCH_ID
        defaultCustomerPaymentCreditCardShouldBeFound("batchId.notEquals=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByBatchIdIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where batchId in DEFAULT_BATCH_ID or UPDATED_BATCH_ID
        defaultCustomerPaymentCreditCardShouldBeFound("batchId.in=" + DEFAULT_BATCH_ID + "," + UPDATED_BATCH_ID);

        // Get all the customerPaymentCreditCardList where batchId equals to UPDATED_BATCH_ID
        defaultCustomerPaymentCreditCardShouldNotBeFound("batchId.in=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByBatchIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where batchId is not null
        defaultCustomerPaymentCreditCardShouldBeFound("batchId.specified=true");

        // Get all the customerPaymentCreditCardList where batchId is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("batchId.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByBatchIdContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where batchId contains DEFAULT_BATCH_ID
        defaultCustomerPaymentCreditCardShouldBeFound("batchId.contains=" + DEFAULT_BATCH_ID);

        // Get all the customerPaymentCreditCardList where batchId contains UPDATED_BATCH_ID
        defaultCustomerPaymentCreditCardShouldNotBeFound("batchId.contains=" + UPDATED_BATCH_ID);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByBatchIdNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where batchId does not contain DEFAULT_BATCH_ID
        defaultCustomerPaymentCreditCardShouldNotBeFound("batchId.doesNotContain=" + DEFAULT_BATCH_ID);

        // Get all the customerPaymentCreditCardList where batchId does not contain UPDATED_BATCH_ID
        defaultCustomerPaymentCreditCardShouldBeFound("batchId.doesNotContain=" + UPDATED_BATCH_ID);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByResponseCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where responseCode equals to DEFAULT_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("responseCode.equals=" + DEFAULT_RESPONSE_CODE);

        // Get all the customerPaymentCreditCardList where responseCode equals to UPDATED_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("responseCode.equals=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByResponseCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where responseCode not equals to DEFAULT_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("responseCode.notEquals=" + DEFAULT_RESPONSE_CODE);

        // Get all the customerPaymentCreditCardList where responseCode not equals to UPDATED_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("responseCode.notEquals=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByResponseCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where responseCode in DEFAULT_RESPONSE_CODE or UPDATED_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("responseCode.in=" + DEFAULT_RESPONSE_CODE + "," + UPDATED_RESPONSE_CODE);

        // Get all the customerPaymentCreditCardList where responseCode equals to UPDATED_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("responseCode.in=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByResponseCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where responseCode is not null
        defaultCustomerPaymentCreditCardShouldBeFound("responseCode.specified=true");

        // Get all the customerPaymentCreditCardList where responseCode is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("responseCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByResponseCodeContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where responseCode contains DEFAULT_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("responseCode.contains=" + DEFAULT_RESPONSE_CODE);

        // Get all the customerPaymentCreditCardList where responseCode contains UPDATED_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("responseCode.contains=" + UPDATED_RESPONSE_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByResponseCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where responseCode does not contain DEFAULT_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("responseCode.doesNotContain=" + DEFAULT_RESPONSE_CODE);

        // Get all the customerPaymentCreditCardList where responseCode does not contain UPDATED_RESPONSE_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("responseCode.doesNotContain=" + UPDATED_RESPONSE_CODE);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByApprovalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where approvalCode equals to DEFAULT_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("approvalCode.equals=" + DEFAULT_APPROVAL_CODE);

        // Get all the customerPaymentCreditCardList where approvalCode equals to UPDATED_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("approvalCode.equals=" + UPDATED_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByApprovalCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where approvalCode not equals to DEFAULT_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("approvalCode.notEquals=" + DEFAULT_APPROVAL_CODE);

        // Get all the customerPaymentCreditCardList where approvalCode not equals to UPDATED_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("approvalCode.notEquals=" + UPDATED_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByApprovalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where approvalCode in DEFAULT_APPROVAL_CODE or UPDATED_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("approvalCode.in=" + DEFAULT_APPROVAL_CODE + "," + UPDATED_APPROVAL_CODE);

        // Get all the customerPaymentCreditCardList where approvalCode equals to UPDATED_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("approvalCode.in=" + UPDATED_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByApprovalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where approvalCode is not null
        defaultCustomerPaymentCreditCardShouldBeFound("approvalCode.specified=true");

        // Get all the customerPaymentCreditCardList where approvalCode is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("approvalCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByApprovalCodeContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where approvalCode contains DEFAULT_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("approvalCode.contains=" + DEFAULT_APPROVAL_CODE);

        // Get all the customerPaymentCreditCardList where approvalCode contains UPDATED_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("approvalCode.contains=" + UPDATED_APPROVAL_CODE);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByApprovalCodeNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where approvalCode does not contain DEFAULT_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldNotBeFound("approvalCode.doesNotContain=" + DEFAULT_APPROVAL_CODE);

        // Get all the customerPaymentCreditCardList where approvalCode does not contain UPDATED_APPROVAL_CODE
        defaultCustomerPaymentCreditCardShouldBeFound("approvalCode.doesNotContain=" + UPDATED_APPROVAL_CODE);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedBy is not null
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedBy.specified=true");

        // Get all the customerPaymentCreditCardList where lastEditedBy is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the customerPaymentCreditCardList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentCreditCardList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentCreditCardList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the customerPaymentCreditCardList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        // Get all the customerPaymentCreditCardList where lastEditedWhen is not null
        defaultCustomerPaymentCreditCardShouldBeFound("lastEditedWhen.specified=true");

        // Get all the customerPaymentCreditCardList where lastEditedWhen is null
        defaultCustomerPaymentCreditCardShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCustomerPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);
        CustomerPayment customerPayment = CustomerPaymentResourceIT.createEntity(em);
        em.persist(customerPayment);
        em.flush();
        customerPaymentCreditCard.setCustomerPayment(customerPayment);
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);
        Long customerPaymentId = customerPayment.getId();

        // Get all the customerPaymentCreditCardList where customerPayment equals to customerPaymentId
        defaultCustomerPaymentCreditCardShouldBeFound("customerPaymentId.equals=" + customerPaymentId);

        // Get all the customerPaymentCreditCardList where customerPayment equals to customerPaymentId + 1
        defaultCustomerPaymentCreditCardShouldNotBeFound("customerPaymentId.equals=" + (customerPaymentId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentCreditCardsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);
        Currency currency = CurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        customerPaymentCreditCard.setCurrency(currency);
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);
        Long currencyId = currency.getId();

        // Get all the customerPaymentCreditCardList where currency equals to currencyId
        defaultCustomerPaymentCreditCardShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the customerPaymentCreditCardList where currency equals to currencyId + 1
        defaultCustomerPaymentCreditCardShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerPaymentCreditCardShouldBeFound(String filter) throws Exception {
        restCustomerPaymentCreditCardMockMvc.perform(get("/api/customer-payment-credit-cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentCreditCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditCardNumber").value(hasItem(DEFAULT_CREDIT_CARD_NUMBER)))
            .andExpect(jsonPath("$.[*].creditCardExpiryMonth").value(hasItem(DEFAULT_CREDIT_CARD_EXPIRY_MONTH)))
            .andExpect(jsonPath("$.[*].creditCardExpiryYear").value(hasItem(DEFAULT_CREDIT_CARD_EXPIRY_YEAR)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].batchId").value(hasItem(DEFAULT_BATCH_ID)))
            .andExpect(jsonPath("$.[*].responseCode").value(hasItem(DEFAULT_RESPONSE_CODE)))
            .andExpect(jsonPath("$.[*].approvalCode").value(hasItem(DEFAULT_APPROVAL_CODE)))
            .andExpect(jsonPath("$.[*].responseData").value(hasItem(DEFAULT_RESPONSE_DATA.toString())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restCustomerPaymentCreditCardMockMvc.perform(get("/api/customer-payment-credit-cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerPaymentCreditCardShouldNotBeFound(String filter) throws Exception {
        restCustomerPaymentCreditCardMockMvc.perform(get("/api/customer-payment-credit-cards?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerPaymentCreditCardMockMvc.perform(get("/api/customer-payment-credit-cards/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerPaymentCreditCard() throws Exception {
        // Get the customerPaymentCreditCard
        restCustomerPaymentCreditCardMockMvc.perform(get("/api/customer-payment-credit-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPaymentCreditCard() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        int databaseSizeBeforeUpdate = customerPaymentCreditCardRepository.findAll().size();

        // Update the customerPaymentCreditCard
        CustomerPaymentCreditCard updatedCustomerPaymentCreditCard = customerPaymentCreditCardRepository.findById(customerPaymentCreditCard.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPaymentCreditCard are not directly saved in db
        em.detach(updatedCustomerPaymentCreditCard);
        updatedCustomerPaymentCreditCard
            .creditCardNumber(UPDATED_CREDIT_CARD_NUMBER)
            .creditCardExpiryMonth(UPDATED_CREDIT_CARD_EXPIRY_MONTH)
            .creditCardExpiryYear(UPDATED_CREDIT_CARD_EXPIRY_YEAR)
            .amount(UPDATED_AMOUNT)
            .batchId(UPDATED_BATCH_ID)
            .responseCode(UPDATED_RESPONSE_CODE)
            .approvalCode(UPDATED_APPROVAL_CODE)
            .responseData(UPDATED_RESPONSE_DATA)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(updatedCustomerPaymentCreditCard);

        restCustomerPaymentCreditCardMockMvc.perform(put("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerPaymentCreditCard in the database
        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeUpdate);
        CustomerPaymentCreditCard testCustomerPaymentCreditCard = customerPaymentCreditCardList.get(customerPaymentCreditCardList.size() - 1);
        assertThat(testCustomerPaymentCreditCard.getCreditCardNumber()).isEqualTo(UPDATED_CREDIT_CARD_NUMBER);
        assertThat(testCustomerPaymentCreditCard.getCreditCardExpiryMonth()).isEqualTo(UPDATED_CREDIT_CARD_EXPIRY_MONTH);
        assertThat(testCustomerPaymentCreditCard.getCreditCardExpiryYear()).isEqualTo(UPDATED_CREDIT_CARD_EXPIRY_YEAR);
        assertThat(testCustomerPaymentCreditCard.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCustomerPaymentCreditCard.getBatchId()).isEqualTo(UPDATED_BATCH_ID);
        assertThat(testCustomerPaymentCreditCard.getResponseCode()).isEqualTo(UPDATED_RESPONSE_CODE);
        assertThat(testCustomerPaymentCreditCard.getApprovalCode()).isEqualTo(UPDATED_APPROVAL_CODE);
        assertThat(testCustomerPaymentCreditCard.getResponseData()).isEqualTo(UPDATED_RESPONSE_DATA);
        assertThat(testCustomerPaymentCreditCard.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testCustomerPaymentCreditCard.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPaymentCreditCard() throws Exception {
        int databaseSizeBeforeUpdate = customerPaymentCreditCardRepository.findAll().size();

        // Create the CustomerPaymentCreditCard
        CustomerPaymentCreditCardDTO customerPaymentCreditCardDTO = customerPaymentCreditCardMapper.toDto(customerPaymentCreditCard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPaymentCreditCardMockMvc.perform(put("/api/customer-payment-credit-cards").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentCreditCardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentCreditCard in the database
        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPaymentCreditCard() throws Exception {
        // Initialize the database
        customerPaymentCreditCardRepository.saveAndFlush(customerPaymentCreditCard);

        int databaseSizeBeforeDelete = customerPaymentCreditCardRepository.findAll().size();

        // Delete the customerPaymentCreditCard
        restCustomerPaymentCreditCardMockMvc.perform(delete("/api/customer-payment-credit-cards/{id}", customerPaymentCreditCard.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPaymentCreditCard> customerPaymentCreditCardList = customerPaymentCreditCardRepository.findAll();
        assertThat(customerPaymentCreditCardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
