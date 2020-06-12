package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.CustomerPaymentVoucher;
import com.vertical.commerce.domain.CustomerPayment;
import com.vertical.commerce.domain.Currency;
import com.vertical.commerce.repository.CustomerPaymentVoucherRepository;
import com.vertical.commerce.service.CustomerPaymentVoucherService;
import com.vertical.commerce.service.dto.CustomerPaymentVoucherDTO;
import com.vertical.commerce.service.mapper.CustomerPaymentVoucherMapper;
import com.vertical.commerce.service.dto.CustomerPaymentVoucherCriteria;
import com.vertical.commerce.service.CustomerPaymentVoucherQueryService;

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
 * Integration tests for the {@link CustomerPaymentVoucherResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class CustomerPaymentVoucherResourceIT {

    private static final String DEFAULT_SERIAL_NO = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_LAST_EDITY_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITY_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private CustomerPaymentVoucherRepository customerPaymentVoucherRepository;

    @Autowired
    private CustomerPaymentVoucherMapper customerPaymentVoucherMapper;

    @Autowired
    private CustomerPaymentVoucherService customerPaymentVoucherService;

    @Autowired
    private CustomerPaymentVoucherQueryService customerPaymentVoucherQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCustomerPaymentVoucherMockMvc;

    private CustomerPaymentVoucher customerPaymentVoucher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentVoucher createEntity(EntityManager em) {
        CustomerPaymentVoucher customerPaymentVoucher = new CustomerPaymentVoucher()
            .serialNo(DEFAULT_SERIAL_NO)
            .amount(DEFAULT_AMOUNT)
            .lastEdityBy(DEFAULT_LAST_EDITY_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN);
        return customerPaymentVoucher;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CustomerPaymentVoucher createUpdatedEntity(EntityManager em) {
        CustomerPaymentVoucher customerPaymentVoucher = new CustomerPaymentVoucher()
            .serialNo(UPDATED_SERIAL_NO)
            .amount(UPDATED_AMOUNT)
            .lastEdityBy(UPDATED_LAST_EDITY_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        return customerPaymentVoucher;
    }

    @BeforeEach
    public void initTest() {
        customerPaymentVoucher = createEntity(em);
    }

    @Test
    @Transactional
    public void createCustomerPaymentVoucher() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentVoucherRepository.findAll().size();
        // Create the CustomerPaymentVoucher
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO = customerPaymentVoucherMapper.toDto(customerPaymentVoucher);
        restCustomerPaymentVoucherMockMvc.perform(post("/api/customer-payment-vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentVoucherDTO)))
            .andExpect(status().isCreated());

        // Validate the CustomerPaymentVoucher in the database
        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeCreate + 1);
        CustomerPaymentVoucher testCustomerPaymentVoucher = customerPaymentVoucherList.get(customerPaymentVoucherList.size() - 1);
        assertThat(testCustomerPaymentVoucher.getSerialNo()).isEqualTo(DEFAULT_SERIAL_NO);
        assertThat(testCustomerPaymentVoucher.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testCustomerPaymentVoucher.getLastEdityBy()).isEqualTo(DEFAULT_LAST_EDITY_BY);
        assertThat(testCustomerPaymentVoucher.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void createCustomerPaymentVoucherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = customerPaymentVoucherRepository.findAll().size();

        // Create the CustomerPaymentVoucher with an existing ID
        customerPaymentVoucher.setId(1L);
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO = customerPaymentVoucherMapper.toDto(customerPaymentVoucher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCustomerPaymentVoucherMockMvc.perform(post("/api/customer-payment-vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentVoucher in the database
        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSerialNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentVoucherRepository.findAll().size();
        // set the field null
        customerPaymentVoucher.setSerialNo(null);

        // Create the CustomerPaymentVoucher, which fails.
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO = customerPaymentVoucherMapper.toDto(customerPaymentVoucher);


        restCustomerPaymentVoucherMockMvc.perform(post("/api/customer-payment-vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentVoucherDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentVoucherRepository.findAll().size();
        // set the field null
        customerPaymentVoucher.setAmount(null);

        // Create the CustomerPaymentVoucher, which fails.
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO = customerPaymentVoucherMapper.toDto(customerPaymentVoucher);


        restCustomerPaymentVoucherMockMvc.perform(post("/api/customer-payment-vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentVoucherDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEdityByIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentVoucherRepository.findAll().size();
        // set the field null
        customerPaymentVoucher.setLastEdityBy(null);

        // Create the CustomerPaymentVoucher, which fails.
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO = customerPaymentVoucherMapper.toDto(customerPaymentVoucher);


        restCustomerPaymentVoucherMockMvc.perform(post("/api/customer-payment-vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentVoucherDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = customerPaymentVoucherRepository.findAll().size();
        // set the field null
        customerPaymentVoucher.setLastEditedWhen(null);

        // Create the CustomerPaymentVoucher, which fails.
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO = customerPaymentVoucherMapper.toDto(customerPaymentVoucher);


        restCustomerPaymentVoucherMockMvc.perform(post("/api/customer-payment-vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentVoucherDTO)))
            .andExpect(status().isBadRequest());

        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchers() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList
        restCustomerPaymentVoucherMockMvc.perform(get("/api/customer-payment-vouchers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lastEdityBy").value(hasItem(DEFAULT_LAST_EDITY_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));
    }
    
    @Test
    @Transactional
    public void getCustomerPaymentVoucher() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get the customerPaymentVoucher
        restCustomerPaymentVoucherMockMvc.perform(get("/api/customer-payment-vouchers/{id}", customerPaymentVoucher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(customerPaymentVoucher.getId().intValue()))
            .andExpect(jsonPath("$.serialNo").value(DEFAULT_SERIAL_NO))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.lastEdityBy").value(DEFAULT_LAST_EDITY_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()));
    }


    @Test
    @Transactional
    public void getCustomerPaymentVouchersByIdFiltering() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        Long id = customerPaymentVoucher.getId();

        defaultCustomerPaymentVoucherShouldBeFound("id.equals=" + id);
        defaultCustomerPaymentVoucherShouldNotBeFound("id.notEquals=" + id);

        defaultCustomerPaymentVoucherShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCustomerPaymentVoucherShouldNotBeFound("id.greaterThan=" + id);

        defaultCustomerPaymentVoucherShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCustomerPaymentVoucherShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersBySerialNoIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where serialNo equals to DEFAULT_SERIAL_NO
        defaultCustomerPaymentVoucherShouldBeFound("serialNo.equals=" + DEFAULT_SERIAL_NO);

        // Get all the customerPaymentVoucherList where serialNo equals to UPDATED_SERIAL_NO
        defaultCustomerPaymentVoucherShouldNotBeFound("serialNo.equals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersBySerialNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where serialNo not equals to DEFAULT_SERIAL_NO
        defaultCustomerPaymentVoucherShouldNotBeFound("serialNo.notEquals=" + DEFAULT_SERIAL_NO);

        // Get all the customerPaymentVoucherList where serialNo not equals to UPDATED_SERIAL_NO
        defaultCustomerPaymentVoucherShouldBeFound("serialNo.notEquals=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersBySerialNoIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where serialNo in DEFAULT_SERIAL_NO or UPDATED_SERIAL_NO
        defaultCustomerPaymentVoucherShouldBeFound("serialNo.in=" + DEFAULT_SERIAL_NO + "," + UPDATED_SERIAL_NO);

        // Get all the customerPaymentVoucherList where serialNo equals to UPDATED_SERIAL_NO
        defaultCustomerPaymentVoucherShouldNotBeFound("serialNo.in=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersBySerialNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where serialNo is not null
        defaultCustomerPaymentVoucherShouldBeFound("serialNo.specified=true");

        // Get all the customerPaymentVoucherList where serialNo is null
        defaultCustomerPaymentVoucherShouldNotBeFound("serialNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentVouchersBySerialNoContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where serialNo contains DEFAULT_SERIAL_NO
        defaultCustomerPaymentVoucherShouldBeFound("serialNo.contains=" + DEFAULT_SERIAL_NO);

        // Get all the customerPaymentVoucherList where serialNo contains UPDATED_SERIAL_NO
        defaultCustomerPaymentVoucherShouldNotBeFound("serialNo.contains=" + UPDATED_SERIAL_NO);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersBySerialNoNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where serialNo does not contain DEFAULT_SERIAL_NO
        defaultCustomerPaymentVoucherShouldNotBeFound("serialNo.doesNotContain=" + DEFAULT_SERIAL_NO);

        // Get all the customerPaymentVoucherList where serialNo does not contain UPDATED_SERIAL_NO
        defaultCustomerPaymentVoucherShouldBeFound("serialNo.doesNotContain=" + UPDATED_SERIAL_NO);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where amount equals to DEFAULT_AMOUNT
        defaultCustomerPaymentVoucherShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentVoucherList where amount equals to UPDATED_AMOUNT
        defaultCustomerPaymentVoucherShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where amount not equals to DEFAULT_AMOUNT
        defaultCustomerPaymentVoucherShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentVoucherList where amount not equals to UPDATED_AMOUNT
        defaultCustomerPaymentVoucherShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultCustomerPaymentVoucherShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the customerPaymentVoucherList where amount equals to UPDATED_AMOUNT
        defaultCustomerPaymentVoucherShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where amount is not null
        defaultCustomerPaymentVoucherShouldBeFound("amount.specified=true");

        // Get all the customerPaymentVoucherList where amount is null
        defaultCustomerPaymentVoucherShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultCustomerPaymentVoucherShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentVoucherList where amount is greater than or equal to UPDATED_AMOUNT
        defaultCustomerPaymentVoucherShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where amount is less than or equal to DEFAULT_AMOUNT
        defaultCustomerPaymentVoucherShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentVoucherList where amount is less than or equal to SMALLER_AMOUNT
        defaultCustomerPaymentVoucherShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where amount is less than DEFAULT_AMOUNT
        defaultCustomerPaymentVoucherShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentVoucherList where amount is less than UPDATED_AMOUNT
        defaultCustomerPaymentVoucherShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where amount is greater than DEFAULT_AMOUNT
        defaultCustomerPaymentVoucherShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the customerPaymentVoucherList where amount is greater than SMALLER_AMOUNT
        defaultCustomerPaymentVoucherShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEdityByIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEdityBy equals to DEFAULT_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldBeFound("lastEdityBy.equals=" + DEFAULT_LAST_EDITY_BY);

        // Get all the customerPaymentVoucherList where lastEdityBy equals to UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEdityBy.equals=" + UPDATED_LAST_EDITY_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEdityByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEdityBy not equals to DEFAULT_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEdityBy.notEquals=" + DEFAULT_LAST_EDITY_BY);

        // Get all the customerPaymentVoucherList where lastEdityBy not equals to UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldBeFound("lastEdityBy.notEquals=" + UPDATED_LAST_EDITY_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEdityByIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEdityBy in DEFAULT_LAST_EDITY_BY or UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldBeFound("lastEdityBy.in=" + DEFAULT_LAST_EDITY_BY + "," + UPDATED_LAST_EDITY_BY);

        // Get all the customerPaymentVoucherList where lastEdityBy equals to UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEdityBy.in=" + UPDATED_LAST_EDITY_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEdityByIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEdityBy is not null
        defaultCustomerPaymentVoucherShouldBeFound("lastEdityBy.specified=true");

        // Get all the customerPaymentVoucherList where lastEdityBy is null
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEdityBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEdityByContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEdityBy contains DEFAULT_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldBeFound("lastEdityBy.contains=" + DEFAULT_LAST_EDITY_BY);

        // Get all the customerPaymentVoucherList where lastEdityBy contains UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEdityBy.contains=" + UPDATED_LAST_EDITY_BY);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEdityByNotContainsSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEdityBy does not contain DEFAULT_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEdityBy.doesNotContain=" + DEFAULT_LAST_EDITY_BY);

        // Get all the customerPaymentVoucherList where lastEdityBy does not contain UPDATED_LAST_EDITY_BY
        defaultCustomerPaymentVoucherShouldBeFound("lastEdityBy.doesNotContain=" + UPDATED_LAST_EDITY_BY);
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentVoucherShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentVoucherList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the customerPaymentVoucherList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentVoucherShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentVoucherShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the customerPaymentVoucherList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        // Get all the customerPaymentVoucherList where lastEditedWhen is not null
        defaultCustomerPaymentVoucherShouldBeFound("lastEditedWhen.specified=true");

        // Get all the customerPaymentVoucherList where lastEditedWhen is null
        defaultCustomerPaymentVoucherShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByCustomerPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);
        CustomerPayment customerPayment = CustomerPaymentResourceIT.createEntity(em);
        em.persist(customerPayment);
        em.flush();
        customerPaymentVoucher.setCustomerPayment(customerPayment);
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);
        Long customerPaymentId = customerPayment.getId();

        // Get all the customerPaymentVoucherList where customerPayment equals to customerPaymentId
        defaultCustomerPaymentVoucherShouldBeFound("customerPaymentId.equals=" + customerPaymentId);

        // Get all the customerPaymentVoucherList where customerPayment equals to customerPaymentId + 1
        defaultCustomerPaymentVoucherShouldNotBeFound("customerPaymentId.equals=" + (customerPaymentId + 1));
    }


    @Test
    @Transactional
    public void getAllCustomerPaymentVouchersByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);
        Currency currency = CurrencyResourceIT.createEntity(em);
        em.persist(currency);
        em.flush();
        customerPaymentVoucher.setCurrency(currency);
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);
        Long currencyId = currency.getId();

        // Get all the customerPaymentVoucherList where currency equals to currencyId
        defaultCustomerPaymentVoucherShouldBeFound("currencyId.equals=" + currencyId);

        // Get all the customerPaymentVoucherList where currency equals to currencyId + 1
        defaultCustomerPaymentVoucherShouldNotBeFound("currencyId.equals=" + (currencyId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCustomerPaymentVoucherShouldBeFound(String filter) throws Exception {
        restCustomerPaymentVoucherMockMvc.perform(get("/api/customer-payment-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(customerPaymentVoucher.getId().intValue())))
            .andExpect(jsonPath("$.[*].serialNo").value(hasItem(DEFAULT_SERIAL_NO)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].lastEdityBy").value(hasItem(DEFAULT_LAST_EDITY_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())));

        // Check, that the count call also returns 1
        restCustomerPaymentVoucherMockMvc.perform(get("/api/customer-payment-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCustomerPaymentVoucherShouldNotBeFound(String filter) throws Exception {
        restCustomerPaymentVoucherMockMvc.perform(get("/api/customer-payment-vouchers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCustomerPaymentVoucherMockMvc.perform(get("/api/customer-payment-vouchers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCustomerPaymentVoucher() throws Exception {
        // Get the customerPaymentVoucher
        restCustomerPaymentVoucherMockMvc.perform(get("/api/customer-payment-vouchers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCustomerPaymentVoucher() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        int databaseSizeBeforeUpdate = customerPaymentVoucherRepository.findAll().size();

        // Update the customerPaymentVoucher
        CustomerPaymentVoucher updatedCustomerPaymentVoucher = customerPaymentVoucherRepository.findById(customerPaymentVoucher.getId()).get();
        // Disconnect from session so that the updates on updatedCustomerPaymentVoucher are not directly saved in db
        em.detach(updatedCustomerPaymentVoucher);
        updatedCustomerPaymentVoucher
            .serialNo(UPDATED_SERIAL_NO)
            .amount(UPDATED_AMOUNT)
            .lastEdityBy(UPDATED_LAST_EDITY_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN);
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO = customerPaymentVoucherMapper.toDto(updatedCustomerPaymentVoucher);

        restCustomerPaymentVoucherMockMvc.perform(put("/api/customer-payment-vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentVoucherDTO)))
            .andExpect(status().isOk());

        // Validate the CustomerPaymentVoucher in the database
        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeUpdate);
        CustomerPaymentVoucher testCustomerPaymentVoucher = customerPaymentVoucherList.get(customerPaymentVoucherList.size() - 1);
        assertThat(testCustomerPaymentVoucher.getSerialNo()).isEqualTo(UPDATED_SERIAL_NO);
        assertThat(testCustomerPaymentVoucher.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testCustomerPaymentVoucher.getLastEdityBy()).isEqualTo(UPDATED_LAST_EDITY_BY);
        assertThat(testCustomerPaymentVoucher.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingCustomerPaymentVoucher() throws Exception {
        int databaseSizeBeforeUpdate = customerPaymentVoucherRepository.findAll().size();

        // Create the CustomerPaymentVoucher
        CustomerPaymentVoucherDTO customerPaymentVoucherDTO = customerPaymentVoucherMapper.toDto(customerPaymentVoucher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCustomerPaymentVoucherMockMvc.perform(put("/api/customer-payment-vouchers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(customerPaymentVoucherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CustomerPaymentVoucher in the database
        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCustomerPaymentVoucher() throws Exception {
        // Initialize the database
        customerPaymentVoucherRepository.saveAndFlush(customerPaymentVoucher);

        int databaseSizeBeforeDelete = customerPaymentVoucherRepository.findAll().size();

        // Delete the customerPaymentVoucher
        restCustomerPaymentVoucherMockMvc.perform(delete("/api/customer-payment-vouchers/{id}", customerPaymentVoucher.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CustomerPaymentVoucher> customerPaymentVoucherList = customerPaymentVoucherRepository.findAll();
        assertThat(customerPaymentVoucherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
