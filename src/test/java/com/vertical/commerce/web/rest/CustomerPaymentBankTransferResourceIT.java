package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CustomerPaymentBankTransfer;
import com.vertical.commerce.domain.CustomerPayment;
import com.vertical.commerce.domain.Currency;
import com.vertical.commerce.repository.CustomerPaymentBankTransferRepository;
import com.vertical.commerce.service.CustomerPaymentBankTransferService;
import com.vertical.commerce.service.dto.CustomerPaymentBankTransferDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentBankTransferMapper;
import com.vertical.commerce.service.dto.CustomerPaymentBankTransferCriteria;
import com.vertical.commerce.service.CustomerPaymentBankTransferQueryService;

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
 * Integration tests for the {@link CustomerPaymentBankTransferResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPaymentBankTransferResourceIT {

    private static final String DEFAULT_RECEIPT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_RECEIPT_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_IN_BANK_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_NAME_IN_BANK_ACCOUNT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_TRANSFER = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_TRANSFER = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_AMOUNT_TRANSFERRED = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_TRANSFERRED = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT_TRANSFERRED = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITY_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITY_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerPaymentBankTransferRepository customerPaymentBankTransferRepository;

    @Autowired
    private CustomerPaymentBankTransferMapper customerPaymentBankTransferMapper;

    @Autowired
    private CustomerPaymentBankTransferService customerPaymentBankTransferService;

    @Autowired
    private CustomerPaymentBankTransferQueryService customerPaymentBankTransferQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPaymentBankTransferMockMvc;

    private CustomerPaymentBankTransfer customerPaymentBankTransfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentBankTransfer createEntity(EntityManager em) {
        CustomerPaymentBankTransfer customerPaymentBankTransfer = new CustomerPaymentBankTransfer()
            .receiptImageUrl(DEFAULT_RECEIPT_IMAGE_URL)
            .nameInBankAccount(DEFAULT_NAME_IN_BANK_ACCOUNT)
            .dateOfTransfer(DEFAULT_DATE_OF_TRANSFER)
            .amountTransferred(DEFAULT_AMOUNT_TRANSFERRED)
            .lastEdityBy(DEFAULT_LAST_EDITY_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return customerPaymentBankTransfer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentBankTransfer createUpdatedEntity(EntityManager em) {
        CustomerPaymentBankTransfer customerPaymentBankTransfer = new CustomerPaymentBankTransfer()
            .receiptImageUrl(UPDATED_RECEIPT_IMAGE_URL)
            .nameInBankAccount(UPDATED_NAME_IN_BANK_ACCOUNT)
            .dateOfTransfer(UPDATED_DATE_OF_TRANSFER)
            .amountTransferred(UPDATED_AMOUNT_TRANSFERRED)
            .lastEdityBy(UPDATED_LAST_EDITY_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return customerPaymentBankTransfer;
    }

    @BeforeEach
    public void initTest() {
        customerPaymentBankTransfer = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPaymentBankTransfer() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentBankTransferRepository.findAll().size();
        // Create the CustomerPaymentBankTransfer
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);
        restCustomerPaymentBankTransferMockMvc.perform(post("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerPaymentBankTransfer in the database
        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPaymentBankTransfer testCustomerPaymentBankTransfer = customerPaymentBankTransferList.get(customerPaymentBankTransferList.size() - 1);
        assertThat(testCustomerPaymentBankTransfer.getReceiptImageUrl()).isEqualTo(DEFAULT_RECEIPT_IMAGE_URL);
        assertThat(testCustomerPaymentBankTransfer.getNameInBankAccount()).isEqualTo(DEFAULT_NAME_IN_BANK_ACCOUNT);
        assertThat(testCustomerPaymentBankTransfer.getDateOfTransfer()).isEqualTo(DEFAULT_DATE_OF_TRANSFER);
        assertThat(testCustomerPaymentBankTransfer.getAmountTransferred()).isEqualTo(DEFAULT_AMOUNT_TRANSFERRED);
        assertThat(testCustomerPaymentBankTransfer.getLastEdityBy()).isEqualTo(DEFAULT_LAST_EDITY_BY);
        assertThat(testCustomerPaymentBankTransfer.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createCustomerPaymentBankTransferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentBankTransferRepository.findAll().size();

        // Create the CustomerPaymentBankTransfer with an existing ID
        customerPaymentBankTransfer.setId(1L);
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPaymentBankTransferMockMvc.perform(post("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentBankTransfer in the database
        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkReceiptImageUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentBankTransferRepository.findAll().size();
        // set the field null
        customerPaymentBankTransfer.setReceiptImageUrl(null);

        // Create the CustomerPaymentBankTransfer, which fails.
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);


        restCustomerPaymentBankTransferMockMvc.perform(post("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameInBankAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentBankTransferRepository.findAll().size();
        // set the field null
        customerPaymentBankTransfer.setNameInBankAccount(null);

        // Create the CustomerPaymentBankTransfer, which fails.
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);


        restCustomerPaymentBankTransferMockMvc.perform(post("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateOfTransferIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentBankTransferRepository.findAll().size();
        // set the field null
        customerPaymentBankTransfer.setDateOfTransfer(null);

        // Create the CustomerPaymentBankTransfer, which fails.
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);


        restCustomerPaymentBankTransferMockMvc.perform(post("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountTransferredIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentBankTransferRepository.findAll().size();
        // set the field null
        customerPaymentBankTransfer.setAmountTransferred(null);

        // Create the CustomerPaymentBankTransfer, which fails.
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);


        restCustomerPaymentBankTransferMockMvc.perform(post("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEdityByIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentBankTransferRepository.findAll().size();
        // set the field null
        customerPaymentBankTransfer.setLastEdityBy(null);

        // Create the CustomerPaymentBankTransfer, which fails.
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);


        restCustomerPaymentBankTransferMockMvc.perform(post("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentBankTransferRepository.findAll().size();
        // set the field null
        customerPaymentBankTransfer.setLastEditedWhen(null);

        // Create the CustomerPaymentBankTransfer, which fails.
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);


        restCustomerPaymentBankTransferMockMvc.perform(post("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfers() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList
        restCustomerPaymentBankTransferMockMvc.perform(get("/api/customer-payment-bank-transfers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentBankTransfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiptImageUrl").value(hasItem(DEFAULT_RECEIPT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].nameInBankAccount").value(hasItem(DEFAULT_NAME_IN_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].dateOfTransfer").value(hasItem(DEFAULT_DATE_OF_TRANSFER.toString())))
            .andExpect(jsonPath("$.[*].amountTransferred").value(hasItem(DEFAULT_AMOUNT_TRANSFERRED.intValue())))
            .andExpect(jsonPath("$.[*].lastEdityBy").value(hasItem(DEFAULT_LAST_EDITY_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerPaymentBankTransfer() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get the customerPaymentBankTransfer
        restCustomerPaymentBankTransferMockMvc.perform(get("/api/customer-payment-bank-transfers/{id}", customerPaymentBankTransfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPaymentBankTransfer.getId().intValue()))
            .andExpect(jsonPath("$.receiptImageUrl").value(DEFAULT_RECEIPT_IMAGE_URL))
            .andExpect(jsonPath("$.nameInBankAccount").value(DEFAULT_NAME_IN_BANK_ACCOUNT))
            .andExpect(jsonPath("$.dateOfTransfer").value(DEFAULT_DATE_OF_TRANSFER.toString()))
            .andExpect(jsonPath("$.amountTransferred").value(DEFAULT_AMOUNT_TRANSFERRED.intValue()))
            .andExpect(jsonPath("$.lastEdityBy").value(DEFAULT_LAST_EDITY_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getCustomerPaymentBankTransfersByIdFiltering() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        Long id = customerPaymentBankTransfer.getId();

        defaultCustomerPaymentBankTransferShouldBeFound("id.equals=" + id);
        defaultCustomerPaymentBankTransferShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerPaymentBankTransferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerPaymentBankTransferShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerPaymentBankTransferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerPaymentBankTransferShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByReceiptImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where receiptImageUrl equals to DEFAULT_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldBeFound("receiptImageUrl.equals=" + DEFAULT_RECEIPT_IMAGE_URL);

        // Get all the customerPaymentBankTransferList where receiptImageUrl equals to UPDATED_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldNotBeFound("receiptImageUrl.equals=" + UPDATED_RECEIPT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByReceiptImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where receiptImageUrl not equals to DEFAULT_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldNotBeFound("receiptImageUrl.notEquals=" + DEFAULT_RECEIPT_IMAGE_URL);

        // Get all the customerPaymentBankTransferList where receiptImageUrl not equals to UPDATED_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldBeFound("receiptImageUrl.notEquals=" + UPDATED_RECEIPT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByReceiptImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where receiptImageUrl in DEFAULT_RECEIPT_IMAGE_URL or UPDATED_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldBeFound("receiptImageUrl.in=" + DEFAULT_RECEIPT_IMAGE_URL + "," + UPDATED_RECEIPT_IMAGE_URL);

        // Get all the customerPaymentBankTransferList where receiptImageUrl equals to UPDATED_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldNotBeFound("receiptImageUrl.in=" + UPDATED_RECEIPT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByReceiptImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where receiptImageUrl is not null
        defaultCustomerPaymentBankTransferShouldBeFound("receiptImageUrl.specified=true");

        // Get all the customerPaymentBankTransferList where receiptImageUrl is null
        defaultCustomerPaymentBankTransferShouldNotBeFound("receiptImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByReceiptImageUrlContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where receiptImageUrl contains DEFAULT_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldBeFound("receiptImageUrl.contains=" + DEFAULT_RECEIPT_IMAGE_URL);

        // Get all the customerPaymentBankTransferList where receiptImageUrl contains UPDATED_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldNotBeFound("receiptImageUrl.contains=" + UPDATED_RECEIPT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByReceiptImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where receiptImageUrl does not contain DEFAULT_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldNotBeFound("receiptImageUrl.doesNotContain=" + DEFAULT_RECEIPT_IMAGE_URL);

        // Get all the customerPaymentBankTransferList where receiptImageUrl does not contain UPDATED_RECEIPT_IMAGE_URL
        defaultCustomerPaymentBankTransferShouldBeFound("receiptImageUrl.doesNotContain=" + UPDATED_RECEIPT_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByNameInBankAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where nameInBankAccount equals to DEFAULT_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldBeFound("nameInBankAccount.equals=" + DEFAULT_NAME_IN_BANK_ACCOUNT);

        // Get all the customerPaymentBankTransferList where nameInBankAccount equals to UPDATED_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldNotBeFound("nameInBankAccount.equals=" + UPDATED_NAME_IN_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByNameInBankAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where nameInBankAccount not equals to DEFAULT_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldNotBeFound("nameInBankAccount.notEquals=" + DEFAULT_NAME_IN_BANK_ACCOUNT);

        // Get all the customerPaymentBankTransferList where nameInBankAccount not equals to UPDATED_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldBeFound("nameInBankAccount.notEquals=" + UPDATED_NAME_IN_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByNameInBankAccountIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where nameInBankAccount in DEFAULT_NAME_IN_BANK_ACCOUNT or UPDATED_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldBeFound("nameInBankAccount.in=" + DEFAULT_NAME_IN_BANK_ACCOUNT + "," + UPDATED_NAME_IN_BANK_ACCOUNT);

        // Get all the customerPaymentBankTransferList where nameInBankAccount equals to UPDATED_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldNotBeFound("nameInBankAccount.in=" + UPDATED_NAME_IN_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByNameInBankAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where nameInBankAccount is not null
        defaultCustomerPaymentBankTransferShouldBeFound("nameInBankAccount.specified=true");

        // Get all the customerPaymentBankTransferList where nameInBankAccount is null
        defaultCustomerPaymentBankTransferShouldNotBeFound("nameInBankAccount.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByNameInBankAccountContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where nameInBankAccount contains DEFAULT_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldBeFound("nameInBankAccount.contains=" + DEFAULT_NAME_IN_BANK_ACCOUNT);

        // Get all the customerPaymentBankTransferList where nameInBankAccount contains UPDATED_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldNotBeFound("nameInBankAccount.contains=" + UPDATED_NAME_IN_BANK_ACCOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByNameInBankAccountNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where nameInBankAccount does not contain DEFAULT_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldNotBeFound("nameInBankAccount.doesNotContain=" + DEFAULT_NAME_IN_BANK_ACCOUNT);

        // Get all the customerPaymentBankTransferList where nameInBankAccount does not contain UPDATED_NAME_IN_BANK_ACCOUNT
        defaultCustomerPaymentBankTransferShouldBeFound("nameInBankAccount.doesNotContain=" + UPDATED_NAME_IN_BANK_ACCOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByDateOfTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where dateOfTransfer equals to DEFAULT_DATE_OF_TRANSFER
        defaultCustomerPaymentBankTransferShouldBeFound("dateOfTransfer.equals=" + DEFAULT_DATE_OF_TRANSFER);

        // Get all the customerPaymentBankTransferList where dateOfTransfer equals to UPDATED_DATE_OF_TRANSFER
        defaultCustomerPaymentBankTransferShouldNotBeFound("dateOfTransfer.equals=" + UPDATED_DATE_OF_TRANSFER);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByDateOfTransferIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where dateOfTransfer not equals to DEFAULT_DATE_OF_TRANSFER
        defaultCustomerPaymentBankTransferShouldNotBeFound("dateOfTransfer.notEquals=" + DEFAULT_DATE_OF_TRANSFER);

        // Get all the customerPaymentBankTransferList where dateOfTransfer not equals to UPDATED_DATE_OF_TRANSFER
        defaultCustomerPaymentBankTransferShouldBeFound("dateOfTransfer.notEquals=" + UPDATED_DATE_OF_TRANSFER);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByDateOfTransferIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where dateOfTransfer in DEFAULT_DATE_OF_TRANSFER or UPDATED_DATE_OF_TRANSFER
        defaultCustomerPaymentBankTransferShouldBeFound("dateOfTransfer.in=" + DEFAULT_DATE_OF_TRANSFER + "," + UPDATED_DATE_OF_TRANSFER);

        // Get all the customerPaymentBankTransferList where dateOfTransfer equals to UPDATED_DATE_OF_TRANSFER
        defaultCustomerPaymentBankTransferShouldNotBeFound("dateOfTransfer.in=" + UPDATED_DATE_OF_TRANSFER);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByDateOfTransferIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where dateOfTransfer is not null
        defaultCustomerPaymentBankTransferShouldBeFound("dateOfTransfer.specified=true");

        // Get all the customerPaymentBankTransferList where dateOfTransfer is null
        defaultCustomerPaymentBankTransferShouldNotBeFound("dateOfTransfer.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByAmountTransferredIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where amountTransferred equals to DEFAULT_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldBeFound("amountTransferred.equals=" + DEFAULT_AMOUNT_TRANSFERRED);

        // Get all the customerPaymentBankTransferList where amountTransferred equals to UPDATED_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldNotBeFound("amountTransferred.equals=" + UPDATED_AMOUNT_TRANSFERRED);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByAmountTransferredIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where amountTransferred not equals to DEFAULT_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldNotBeFound("amountTransferred.notEquals=" + DEFAULT_AMOUNT_TRANSFERRED);

        // Get all the customerPaymentBankTransferList where amountTransferred not equals to UPDATED_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldBeFound("amountTransferred.notEquals=" + UPDATED_AMOUNT_TRANSFERRED);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByAmountTransferredIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where amountTransferred in DEFAULT_AMOUNT_TRANSFERRED or UPDATED_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldBeFound("amountTransferred.in=" + DEFAULT_AMOUNT_TRANSFERRED + "," + UPDATED_AMOUNT_TRANSFERRED);

        // Get all the customerPaymentBankTransferList where amountTransferred equals to UPDATED_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldNotBeFound("amountTransferred.in=" + UPDATED_AMOUNT_TRANSFERRED);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByAmountTransferredIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where amountTransferred is not null
        defaultCustomerPaymentBankTransferShouldBeFound("amountTransferred.specified=true");

        // Get all the customerPaymentBankTransferList where amountTransferred is null
        defaultCustomerPaymentBankTransferShouldNotBeFound("amountTransferred.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByAmountTransferredIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where amountTransferred is greater than or equal to DEFAULT_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldBeFound("amountTransferred.greaterThanOrEqual=" + DEFAULT_AMOUNT_TRANSFERRED);

        // Get all the customerPaymentBankTransferList where amountTransferred is greater than or equal to UPDATED_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldNotBeFound("amountTransferred.greaterThanOrEqual=" + UPDATED_AMOUNT_TRANSFERRED);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByAmountTransferredIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where amountTransferred is less than or equal to DEFAULT_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldBeFound("amountTransferred.lessThanOrEqual=" + DEFAULT_AMOUNT_TRANSFERRED);

        // Get all the customerPaymentBankTransferList where amountTransferred is less than or equal to SMALLER_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldNotBeFound("amountTransferred.lessThanOrEqual=" + SMALLER_AMOUNT_TRANSFERRED);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByAmountTransferredIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where amountTransferred is less than DEFAULT_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldNotBeFound("amountTransferred.lessThan=" + DEFAULT_AMOUNT_TRANSFERRED);

        // Get all the customerPaymentBankTransferList where amountTransferred is less than UPDATED_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldBeFound("amountTransferred.lessThan=" + UPDATED_AMOUNT_TRANSFERRED);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByAmountTransferredIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where amountTransferred is greater than DEFAULT_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldNotBeFound("amountTransferred.greaterThan=" + DEFAULT_AMOUNT_TRANSFERRED);

        // Get all the customerPaymentBankTransferList where amountTransferred is greater than SMALLER_AMOUNT_TRANSFERRED
        defaultCustomerPaymentBankTransferShouldBeFound("amountTransferred.greaterThan=" + SMALLER_AMOUNT_TRANSFERRED);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEdityByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEdityBy equals to DEFAULT_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldBeFound("lastEdityBy.equals=" + DEFAULT_LAST_EDITY_BY);

        // Get all the customerPaymentBankTransferList where lastEdityBy equals to UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEdityBy.equals=" + UPDATED_LAST_EDITY_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEdityByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEdityBy not equals to DEFAULT_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEdityBy.notEquals=" + DEFAULT_LAST_EDITY_BY);

        // Get all the customerPaymentBankTransferList where lastEdityBy not equals to UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldBeFound("lastEdityBy.notEquals=" + UPDATED_LAST_EDITY_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEdityByIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEdityBy in DEFAULT_LAST_EDITY_BY or UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldBeFound("lastEdityBy.in=" + DEFAULT_LAST_EDITY_BY + "," + UPDATED_LAST_EDITY_BY);

        // Get all the customerPaymentBankTransferList where lastEdityBy equals to UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEdityBy.in=" + UPDATED_LAST_EDITY_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEdityByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEdityBy is not null
        defaultCustomerPaymentBankTransferShouldBeFound("lastEdityBy.specified=true");

        // Get all the customerPaymentBankTransferList where lastEdityBy is null
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEdityBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEdityByContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEdityBy contains DEFAULT_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldBeFound("lastEdityBy.contains=" + DEFAULT_LAST_EDITY_BY);

        // Get all the customerPaymentBankTransferList where lastEdityBy contains UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEdityBy.contains=" + UPDATED_LAST_EDITY_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEdityByNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEdityBy does not contain DEFAULT_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEdityBy.doesNotContain=" + DEFAULT_LAST_EDITY_BY);

        // Get all the customerPaymentBankTransferList where lastEdityBy does not contain UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentBankTransferShouldBeFound("lastEdityBy.doesNotContain=" + UPDATED_LAST_EDITY_BY);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentBankTransferShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentBankTransferList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentBankTransferList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentBankTransferShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentBankTransferShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the customerPaymentBankTransferList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        // Get all the customerPaymentBankTransferList where lastEditedWhen is not null
        defaultCustomerPaymentBankTransferShouldBeFound("lastEditedWhen.specified=true");

        // Get all the customerPaymentBankTransferList where lastEditedWhen is null
        defaultCustomerPaymentBankTransferShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByCustomerPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);
        CustomerPayment customerPayment = CustomerPaymentResourceIT.createEntity(em);
        em.persist(customerPayment);
        em.flush();
        customerPaymentBankTransfer.setCustomerPayment(customerPayment);
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);
        Long customerPaymentId = customerPayment.getId();

        // Get all the customerPaymentBankTransferList where customerPayment equals to customerPaymentId
        defaultCustomerPaymentBankTransferShouldBeFound("customerPaymentId.equals=" + customerPaymentId);

        // Get all the customerPaymentBankTransferList where customerPayment equals to customerPaymentId + 1
        defaultCustomerPaymentBankTransferShouldNotBeFound("customerPaymentId.equals=" + (customerPaymentId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentBankTransfersByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);
        Currency currency = CurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        customerPaymentBankTransfer.setCurrency(currency);
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);
        Long currencyId = currency.getId();

        // Get all the customerPaymentBankTransferList where currency equals to currencyId
        defaultCustomerPaymentBankTransferShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the customerPaymentBankTransferList where currency equals to currencyId + 1
        defaultCustomerPaymentBankTransferShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerPaymentBankTransferShouldBeFound(String filter) throws Exception {
        restCustomerPaymentBankTransferMockMvc.perform(get("/api/customer-payment-bank-transfers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentBankTransfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].receiptImageUrl").value(hasItem(DEFAULT_RECEIPT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].nameInBankAccount").value(hasItem(DEFAULT_NAME_IN_BANK_ACCOUNT)))
            .andExpect(jsonPath("$.[*].dateOfTransfer").value(hasItem(DEFAULT_DATE_OF_TRANSFER.toString())))
            .andExpect(jsonPath("$.[*].amountTransferred").value(hasItem(DEFAULT_AMOUNT_TRANSFERRED.intValue())))
            .andExpect(jsonPath("$.[*].lastEdityBy").value(hasItem(DEFAULT_LAST_EDITY_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restCustomerPaymentBankTransferMockMvc.perform(get("/api/customer-payment-bank-transfers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerPaymentBankTransferShouldNotBeFound(String filter) throws Exception {
        restCustomerPaymentBankTransferMockMvc.perform(get("/api/customer-payment-bank-transfers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerPaymentBankTransferMockMvc.perform(get("/api/customer-payment-bank-transfers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerPaymentBankTransfer() throws Exception {
        // Get the customerPaymentBankTransfer
        restCustomerPaymentBankTransferMockMvc.perform(get("/api/customer-payment-bank-transfers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPaymentBankTransfer() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        int databaseSizeBeforeUpdate = customerPaymentBankTransferRepository.findAll().size();

        // Update the customerPaymentBankTransfer
        CustomerPaymentBankTransfer updatedCustomerPaymentBankTransfer = customerPaymentBankTransferRepository.findById(customerPaymentBankTransfer.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPaymentBankTransfer are not directly saved in db
        em.detach(updatedCustomerPaymentBankTransfer);
        updatedCustomerPaymentBankTransfer
            .receiptImageUrl(UPDATED_RECEIPT_IMAGE_URL)
            .nameInBankAccount(UPDATED_NAME_IN_BANK_ACCOUNT)
            .dateOfTransfer(UPDATED_DATE_OF_TRANSFER)
            .amountTransferred(UPDATED_AMOUNT_TRANSFERRED)
            .lastEdityBy(UPDATED_LAST_EDITY_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(updatedCustomerPaymentBankTransfer);

        restCustomerPaymentBankTransferMockMvc.perform(put("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerPaymentBankTransfer in the database
        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeUpdate);
        CustomerPaymentBankTransfer testCustomerPaymentBankTransfer = customerPaymentBankTransferList.get(customerPaymentBankTransferList.size() - 1);
        assertThat(testCustomerPaymentBankTransfer.getReceiptImageUrl()).isEqualTo(UPDATED_RECEIPT_IMAGE_URL);
        assertThat(testCustomerPaymentBankTransfer.getNameInBankAccount()).isEqualTo(UPDATED_NAME_IN_BANK_ACCOUNT);
        assertThat(testCustomerPaymentBankTransfer.getDateOfTransfer()).isEqualTo(UPDATED_DATE_OF_TRANSFER);
        assertThat(testCustomerPaymentBankTransfer.getAmountTransferred()).isEqualTo(UPDATED_AMOUNT_TRANSFERRED);
        assertThat(testCustomerPaymentBankTransfer.getLastEdityBy()).isEqualTo(UPDATED_LAST_EDITY_BY);
        assertThat(testCustomerPaymentBankTransfer.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPaymentBankTransfer() throws Exception {
        int databaseSizeBeforeUpdate = customerPaymentBankTransferRepository.findAll().size();

        // Create the CustomerPaymentBankTransfer
        CustomerPaymentBankTransferDTO customerPaymentBankTransferDTO = customerPaymentBankTransferMapper.toDto(customerPaymentBankTransfer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPaymentBankTransferMockMvc.perform(put("/api/customer-payment-bank-transfers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentBankTransferDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentBankTransfer in the database
        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPaymentBankTransfer() throws Exception {
        // Initialize the database
        customerPaymentBankTransferRepository.saveAndFlush(customerPaymentBankTransfer);

        int databaseSizeBeforeDelete = customerPaymentBankTransferRepository.findAll().size();

        // Delete the customerPaymentBankTransfer
        restCustomerPaymentBankTransferMockMvc.perform(delete("/api/customer-payment-bank-transfers/{id}", customerPaymentBankTransfer.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPaymentBankTransfer> customerPaymentBankTransferList = customerPaymentBankTransferRepository.findAll();
        assertThat(customerPaymentBankTransferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
