package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductSetDetailPrice;
import com.vertical.commerce.domain.ProductSetDetails;
import com.vertical.commerce.repository.ProductSetDetailPriceRepository;
import com.vertical.commerce.service.ProductSetDetailPriceService;
import com.vertical.commerce.service.dto.ProductSetDetailPriceDTO;
import com.vertical.commerce.service.mapper.ProductSetDetailPriceMapper;
import com.vertical.commerce.service.dto.ProductSetDetailPriceCriteria;
import com.vertical.commerce.service.ProductSetDetailPriceQueryService;

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
 * Integration tests for the {@link ProductSetDetailPriceResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductSetDetailPriceResourceIT {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_START_COUNT = 1;
    private static final Integer UPDATED_START_COUNT = 2;
    private static final Integer SMALLER_START_COUNT = 1 - 1;

    private static final Integer DEFAULT_END_COUNT = 1;
    private static final Integer UPDATED_END_COUNT = 2;
    private static final Integer SMALLER_END_COUNT = 1 - 1;

    private static final Integer DEFAULT_MULTIPLY_COUNT = 1;
    private static final Integer UPDATED_MULTIPLY_COUNT = 2;
    private static final Integer SMALLER_MULTIPLY_COUNT = 1 - 1;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductSetDetailPriceRepository productSetDetailPriceRepository;

    @Autowired
    private ProductSetDetailPriceMapper productSetDetailPriceMapper;

    @Autowired
    private ProductSetDetailPriceService productSetDetailPriceService;

    @Autowired
    private ProductSetDetailPriceQueryService productSetDetailPriceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductSetDetailPriceMockMvc;

    private ProductSetDetailPrice productSetDetailPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSetDetailPrice createEntity(EntityManager em) {
        ProductSetDetailPrice productSetDetailPrice = new ProductSetDetailPrice()
            .price(DEFAULT_PRICE)
            .startCount(DEFAULT_START_COUNT)
            .endCount(DEFAULT_END_COUNT)
            .multiplyCount(DEFAULT_MULTIPLY_COUNT)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return productSetDetailPrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductSetDetailPrice createUpdatedEntity(EntityManager em) {
        ProductSetDetailPrice productSetDetailPrice = new ProductSetDetailPrice()
            .price(UPDATED_PRICE)
            .startCount(UPDATED_START_COUNT)
            .endCount(UPDATED_END_COUNT)
            .multiplyCount(UPDATED_MULTIPLY_COUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return productSetDetailPrice;
    }

    @BeforeEach
    public void initTest() {
        productSetDetailPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductSetDetailPrice() throws Exception {
        int databaseSizeBeforeCreate = productSetDetailPriceRepository.findAll().size();
        // Create the ProductSetDetailPrice
        ProductSetDetailPriceDTO productSetDetailPriceDTO = productSetDetailPriceMapper.toDto(productSetDetailPrice);
        restProductSetDetailPriceMockMvc.perform(post("/api/product-set-detail-prices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailPriceDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductSetDetailPrice in the database
        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductSetDetailPrice testProductSetDetailPrice = productSetDetailPriceList.get(productSetDetailPriceList.size() - 1);
        assertThat(testProductSetDetailPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductSetDetailPrice.getStartCount()).isEqualTo(DEFAULT_START_COUNT);
        assertThat(testProductSetDetailPrice.getEndCount()).isEqualTo(DEFAULT_END_COUNT);
        assertThat(testProductSetDetailPrice.getMultiplyCount()).isEqualTo(DEFAULT_MULTIPLY_COUNT);
        assertThat(testProductSetDetailPrice.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProductSetDetailPrice.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProductSetDetailPrice.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProductSetDetailPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productSetDetailPriceRepository.findAll().size();

        // Create the ProductSetDetailPrice with an existing ID
        productSetDetailPrice.setId(1L);
        ProductSetDetailPriceDTO productSetDetailPriceDTO = productSetDetailPriceMapper.toDto(productSetDetailPrice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductSetDetailPriceMockMvc.perform(post("/api/product-set-detail-prices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSetDetailPrice in the database
        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSetDetailPriceRepository.findAll().size();
        // set the field null
        productSetDetailPrice.setPrice(null);

        // Create the ProductSetDetailPrice, which fails.
        ProductSetDetailPriceDTO productSetDetailPriceDTO = productSetDetailPriceMapper.toDto(productSetDetailPrice);


        restProductSetDetailPriceMockMvc.perform(post("/api/product-set-detail-prices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartCountIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSetDetailPriceRepository.findAll().size();
        // set the field null
        productSetDetailPrice.setStartCount(null);

        // Create the ProductSetDetailPrice, which fails.
        ProductSetDetailPriceDTO productSetDetailPriceDTO = productSetDetailPriceMapper.toDto(productSetDetailPrice);


        restProductSetDetailPriceMockMvc.perform(post("/api/product-set-detail-prices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSetDetailPriceRepository.findAll().size();
        // set the field null
        productSetDetailPrice.setStartDate(null);

        // Create the ProductSetDetailPrice, which fails.
        ProductSetDetailPriceDTO productSetDetailPriceDTO = productSetDetailPriceMapper.toDto(productSetDetailPrice);


        restProductSetDetailPriceMockMvc.perform(post("/api/product-set-detail-prices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifiedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productSetDetailPriceRepository.findAll().size();
        // set the field null
        productSetDetailPrice.setModifiedDate(null);

        // Create the ProductSetDetailPrice, which fails.
        ProductSetDetailPriceDTO productSetDetailPriceDTO = productSetDetailPriceMapper.toDto(productSetDetailPrice);


        restProductSetDetailPriceMockMvc.perform(post("/api/product-set-detail-prices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailPriceDTO)))
            .andExpect(status().isBadRequest());

        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPrices() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList
        restProductSetDetailPriceMockMvc.perform(get("/api/product-set-detail-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSetDetailPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].startCount").value(hasItem(DEFAULT_START_COUNT)))
            .andExpect(jsonPath("$.[*].endCount").value(hasItem(DEFAULT_END_COUNT)))
            .andExpect(jsonPath("$.[*].multiplyCount").value(hasItem(DEFAULT_MULTIPLY_COUNT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductSetDetailPrice() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get the productSetDetailPrice
        restProductSetDetailPriceMockMvc.perform(get("/api/product-set-detail-prices/{id}", productSetDetailPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productSetDetailPrice.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.startCount").value(DEFAULT_START_COUNT))
            .andExpect(jsonPath("$.endCount").value(DEFAULT_END_COUNT))
            .andExpect(jsonPath("$.multiplyCount").value(DEFAULT_MULTIPLY_COUNT))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getProductSetDetailPricesByIdFiltering() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        Long id = productSetDetailPrice.getId();

        defaultProductSetDetailPriceShouldBeFound("id.equals=" + id);
        defaultProductSetDetailPriceShouldNotBeFound("id.notEquals=" + id);

        defaultProductSetDetailPriceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductSetDetailPriceShouldNotBeFound("id.greaterThan=" + id);

        defaultProductSetDetailPriceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductSetDetailPriceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailPricesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where price equals to DEFAULT_PRICE
        defaultProductSetDetailPriceShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productSetDetailPriceList where price equals to UPDATED_PRICE
        defaultProductSetDetailPriceShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where price not equals to DEFAULT_PRICE
        defaultProductSetDetailPriceShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the productSetDetailPriceList where price not equals to UPDATED_PRICE
        defaultProductSetDetailPriceShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductSetDetailPriceShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productSetDetailPriceList where price equals to UPDATED_PRICE
        defaultProductSetDetailPriceShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where price is not null
        defaultProductSetDetailPriceShouldBeFound("price.specified=true");

        // Get all the productSetDetailPriceList where price is null
        defaultProductSetDetailPriceShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where price is greater than or equal to DEFAULT_PRICE
        defaultProductSetDetailPriceShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productSetDetailPriceList where price is greater than or equal to UPDATED_PRICE
        defaultProductSetDetailPriceShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where price is less than or equal to DEFAULT_PRICE
        defaultProductSetDetailPriceShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productSetDetailPriceList where price is less than or equal to SMALLER_PRICE
        defaultProductSetDetailPriceShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where price is less than DEFAULT_PRICE
        defaultProductSetDetailPriceShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the productSetDetailPriceList where price is less than UPDATED_PRICE
        defaultProductSetDetailPriceShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where price is greater than DEFAULT_PRICE
        defaultProductSetDetailPriceShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the productSetDetailPriceList where price is greater than SMALLER_PRICE
        defaultProductSetDetailPriceShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startCount equals to DEFAULT_START_COUNT
        defaultProductSetDetailPriceShouldBeFound("startCount.equals=" + DEFAULT_START_COUNT);

        // Get all the productSetDetailPriceList where startCount equals to UPDATED_START_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("startCount.equals=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startCount not equals to DEFAULT_START_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("startCount.notEquals=" + DEFAULT_START_COUNT);

        // Get all the productSetDetailPriceList where startCount not equals to UPDATED_START_COUNT
        defaultProductSetDetailPriceShouldBeFound("startCount.notEquals=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartCountIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startCount in DEFAULT_START_COUNT or UPDATED_START_COUNT
        defaultProductSetDetailPriceShouldBeFound("startCount.in=" + DEFAULT_START_COUNT + "," + UPDATED_START_COUNT);

        // Get all the productSetDetailPriceList where startCount equals to UPDATED_START_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("startCount.in=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startCount is not null
        defaultProductSetDetailPriceShouldBeFound("startCount.specified=true");

        // Get all the productSetDetailPriceList where startCount is null
        defaultProductSetDetailPriceShouldNotBeFound("startCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startCount is greater than or equal to DEFAULT_START_COUNT
        defaultProductSetDetailPriceShouldBeFound("startCount.greaterThanOrEqual=" + DEFAULT_START_COUNT);

        // Get all the productSetDetailPriceList where startCount is greater than or equal to UPDATED_START_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("startCount.greaterThanOrEqual=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startCount is less than or equal to DEFAULT_START_COUNT
        defaultProductSetDetailPriceShouldBeFound("startCount.lessThanOrEqual=" + DEFAULT_START_COUNT);

        // Get all the productSetDetailPriceList where startCount is less than or equal to SMALLER_START_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("startCount.lessThanOrEqual=" + SMALLER_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startCount is less than DEFAULT_START_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("startCount.lessThan=" + DEFAULT_START_COUNT);

        // Get all the productSetDetailPriceList where startCount is less than UPDATED_START_COUNT
        defaultProductSetDetailPriceShouldBeFound("startCount.lessThan=" + UPDATED_START_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startCount is greater than DEFAULT_START_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("startCount.greaterThan=" + DEFAULT_START_COUNT);

        // Get all the productSetDetailPriceList where startCount is greater than SMALLER_START_COUNT
        defaultProductSetDetailPriceShouldBeFound("startCount.greaterThan=" + SMALLER_START_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endCount equals to DEFAULT_END_COUNT
        defaultProductSetDetailPriceShouldBeFound("endCount.equals=" + DEFAULT_END_COUNT);

        // Get all the productSetDetailPriceList where endCount equals to UPDATED_END_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("endCount.equals=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endCount not equals to DEFAULT_END_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("endCount.notEquals=" + DEFAULT_END_COUNT);

        // Get all the productSetDetailPriceList where endCount not equals to UPDATED_END_COUNT
        defaultProductSetDetailPriceShouldBeFound("endCount.notEquals=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndCountIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endCount in DEFAULT_END_COUNT or UPDATED_END_COUNT
        defaultProductSetDetailPriceShouldBeFound("endCount.in=" + DEFAULT_END_COUNT + "," + UPDATED_END_COUNT);

        // Get all the productSetDetailPriceList where endCount equals to UPDATED_END_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("endCount.in=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endCount is not null
        defaultProductSetDetailPriceShouldBeFound("endCount.specified=true");

        // Get all the productSetDetailPriceList where endCount is null
        defaultProductSetDetailPriceShouldNotBeFound("endCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endCount is greater than or equal to DEFAULT_END_COUNT
        defaultProductSetDetailPriceShouldBeFound("endCount.greaterThanOrEqual=" + DEFAULT_END_COUNT);

        // Get all the productSetDetailPriceList where endCount is greater than or equal to UPDATED_END_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("endCount.greaterThanOrEqual=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endCount is less than or equal to DEFAULT_END_COUNT
        defaultProductSetDetailPriceShouldBeFound("endCount.lessThanOrEqual=" + DEFAULT_END_COUNT);

        // Get all the productSetDetailPriceList where endCount is less than or equal to SMALLER_END_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("endCount.lessThanOrEqual=" + SMALLER_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endCount is less than DEFAULT_END_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("endCount.lessThan=" + DEFAULT_END_COUNT);

        // Get all the productSetDetailPriceList where endCount is less than UPDATED_END_COUNT
        defaultProductSetDetailPriceShouldBeFound("endCount.lessThan=" + UPDATED_END_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endCount is greater than DEFAULT_END_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("endCount.greaterThan=" + DEFAULT_END_COUNT);

        // Get all the productSetDetailPriceList where endCount is greater than SMALLER_END_COUNT
        defaultProductSetDetailPriceShouldBeFound("endCount.greaterThan=" + SMALLER_END_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailPricesByMultiplyCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where multiplyCount equals to DEFAULT_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldBeFound("multiplyCount.equals=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the productSetDetailPriceList where multiplyCount equals to UPDATED_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("multiplyCount.equals=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByMultiplyCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where multiplyCount not equals to DEFAULT_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("multiplyCount.notEquals=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the productSetDetailPriceList where multiplyCount not equals to UPDATED_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldBeFound("multiplyCount.notEquals=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByMultiplyCountIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where multiplyCount in DEFAULT_MULTIPLY_COUNT or UPDATED_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldBeFound("multiplyCount.in=" + DEFAULT_MULTIPLY_COUNT + "," + UPDATED_MULTIPLY_COUNT);

        // Get all the productSetDetailPriceList where multiplyCount equals to UPDATED_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("multiplyCount.in=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByMultiplyCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where multiplyCount is not null
        defaultProductSetDetailPriceShouldBeFound("multiplyCount.specified=true");

        // Get all the productSetDetailPriceList where multiplyCount is null
        defaultProductSetDetailPriceShouldNotBeFound("multiplyCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByMultiplyCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where multiplyCount is greater than or equal to DEFAULT_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldBeFound("multiplyCount.greaterThanOrEqual=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the productSetDetailPriceList where multiplyCount is greater than or equal to UPDATED_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("multiplyCount.greaterThanOrEqual=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByMultiplyCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where multiplyCount is less than or equal to DEFAULT_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldBeFound("multiplyCount.lessThanOrEqual=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the productSetDetailPriceList where multiplyCount is less than or equal to SMALLER_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("multiplyCount.lessThanOrEqual=" + SMALLER_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByMultiplyCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where multiplyCount is less than DEFAULT_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("multiplyCount.lessThan=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the productSetDetailPriceList where multiplyCount is less than UPDATED_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldBeFound("multiplyCount.lessThan=" + UPDATED_MULTIPLY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByMultiplyCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where multiplyCount is greater than DEFAULT_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldNotBeFound("multiplyCount.greaterThan=" + DEFAULT_MULTIPLY_COUNT);

        // Get all the productSetDetailPriceList where multiplyCount is greater than SMALLER_MULTIPLY_COUNT
        defaultProductSetDetailPriceShouldBeFound("multiplyCount.greaterThan=" + SMALLER_MULTIPLY_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startDate equals to DEFAULT_START_DATE
        defaultProductSetDetailPriceShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the productSetDetailPriceList where startDate equals to UPDATED_START_DATE
        defaultProductSetDetailPriceShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startDate not equals to DEFAULT_START_DATE
        defaultProductSetDetailPriceShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the productSetDetailPriceList where startDate not equals to UPDATED_START_DATE
        defaultProductSetDetailPriceShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultProductSetDetailPriceShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the productSetDetailPriceList where startDate equals to UPDATED_START_DATE
        defaultProductSetDetailPriceShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where startDate is not null
        defaultProductSetDetailPriceShouldBeFound("startDate.specified=true");

        // Get all the productSetDetailPriceList where startDate is null
        defaultProductSetDetailPriceShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endDate equals to DEFAULT_END_DATE
        defaultProductSetDetailPriceShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the productSetDetailPriceList where endDate equals to UPDATED_END_DATE
        defaultProductSetDetailPriceShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endDate not equals to DEFAULT_END_DATE
        defaultProductSetDetailPriceShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the productSetDetailPriceList where endDate not equals to UPDATED_END_DATE
        defaultProductSetDetailPriceShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultProductSetDetailPriceShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the productSetDetailPriceList where endDate equals to UPDATED_END_DATE
        defaultProductSetDetailPriceShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where endDate is not null
        defaultProductSetDetailPriceShouldBeFound("endDate.specified=true");

        // Get all the productSetDetailPriceList where endDate is null
        defaultProductSetDetailPriceShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultProductSetDetailPriceShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the productSetDetailPriceList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultProductSetDetailPriceShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultProductSetDetailPriceShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the productSetDetailPriceList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultProductSetDetailPriceShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultProductSetDetailPriceShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the productSetDetailPriceList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultProductSetDetailPriceShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        // Get all the productSetDetailPriceList where modifiedDate is not null
        defaultProductSetDetailPriceShouldBeFound("modifiedDate.specified=true");

        // Get all the productSetDetailPriceList where modifiedDate is null
        defaultProductSetDetailPriceShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductSetDetailPricesByProductSetDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);
        ProductSetDetails productSetDetail = ProductSetDetailsResourceIT.createEntity(em);
        em.persist(productSetDetail);
        em.flush();
        productSetDetailPrice.setProductSetDetail(productSetDetail);
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);
        Long productSetDetailId = productSetDetail.getId();

        // Get all the productSetDetailPriceList where productSetDetail equals to productSetDetailId
        defaultProductSetDetailPriceShouldBeFound("productSetDetailId.equals=" + productSetDetailId);

        // Get all the productSetDetailPriceList where productSetDetail equals to productSetDetailId + 1
        defaultProductSetDetailPriceShouldNotBeFound("productSetDetailId.equals=" + (productSetDetailId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductSetDetailPriceShouldBeFound(String filter) throws Exception {
        restProductSetDetailPriceMockMvc.perform(get("/api/product-set-detail-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productSetDetailPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].startCount").value(hasItem(DEFAULT_START_COUNT)))
            .andExpect(jsonPath("$.[*].endCount").value(hasItem(DEFAULT_END_COUNT)))
            .andExpect(jsonPath("$.[*].multiplyCount").value(hasItem(DEFAULT_MULTIPLY_COUNT)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductSetDetailPriceMockMvc.perform(get("/api/product-set-detail-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductSetDetailPriceShouldNotBeFound(String filter) throws Exception {
        restProductSetDetailPriceMockMvc.perform(get("/api/product-set-detail-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductSetDetailPriceMockMvc.perform(get("/api/product-set-detail-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductSetDetailPrice() throws Exception {
        // Get the productSetDetailPrice
        restProductSetDetailPriceMockMvc.perform(get("/api/product-set-detail-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductSetDetailPrice() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        int databaseSizeBeforeUpdate = productSetDetailPriceRepository.findAll().size();

        // Update the productSetDetailPrice
        ProductSetDetailPrice updatedProductSetDetailPrice = productSetDetailPriceRepository.findById(productSetDetailPrice.getId()).get();
        // Disconnect from session so that the updates on updatedProductSetDetailPrice are not directly saved in db
        em.detach(updatedProductSetDetailPrice);
        updatedProductSetDetailPrice
            .price(UPDATED_PRICE)
            .startCount(UPDATED_START_COUNT)
            .endCount(UPDATED_END_COUNT)
            .multiplyCount(UPDATED_MULTIPLY_COUNT)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        ProductSetDetailPriceDTO productSetDetailPriceDTO = productSetDetailPriceMapper.toDto(updatedProductSetDetailPrice);

        restProductSetDetailPriceMockMvc.perform(put("/api/product-set-detail-prices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailPriceDTO)))
            .andExpect(status().isOk());

        // Validate the ProductSetDetailPrice in the database
        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeUpdate);
        ProductSetDetailPrice testProductSetDetailPrice = productSetDetailPriceList.get(productSetDetailPriceList.size() - 1);
        assertThat(testProductSetDetailPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductSetDetailPrice.getStartCount()).isEqualTo(UPDATED_START_COUNT);
        assertThat(testProductSetDetailPrice.getEndCount()).isEqualTo(UPDATED_END_COUNT);
        assertThat(testProductSetDetailPrice.getMultiplyCount()).isEqualTo(UPDATED_MULTIPLY_COUNT);
        assertThat(testProductSetDetailPrice.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProductSetDetailPrice.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProductSetDetailPrice.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductSetDetailPrice() throws Exception {
        int databaseSizeBeforeUpdate = productSetDetailPriceRepository.findAll().size();

        // Create the ProductSetDetailPrice
        ProductSetDetailPriceDTO productSetDetailPriceDTO = productSetDetailPriceMapper.toDto(productSetDetailPrice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductSetDetailPriceMockMvc.perform(put("/api/product-set-detail-prices").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productSetDetailPriceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductSetDetailPrice in the database
        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductSetDetailPrice() throws Exception {
        // Initialize the database
        productSetDetailPriceRepository.saveAndFlush(productSetDetailPrice);

        int databaseSizeBeforeDelete = productSetDetailPriceRepository.findAll().size();

        // Delete the productSetDetailPrice
        restProductSetDetailPriceMockMvc.perform(delete("/api/product-set-detail-prices/{id}", productSetDetailPrice.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductSetDetailPrice> productSetDetailPriceList = productSetDetailPriceRepository.findAll();
        assertThat(productSetDetailPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
