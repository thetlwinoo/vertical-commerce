package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.DiscountDetails;
import com.vertical.commerce.domain.Discount;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.repository.DiscountDetailsRepository;
import com.vertical.commerce.service.DiscountDetailsService;
import com.vertical.commerce.service.dto.DiscountDetailsDTO;
import com.vertical.commerce.service.mapper.DiscountDetailsMapper;
import com.vertical.commerce.service.dto.DiscountDetailsCriteria;
import com.vertical.commerce.service.DiscountDetailsQueryService;

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
 * Integration tests for the {@link DiscountDetailsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class DiscountDetailsResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_IS_PERCENTAGE = false;
    private static final Boolean UPDATED_IS_PERCENTAGE = true;

    private static final Boolean DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT = false;
    private static final Boolean UPDATED_IS_ALLOW_COMBINATION_DISCOUNT = true;

    private static final Boolean DEFAULT_IS_FINAL_BILL_DISCOUNT = false;
    private static final Boolean UPDATED_IS_FINAL_BILL_DISCOUNT = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_COUNT = 1;
    private static final Integer UPDATED_START_COUNT = 2;
    private static final Integer SMALLER_START_COUNT = 1 - 1;

    private static final Integer DEFAULT_END_COUNT = 1;
    private static final Integer UPDATED_END_COUNT = 2;
    private static final Integer SMALLER_END_COUNT = 1 - 1;

    private static final Integer DEFAULT_MULTIPLY_COUNT = 1;
    private static final Integer UPDATED_MULTIPLY_COUNT = 2;
    private static final Integer SMALLER_MULTIPLY_COUNT = 1 - 1;

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private DiscountDetailsRepository discountDetailsRepository;

    @Autowired
    private DiscountDetailsMapper discountDetailsMapper;

    @Autowired
    private DiscountDetailsService discountDetailsService;

    @Autowired
    private DiscountDetailsQueryService discountDetailsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDiscountDetailsMockMvc;

    private DiscountDetails discountDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountDetails createEntity(EntityManager em) {
        DiscountDetails discountDetails = new DiscountDetails()
            .amount(DEFAULT_AMOUNT)
            .isPercentage(DEFAULT_IS_PERCENTAGE)
            .isAllowCombinationDiscount(DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT)
            .isFinalBillDiscount(DEFAULT_IS_FINAL_BILL_DISCOUNT)
            .name(DEFAULT_NAME)
            .startCount(DEFAULT_START_COUNT)
            .endCount(DEFAULT_END_COUNT)
            .multiplyCount(DEFAULT_MULTIPLY_COUNT)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return discountDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountDetails createUpdatedEntity(EntityManager em) {
        DiscountDetails discountDetails = new DiscountDetails()
            .amount(UPDATED_AMOUNT)
            .isPercentage(UPDATED_IS_PERCENTAGE)
            .isAllowCombinationDiscount(UPDATED_IS_ALLOW_COMBINATION_DISCOUNT)
            .isFinalBillDiscount(UPDATED_IS_FINAL_BILL_DISCOUNT)
            .name(UPDATED_NAME)
            .startCount(UPDATED_START_COUNT)
            .endCount(UPDATED_END_COUNT)
            .multiplyCount(UPDATED_MULTIPLY_COUNT)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return discountDetails;
    }

    @BeforeEach
    public void initTest() {
        discountDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscountDetails() throws Exception {
        int databaseSizeBeforeCreate = discountDetailsRepository.findAll().size();
        // Create the DiscountDetails
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);
        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the DiscountDetails in the database
        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        DiscountDetails testDiscountDetails = discountDetailsList.get(discountDetailsList.size() - 1);
        assertThat(testDiscountDetails.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testDiscountDetails.isIsPercentage()).isEqualTo(DEFAULT_IS_PERCENTAGE);
        assertThat(testDiscountDetails.isIsAllowCombinationDiscount()).isEqualTo(DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT);
        assertThat(testDiscountDetails.isIsFinalBillDiscount()).isEqualTo(DEFAULT_IS_FINAL_BILL_DISCOUNT);
        assertThat(testDiscountDetails.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDiscountDetails.getStartCount()).isEqualTo(DEFAULT_START_COUNT);
        assertThat(testDiscountDetails.getEndCount()).isEqualTo(DEFAULT_END_COUNT);
        assertThat(testDiscountDetails.getMultiplyCount()).isEqualTo(DEFAULT_MULTIPLY_COUNT);
        assertThat(testDiscountDetails.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createDiscountDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountDetailsRepository.findAll().size();

        // Create the DiscountDetails with an existing ID
        discountDetails.setId(1L);
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountDetails in the database
        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountDetailsRepository.findAll().size();
        // set the field null
        discountDetails.setAmount(null);

        // Create the DiscountDetails, which fails.
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);


        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsPercentageIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountDetailsRepository.findAll().size();
        // set the field null
        discountDetails.setIsPercentage(null);

        // Create the DiscountDetails, which fails.
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);


        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsAllowCombinationDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountDetailsRepository.findAll().size();
        // set the field null
        discountDetails.setIsAllowCombinationDiscount(null);

        // Create the DiscountDetails, which fails.
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);


        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsFinalBillDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountDetailsRepository.findAll().size();
        // set the field null
        discountDetails.setIsFinalBillDiscount(null);

        // Create the DiscountDetails, which fails.
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);


        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountDetailsRepository.findAll().size();
        // set the field null
        discountDetails.setName(null);

        // Create the DiscountDetails, which fails.
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);


        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountDetailsRepository.findAll().size();
        // set the field null
        discountDetails.setStartCount(null);

        // Create the DiscountDetails, which fails.
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);


        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMultiplyCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountDetailsRepository.findAll().size();
        // set the field null
        discountDetails.setMultiplyCount(null);

        // Create the DiscountDetails, which fails.
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);


        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifiedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountDetailsRepository.findAll().size();
        // set the field null
        discountDetails.setModifiedDate(null);

        // Create the DiscountDetails, which fails.
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);


        restDiscountDetailsMockMvc.perform(post("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscountDetails() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList
        restDiscountDetailsMockMvc.perform(get("/api/discount-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].isPercentage").value(hasItem(DEFAULT_IS_PERCENTAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].isAllowCombinationDiscount").value(hasItem(DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].isFinalBillDiscount").value(hasItem(DEFAULT_IS_FINAL_BILL_DISCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startCount").value(hasItem(DEFAULT_START_COUNT)))
            .andExpect(jsonPath("$.[*].endCount").value(hasItem(DEFAULT_END_COUNT)))
            .andExpect(jsonPath("$.[*].multiplyCount").value(hasItem(DEFAULT_MULTIPLY_COUNT)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDiscountDetails() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get the discountDetails
        restDiscountDetailsMockMvc.perform(get("/api/discount-details/{id}", discountDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discountDetails.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.isPercentage").value(DEFAULT_IS_PERCENTAGE.booleanValue()))
            .andExpect(jsonPath("$.isAllowCombinationDiscount").value(DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT.booleanValue()))
            .andExpect(jsonPath("$.isFinalBillDiscount").value(DEFAULT_IS_FINAL_BILL_DISCOUNT.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.startCount").value(DEFAULT_START_COUNT))
            .andExpect(jsonPath("$.endCount").value(DEFAULT_END_COUNT))
            .andExpect(jsonPath("$.multiplyCount").value(DEFAULT_MULTIPLY_COUNT))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getDiscountDetailsByIdFiltering() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        Long id = discountDetails.getId();

        defaultDiscountDetailsShouldBeFound("id.equals=" + id);
        defaultDiscountDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultDiscountDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDiscountDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultDiscountDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDiscountDetailsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDiscountDetailsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where amount equals to DEFAULT_AMOUNT
        defaultDiscountDetailsShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the discountDetailsList where amount equals to UPDATED_AMOUNT
        defaultDiscountDetailsShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where amount not equals to DEFAULT_AMOUNT
        defaultDiscountDetailsShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the discountDetailsList where amount not equals to UPDATED_AMOUNT
        defaultDiscountDetailsShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultDiscountDetailsShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the discountDetailsList where amount equals to UPDATED_AMOUNT
        defaultDiscountDetailsShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where amount is not null
        defaultDiscountDetailsShouldBeFound("amount.specified=true");

        // Get all the discountDetailsList where amount is null
        defaultDiscountDetailsShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultDiscountDetailsShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the discountDetailsList where amount is greater than or equal to UPDATED_AMOUNT
        defaultDiscountDetailsShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where amount is less than or equal to DEFAULT_AMOUNT
        defaultDiscountDetailsShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the discountDetailsList where amount is less than or equal to SMALLER_AMOUNT
        defaultDiscountDetailsShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where amount is less than DEFAULT_AMOUNT
        defaultDiscountDetailsShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the discountDetailsList where amount is less than UPDATED_AMOUNT
        defaultDiscountDetailsShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where amount is greater than DEFAULT_AMOUNT
        defaultDiscountDetailsShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the discountDetailsList where amount is greater than SMALLER_AMOUNT
        defaultDiscountDetailsShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllDiscountDetailsByIsPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isPercentage equals to DEFAULT_IS_PERCENTAGE
        defaultDiscountDetailsShouldBeFound("isPercentage.equals=" + DEFAULT_IS_PERCENTAGE);

        // Get all the discountDetailsList where isPercentage equals to UPDATED_IS_PERCENTAGE
        defaultDiscountDetailsShouldNotBeFound("isPercentage.equals=" + UPDATED_IS_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isPercentage not equals to DEFAULT_IS_PERCENTAGE
        defaultDiscountDetailsShouldNotBeFound("isPercentage.notEquals=" + DEFAULT_IS_PERCENTAGE);

        // Get all the discountDetailsList where isPercentage not equals to UPDATED_IS_PERCENTAGE
        defaultDiscountDetailsShouldBeFound("isPercentage.notEquals=" + UPDATED_IS_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isPercentage in DEFAULT_IS_PERCENTAGE or UPDATED_IS_PERCENTAGE
        defaultDiscountDetailsShouldBeFound("isPercentage.in=" + DEFAULT_IS_PERCENTAGE + "," + UPDATED_IS_PERCENTAGE);

        // Get all the discountDetailsList where isPercentage equals to UPDATED_IS_PERCENTAGE
        defaultDiscountDetailsShouldNotBeFound("isPercentage.in=" + UPDATED_IS_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isPercentage is not null
        defaultDiscountDetailsShouldBeFound("isPercentage.specified=true");

        // Get all the discountDetailsList where isPercentage is null
        defaultDiscountDetailsShouldNotBeFound("isPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsAllowCombinationDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isAllowCombinationDiscount equals to DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT
        defaultDiscountDetailsShouldBeFound("isAllowCombinationDiscount.equals=" + DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT);

        // Get all the discountDetailsList where isAllowCombinationDiscount equals to UPDATED_IS_ALLOW_COMBINATION_DISCOUNT
        defaultDiscountDetailsShouldNotBeFound("isAllowCombinationDiscount.equals=" + UPDATED_IS_ALLOW_COMBINATION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsAllowCombinationDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isAllowCombinationDiscount not equals to DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT
        defaultDiscountDetailsShouldNotBeFound("isAllowCombinationDiscount.notEquals=" + DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT);

        // Get all the discountDetailsList where isAllowCombinationDiscount not equals to UPDATED_IS_ALLOW_COMBINATION_DISCOUNT
        defaultDiscountDetailsShouldBeFound("isAllowCombinationDiscount.notEquals=" + UPDATED_IS_ALLOW_COMBINATION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsAllowCombinationDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isAllowCombinationDiscount in DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT or UPDATED_IS_ALLOW_COMBINATION_DISCOUNT
        defaultDiscountDetailsShouldBeFound("isAllowCombinationDiscount.in=" + DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT + "," + UPDATED_IS_ALLOW_COMBINATION_DISCOUNT);

        // Get all the discountDetailsList where isAllowCombinationDiscount equals to UPDATED_IS_ALLOW_COMBINATION_DISCOUNT
        defaultDiscountDetailsShouldNotBeFound("isAllowCombinationDiscount.in=" + UPDATED_IS_ALLOW_COMBINATION_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsAllowCombinationDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isAllowCombinationDiscount is not null
        defaultDiscountDetailsShouldBeFound("isAllowCombinationDiscount.specified=true");

        // Get all the discountDetailsList where isAllowCombinationDiscount is null
        defaultDiscountDetailsShouldNotBeFound("isAllowCombinationDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsFinalBillDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isFinalBillDiscount equals to DEFAULT_IS_FINAL_BILL_DISCOUNT
        defaultDiscountDetailsShouldBeFound("isFinalBillDiscount.equals=" + DEFAULT_IS_FINAL_BILL_DISCOUNT);

        // Get all the discountDetailsList where isFinalBillDiscount equals to UPDATED_IS_FINAL_BILL_DISCOUNT
        defaultDiscountDetailsShouldNotBeFound("isFinalBillDiscount.equals=" + UPDATED_IS_FINAL_BILL_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsFinalBillDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isFinalBillDiscount not equals to DEFAULT_IS_FINAL_BILL_DISCOUNT
        defaultDiscountDetailsShouldNotBeFound("isFinalBillDiscount.notEquals=" + DEFAULT_IS_FINAL_BILL_DISCOUNT);

        // Get all the discountDetailsList where isFinalBillDiscount not equals to UPDATED_IS_FINAL_BILL_DISCOUNT
        defaultDiscountDetailsShouldBeFound("isFinalBillDiscount.notEquals=" + UPDATED_IS_FINAL_BILL_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsFinalBillDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isFinalBillDiscount in DEFAULT_IS_FINAL_BILL_DISCOUNT or UPDATED_IS_FINAL_BILL_DISCOUNT
        defaultDiscountDetailsShouldBeFound("isFinalBillDiscount.in=" + DEFAULT_IS_FINAL_BILL_DISCOUNT + "," + UPDATED_IS_FINAL_BILL_DISCOUNT);

        // Get all the discountDetailsList where isFinalBillDiscount equals to UPDATED_IS_FINAL_BILL_DISCOUNT
        defaultDiscountDetailsShouldNotBeFound("isFinalBillDiscount.in=" + UPDATED_IS_FINAL_BILL_DISCOUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByIsFinalBillDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where isFinalBillDiscount is not null
        defaultDiscountDetailsShouldBeFound("isFinalBillDiscount.specified=true");

        // Get all the discountDetailsList where isFinalBillDiscount is null
        defaultDiscountDetailsShouldNotBeFound("isFinalBillDiscount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where name equals to DEFAULT_NAME
        defaultDiscountDetailsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the discountDetailsList where name equals to UPDATED_NAME
        defaultDiscountDetailsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where name not equals to DEFAULT_NAME
        defaultDiscountDetailsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the discountDetailsList where name not equals to UPDATED_NAME
        defaultDiscountDetailsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDiscountDetailsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the discountDetailsList where name equals to UPDATED_NAME
        defaultDiscountDetailsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where name is not null
        defaultDiscountDetailsShouldBeFound("name.specified=true");

        // Get all the discountDetailsList where name is null
        defaultDiscountDetailsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDiscountDetailsByNameContainsSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where name contains DEFAULT_NAME
        defaultDiscountDetailsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the discountDetailsList where name contains UPDATED_NAME
        defaultDiscountDetailsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where name does not contain DEFAULT_NAME
        defaultDiscountDetailsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the discountDetailsList where name does not contain UPDATED_NAME
        defaultDiscountDetailsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDiscountDetailsByStartCountIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where startCount equals to DEFAULT_START_COUNT
        defaultDiscountDetailsShouldBeFound("startCount.equals=" + DEFAULT_START_COUNT);

        // Get all the discountDetailsList where startCount equals to UPDATED_START_COUNT
        defaultDiscountDetailsShouldNotBeFound("startCount.equals=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByStartCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where startCount not equals to DEFAULT_START_COUNT
        defaultDiscountDetailsShouldNotBeFound("startCount.notEquals=" + DEFAULT_START_COUNT);

        // Get all the discountDetailsList where startCount not equals to UPDATED_START_COUNT
        defaultDiscountDetailsShouldBeFound("startCount.notEquals=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByStartCountIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where startCount in DEFAULT_START_COUNT or UPDATED_START_COUNT
        defaultDiscountDetailsShouldBeFound("startCount.in=" + DEFAULT_START_COUNT + "," + UPDATED_START_COUNT);

        // Get all the discountDetailsList where startCount equals to UPDATED_START_COUNT
        defaultDiscountDetailsShouldNotBeFound("startCount.in=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByStartCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where startCount is not null
        defaultDiscountDetailsShouldBeFound("startCount.specified=true");

        // Get all the discountDetailsList where startCount is null
        defaultDiscountDetailsShouldNotBeFound("startCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByStartCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where startCount is greater than or equal to DEFAULT_START_COUNT
        defaultDiscountDetailsShouldBeFound("startCount.greaterThanOrEqual=" + DEFAULT_START_COUNT);

        // Get all the discountDetailsList where startCount is greater than or equal to UPDATED_START_COUNT
        defaultDiscountDetailsShouldNotBeFound("startCount.greaterThanOrEqual=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByStartCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where startCount is less than or equal to DEFAULT_START_COUNT
        defaultDiscountDetailsShouldBeFound("startCount.lessThanOrEqual=" + DEFAULT_START_COUNT);

        // Get all the discountDetailsList where startCount is less than or equal to SMALLER_START_COUNT
        defaultDiscountDetailsShouldNotBeFound("startCount.lessThanOrEqual=" + SMALLER_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByStartCountIsLessThanSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where startCount is less than DEFAULT_START_COUNT
        defaultDiscountDetailsShouldNotBeFound("startCount.lessThan=" + DEFAULT_START_COUNT);

        // Get all the discountDetailsList where startCount is less than UPDATED_START_COUNT
        defaultDiscountDetailsShouldBeFound("startCount.lessThan=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByStartCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where startCount is greater than DEFAULT_START_COUNT
        defaultDiscountDetailsShouldNotBeFound("startCount.greaterThan=" + DEFAULT_START_COUNT);

        // Get all the discountDetailsList where startCount is greater than SMALLER_START_COUNT
        defaultDiscountDetailsShouldBeFound("startCount.greaterThan=" + SMALLER_START_COUNT);
    }


    @Test
    @Transactional
    public void getAllDiscountDetailsByEndCountIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where endCount equals to DEFAULT_END_COUNT
        defaultDiscountDetailsShouldBeFound("endCount.equals=" + DEFAULT_END_COUNT);

        // Get all the discountDetailsList where endCount equals to UPDATED_END_COUNT
        defaultDiscountDetailsShouldNotBeFound("endCount.equals=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByEndCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where endCount not equals to DEFAULT_END_COUNT
        defaultDiscountDetailsShouldNotBeFound("endCount.notEquals=" + DEFAULT_END_COUNT);

        // Get all the discountDetailsList where endCount not equals to UPDATED_END_COUNT
        defaultDiscountDetailsShouldBeFound("endCount.notEquals=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByEndCountIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where endCount in DEFAULT_END_COUNT or UPDATED_END_COUNT
        defaultDiscountDetailsShouldBeFound("endCount.in=" + DEFAULT_END_COUNT + "," + UPDATED_END_COUNT);

        // Get all the discountDetailsList where endCount equals to UPDATED_END_COUNT
        defaultDiscountDetailsShouldNotBeFound("endCount.in=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByEndCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where endCount is not null
        defaultDiscountDetailsShouldBeFound("endCount.specified=true");

        // Get all the discountDetailsList where endCount is null
        defaultDiscountDetailsShouldNotBeFound("endCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByEndCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where endCount is greater than or equal to DEFAULT_END_COUNT
        defaultDiscountDetailsShouldBeFound("endCount.greaterThanOrEqual=" + DEFAULT_END_COUNT);

        // Get all the discountDetailsList where endCount is greater than or equal to UPDATED_END_COUNT
        defaultDiscountDetailsShouldNotBeFound("endCount.greaterThanOrEqual=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByEndCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where endCount is less than or equal to DEFAULT_END_COUNT
        defaultDiscountDetailsShouldBeFound("endCount.lessThanOrEqual=" + DEFAULT_END_COUNT);

        // Get all the discountDetailsList where endCount is less than or equal to SMALLER_END_COUNT
        defaultDiscountDetailsShouldNotBeFound("endCount.lessThanOrEqual=" + SMALLER_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByEndCountIsLessThanSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where endCount is less than DEFAULT_END_COUNT
        defaultDiscountDetailsShouldNotBeFound("endCount.lessThan=" + DEFAULT_END_COUNT);

        // Get all the discountDetailsList where endCount is less than UPDATED_END_COUNT
        defaultDiscountDetailsShouldBeFound("endCount.lessThan=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByEndCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where endCount is greater than DEFAULT_END_COUNT
        defaultDiscountDetailsShouldNotBeFound("endCount.greaterThan=" + DEFAULT_END_COUNT);

        // Get all the discountDetailsList where endCount is greater than SMALLER_END_COUNT
        defaultDiscountDetailsShouldBeFound("endCount.greaterThan=" + SMALLER_END_COUNT);
    }


    @Test
    @Transactional
    public void getAllDiscountDetailsByMultiplyCountIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where multiplyCount equals to DEFAULT_MULTIPLY_COUNT
        defaultDiscountDetailsShouldBeFound("multiplyCount.equals=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the discountDetailsList where multiplyCount equals to UPDATED_MULTIPLY_COUNT
        defaultDiscountDetailsShouldNotBeFound("multiplyCount.equals=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByMultiplyCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where multiplyCount not equals to DEFAULT_MULTIPLY_COUNT
        defaultDiscountDetailsShouldNotBeFound("multiplyCount.notEquals=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the discountDetailsList where multiplyCount not equals to UPDATED_MULTIPLY_COUNT
        defaultDiscountDetailsShouldBeFound("multiplyCount.notEquals=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByMultiplyCountIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where multiplyCount in DEFAULT_MULTIPLY_COUNT or UPDATED_MULTIPLY_COUNT
        defaultDiscountDetailsShouldBeFound("multiplyCount.in=" + DEFAULT_MULTIPLY_COUNT + "," + UPDATED_MULTIPLY_COUNT);

        // Get all the discountDetailsList where multiplyCount equals to UPDATED_MULTIPLY_COUNT
        defaultDiscountDetailsShouldNotBeFound("multiplyCount.in=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByMultiplyCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where multiplyCount is not null
        defaultDiscountDetailsShouldBeFound("multiplyCount.specified=true");

        // Get all the discountDetailsList where multiplyCount is null
        defaultDiscountDetailsShouldNotBeFound("multiplyCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByMultiplyCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where multiplyCount is greater than or equal to DEFAULT_MULTIPLY_COUNT
        defaultDiscountDetailsShouldBeFound("multiplyCount.greaterThanOrEqual=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the discountDetailsList where multiplyCount is greater than or equal to UPDATED_MULTIPLY_COUNT
        defaultDiscountDetailsShouldNotBeFound("multiplyCount.greaterThanOrEqual=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByMultiplyCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where multiplyCount is less than or equal to DEFAULT_MULTIPLY_COUNT
        defaultDiscountDetailsShouldBeFound("multiplyCount.lessThanOrEqual=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the discountDetailsList where multiplyCount is less than or equal to SMALLER_MULTIPLY_COUNT
        defaultDiscountDetailsShouldNotBeFound("multiplyCount.lessThanOrEqual=" + SMALLER_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByMultiplyCountIsLessThanSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where multiplyCount is less than DEFAULT_MULTIPLY_COUNT
        defaultDiscountDetailsShouldNotBeFound("multiplyCount.lessThan=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the discountDetailsList where multiplyCount is less than UPDATED_MULTIPLY_COUNT
        defaultDiscountDetailsShouldBeFound("multiplyCount.lessThan=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByMultiplyCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where multiplyCount is greater than DEFAULT_MULTIPLY_COUNT
        defaultDiscountDetailsShouldNotBeFound("multiplyCount.greaterThan=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the discountDetailsList where multiplyCount is greater than SMALLER_MULTIPLY_COUNT
        defaultDiscountDetailsShouldBeFound("multiplyCount.greaterThan=" + SMALLER_MULTIPLY_COUNT);
    }


    @Test
    @Transactional
    public void getAllDiscountDetailsByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultDiscountDetailsShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the discountDetailsList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultDiscountDetailsShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultDiscountDetailsShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the discountDetailsList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultDiscountDetailsShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultDiscountDetailsShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the discountDetailsList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultDiscountDetailsShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        // Get all the discountDetailsList where modifiedDate is not null
        defaultDiscountDetailsShouldBeFound("modifiedDate.specified=true");

        // Get all the discountDetailsList where modifiedDate is null
        defaultDiscountDetailsShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDiscountDetailsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);
        Discount discount = DiscountResourceIT.createEntity(em);
        em.persist(discount);
        em.flush();
        discountDetails.setDiscount(discount);
        discountDetailsRepository.saveAndFlush(discountDetails);
        Long discountId = discount.getId();

        // Get all the discountDetailsList where discount equals to discountId
        defaultDiscountDetailsShouldBeFound("discountId.equals=" + discountId);

        // Get all the discountDetailsList where discount equals to discountId + 1
        defaultDiscountDetailsShouldNotBeFound("discountId.equals=" + (discountId + 1));
    }


    @Test
    @Transactional
    public void getAllDiscountDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        discountDetails.setProduct(product);
        discountDetailsRepository.saveAndFlush(discountDetails);
        Long productId = product.getId();

        // Get all the discountDetailsList where product equals to productId
        defaultDiscountDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the discountDetailsList where product equals to productId + 1
        defaultDiscountDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllDiscountDetailsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        discountDetails.setProductCategory(productCategory);
        discountDetailsRepository.saveAndFlush(discountDetails);
        Long productCategoryId = productCategory.getId();

        // Get all the discountDetailsList where productCategory equals to productCategoryId
        defaultDiscountDetailsShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the discountDetailsList where productCategory equals to productCategoryId + 1
        defaultDiscountDetailsShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDiscountDetailsShouldBeFound(String filter) throws Exception {
        restDiscountDetailsMockMvc.perform(get("/api/discount-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].isPercentage").value(hasItem(DEFAULT_IS_PERCENTAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].isAllowCombinationDiscount").value(hasItem(DEFAULT_IS_ALLOW_COMBINATION_DISCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].isFinalBillDiscount").value(hasItem(DEFAULT_IS_FINAL_BILL_DISCOUNT.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].startCount").value(hasItem(DEFAULT_START_COUNT)))
            .andExpect(jsonPath("$.[*].endCount").value(hasItem(DEFAULT_END_COUNT)))
            .andExpect(jsonPath("$.[*].multiplyCount").value(hasItem(DEFAULT_MULTIPLY_COUNT)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restDiscountDetailsMockMvc.perform(get("/api/discount-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDiscountDetailsShouldNotBeFound(String filter) throws Exception {
        restDiscountDetailsMockMvc.perform(get("/api/discount-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDiscountDetailsMockMvc.perform(get("/api/discount-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDiscountDetails() throws Exception {
        // Get the discountDetails
        restDiscountDetailsMockMvc.perform(get("/api/discount-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscountDetails() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        int databaseSizeBeforeUpdate = discountDetailsRepository.findAll().size();

        // Update the discountDetails
        DiscountDetails updatedDiscountDetails = discountDetailsRepository.findById(discountDetails.getId()).get();
        // Disconnect from session so that the updates on updatedDiscountDetails are not directly saved in db
        em.detach(updatedDiscountDetails);
        updatedDiscountDetails
            .amount(UPDATED_AMOUNT)
            .isPercentage(UPDATED_IS_PERCENTAGE)
            .isAllowCombinationDiscount(UPDATED_IS_ALLOW_COMBINATION_DISCOUNT)
            .isFinalBillDiscount(UPDATED_IS_FINAL_BILL_DISCOUNT)
            .name(UPDATED_NAME)
            .startCount(UPDATED_START_COUNT)
            .endCount(UPDATED_END_COUNT)
            .multiplyCount(UPDATED_MULTIPLY_COUNT)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(updatedDiscountDetails);

        restDiscountDetailsMockMvc.perform(put("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the DiscountDetails in the database
        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeUpdate);
        DiscountDetails testDiscountDetails = discountDetailsList.get(discountDetailsList.size() - 1);
        assertThat(testDiscountDetails.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testDiscountDetails.isIsPercentage()).isEqualTo(UPDATED_IS_PERCENTAGE);
        assertThat(testDiscountDetails.isIsAllowCombinationDiscount()).isEqualTo(UPDATED_IS_ALLOW_COMBINATION_DISCOUNT);
        assertThat(testDiscountDetails.isIsFinalBillDiscount()).isEqualTo(UPDATED_IS_FINAL_BILL_DISCOUNT);
        assertThat(testDiscountDetails.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDiscountDetails.getStartCount()).isEqualTo(UPDATED_START_COUNT);
        assertThat(testDiscountDetails.getEndCount()).isEqualTo(UPDATED_END_COUNT);
        assertThat(testDiscountDetails.getMultiplyCount()).isEqualTo(UPDATED_MULTIPLY_COUNT);
        assertThat(testDiscountDetails.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscountDetails() throws Exception {
        int databaseSizeBeforeUpdate = discountDetailsRepository.findAll().size();

        // Create the DiscountDetails
        DiscountDetailsDTO discountDetailsDTO = discountDetailsMapper.toDto(discountDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountDetailsMockMvc.perform(put("/api/discount-details").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(discountDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountDetails in the database
        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscountDetails() throws Exception {
        // Initialize the database
        discountDetailsRepository.saveAndFlush(discountDetails);

        int databaseSizeBeforeDelete = discountDetailsRepository.findAll().size();

        // Delete the discountDetails
        restDiscountDetailsMockMvc.perform(delete("/api/discount-details/{id}", discountDetails.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiscountDetails> discountDetailsList = discountDetailsRepository.findAll();
        assertThat(discountDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
