package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.ProductListPriceHistory;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.repository.ProductListPriceHistoryRepository;
import com.vertical.commerce.service.ProductListPriceHistoryService;
import com.vertical.commerce.service.dto.ProductListPriceHistoryDTO;
import com.vertical.commerce.service.mapper.ProductListPriceHistoryMapper;
import com.vertical.commerce.service.dto.ProductListPriceHistoryCriteria;
import com.vertical.commerce.service.ProductListPriceHistoryQueryService;

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
 * Integration tests for the {@link ProductListPriceHistoryResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductListPriceHistoryResourceIT {

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_LIST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LIST_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LIST_PRICE = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductListPriceHistoryRepository productListPriceHistoryRepository;

    @Autowired
    private ProductListPriceHistoryMapper productListPriceHistoryMapper;

    @Autowired
    private ProductListPriceHistoryService productListPriceHistoryService;

    @Autowired
    private ProductListPriceHistoryQueryService productListPriceHistoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductListPriceHistoryMockMvc;

    private ProductListPriceHistory productListPriceHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductListPriceHistory createEntity(EntityManager em) {
        ProductListPriceHistory productListPriceHistory = new ProductListPriceHistory()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .listPrice(DEFAULT_LIST_PRICE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return productListPriceHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductListPriceHistory createUpdatedEntity(EntityManager em) {
        ProductListPriceHistory productListPriceHistory = new ProductListPriceHistory()
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .listPrice(UPDATED_LIST_PRICE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return productListPriceHistory;
    }

    @BeforeEach
    public void initTest() {
        productListPriceHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductListPriceHistory() throws Exception {
        int databaseSizeBeforeCreate = productListPriceHistoryRepository.findAll().size();
        // Create the ProductListPriceHistory
        ProductListPriceHistoryDTO productListPriceHistoryDTO = productListPriceHistoryMapper.toDto(productListPriceHistory);
        restProductListPriceHistoryMockMvc.perform(post("/api/product-list-price-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productListPriceHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductListPriceHistory in the database
        List<ProductListPriceHistory> productListPriceHistoryList = productListPriceHistoryRepository.findAll();
        assertThat(productListPriceHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductListPriceHistory testProductListPriceHistory = productListPriceHistoryList.get(productListPriceHistoryList.size() - 1);
        assertThat(testProductListPriceHistory.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testProductListPriceHistory.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testProductListPriceHistory.getListPrice()).isEqualTo(DEFAULT_LIST_PRICE);
        assertThat(testProductListPriceHistory.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void createProductListPriceHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productListPriceHistoryRepository.findAll().size();

        // Create the ProductListPriceHistory with an existing ID
        productListPriceHistory.setId(1L);
        ProductListPriceHistoryDTO productListPriceHistoryDTO = productListPriceHistoryMapper.toDto(productListPriceHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductListPriceHistoryMockMvc.perform(post("/api/product-list-price-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productListPriceHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductListPriceHistory in the database
        List<ProductListPriceHistory> productListPriceHistoryList = productListPriceHistoryRepository.findAll();
        assertThat(productListPriceHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productListPriceHistoryRepository.findAll().size();
        // set the field null
        productListPriceHistory.setStartDate(null);

        // Create the ProductListPriceHistory, which fails.
        ProductListPriceHistoryDTO productListPriceHistoryDTO = productListPriceHistoryMapper.toDto(productListPriceHistory);


        restProductListPriceHistoryMockMvc.perform(post("/api/product-list-price-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productListPriceHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductListPriceHistory> productListPriceHistoryList = productListPriceHistoryRepository.findAll();
        assertThat(productListPriceHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkListPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = productListPriceHistoryRepository.findAll().size();
        // set the field null
        productListPriceHistory.setListPrice(null);

        // Create the ProductListPriceHistory, which fails.
        ProductListPriceHistoryDTO productListPriceHistoryDTO = productListPriceHistoryMapper.toDto(productListPriceHistory);


        restProductListPriceHistoryMockMvc.perform(post("/api/product-list-price-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productListPriceHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductListPriceHistory> productListPriceHistoryList = productListPriceHistoryRepository.findAll();
        assertThat(productListPriceHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModifiedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productListPriceHistoryRepository.findAll().size();
        // set the field null
        productListPriceHistory.setModifiedDate(null);

        // Create the ProductListPriceHistory, which fails.
        ProductListPriceHistoryDTO productListPriceHistoryDTO = productListPriceHistoryMapper.toDto(productListPriceHistory);


        restProductListPriceHistoryMockMvc.perform(post("/api/product-list-price-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productListPriceHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<ProductListPriceHistory> productListPriceHistoryList = productListPriceHistoryRepository.findAll();
        assertThat(productListPriceHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistories() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList
        restProductListPriceHistoryMockMvc.perform(get("/api/product-list-price-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productListPriceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProductListPriceHistory() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get the productListPriceHistory
        restProductListPriceHistoryMockMvc.perform(get("/api/product-list-price-histories/{id}", productListPriceHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productListPriceHistory.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.listPrice").value(DEFAULT_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getProductListPriceHistoriesByIdFiltering() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        Long id = productListPriceHistory.getId();

        defaultProductListPriceHistoryShouldBeFound("id.equals=" + id);
        defaultProductListPriceHistoryShouldNotBeFound("id.notEquals=" + id);

        defaultProductListPriceHistoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductListPriceHistoryShouldNotBeFound("id.greaterThan=" + id);

        defaultProductListPriceHistoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductListPriceHistoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where startDate equals to DEFAULT_START_DATE
        defaultProductListPriceHistoryShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the productListPriceHistoryList where startDate equals to UPDATED_START_DATE
        defaultProductListPriceHistoryShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where startDate not equals to DEFAULT_START_DATE
        defaultProductListPriceHistoryShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the productListPriceHistoryList where startDate not equals to UPDATED_START_DATE
        defaultProductListPriceHistoryShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultProductListPriceHistoryShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the productListPriceHistoryList where startDate equals to UPDATED_START_DATE
        defaultProductListPriceHistoryShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where startDate is not null
        defaultProductListPriceHistoryShouldBeFound("startDate.specified=true");

        // Get all the productListPriceHistoryList where startDate is null
        defaultProductListPriceHistoryShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where endDate equals to DEFAULT_END_DATE
        defaultProductListPriceHistoryShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the productListPriceHistoryList where endDate equals to UPDATED_END_DATE
        defaultProductListPriceHistoryShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByEndDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where endDate not equals to DEFAULT_END_DATE
        defaultProductListPriceHistoryShouldNotBeFound("endDate.notEquals=" + DEFAULT_END_DATE);

        // Get all the productListPriceHistoryList where endDate not equals to UPDATED_END_DATE
        defaultProductListPriceHistoryShouldBeFound("endDate.notEquals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultProductListPriceHistoryShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the productListPriceHistoryList where endDate equals to UPDATED_END_DATE
        defaultProductListPriceHistoryShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where endDate is not null
        defaultProductListPriceHistoryShouldBeFound("endDate.specified=true");

        // Get all the productListPriceHistoryList where endDate is null
        defaultProductListPriceHistoryShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByListPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where listPrice equals to DEFAULT_LIST_PRICE
        defaultProductListPriceHistoryShouldBeFound("listPrice.equals=" + DEFAULT_LIST_PRICE);

        // Get all the productListPriceHistoryList where listPrice equals to UPDATED_LIST_PRICE
        defaultProductListPriceHistoryShouldNotBeFound("listPrice.equals=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByListPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where listPrice not equals to DEFAULT_LIST_PRICE
        defaultProductListPriceHistoryShouldNotBeFound("listPrice.notEquals=" + DEFAULT_LIST_PRICE);

        // Get all the productListPriceHistoryList where listPrice not equals to UPDATED_LIST_PRICE
        defaultProductListPriceHistoryShouldBeFound("listPrice.notEquals=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByListPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where listPrice in DEFAULT_LIST_PRICE or UPDATED_LIST_PRICE
        defaultProductListPriceHistoryShouldBeFound("listPrice.in=" + DEFAULT_LIST_PRICE + "," + UPDATED_LIST_PRICE);

        // Get all the productListPriceHistoryList where listPrice equals to UPDATED_LIST_PRICE
        defaultProductListPriceHistoryShouldNotBeFound("listPrice.in=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByListPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where listPrice is not null
        defaultProductListPriceHistoryShouldBeFound("listPrice.specified=true");

        // Get all the productListPriceHistoryList where listPrice is null
        defaultProductListPriceHistoryShouldNotBeFound("listPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByListPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where listPrice is greater than or equal to DEFAULT_LIST_PRICE
        defaultProductListPriceHistoryShouldBeFound("listPrice.greaterThanOrEqual=" + DEFAULT_LIST_PRICE);

        // Get all the productListPriceHistoryList where listPrice is greater than or equal to UPDATED_LIST_PRICE
        defaultProductListPriceHistoryShouldNotBeFound("listPrice.greaterThanOrEqual=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByListPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where listPrice is less than or equal to DEFAULT_LIST_PRICE
        defaultProductListPriceHistoryShouldBeFound("listPrice.lessThanOrEqual=" + DEFAULT_LIST_PRICE);

        // Get all the productListPriceHistoryList where listPrice is less than or equal to SMALLER_LIST_PRICE
        defaultProductListPriceHistoryShouldNotBeFound("listPrice.lessThanOrEqual=" + SMALLER_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByListPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where listPrice is less than DEFAULT_LIST_PRICE
        defaultProductListPriceHistoryShouldNotBeFound("listPrice.lessThan=" + DEFAULT_LIST_PRICE);

        // Get all the productListPriceHistoryList where listPrice is less than UPDATED_LIST_PRICE
        defaultProductListPriceHistoryShouldBeFound("listPrice.lessThan=" + UPDATED_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByListPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where listPrice is greater than DEFAULT_LIST_PRICE
        defaultProductListPriceHistoryShouldNotBeFound("listPrice.greaterThan=" + DEFAULT_LIST_PRICE);

        // Get all the productListPriceHistoryList where listPrice is greater than SMALLER_LIST_PRICE
        defaultProductListPriceHistoryShouldBeFound("listPrice.greaterThan=" + SMALLER_LIST_PRICE);
    }


    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where modifiedDate equals to DEFAULT_MODIFIED_DATE
        defaultProductListPriceHistoryShouldBeFound("modifiedDate.equals=" + DEFAULT_MODIFIED_DATE);

        // Get all the productListPriceHistoryList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultProductListPriceHistoryShouldNotBeFound("modifiedDate.equals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where modifiedDate not equals to DEFAULT_MODIFIED_DATE
        defaultProductListPriceHistoryShouldNotBeFound("modifiedDate.notEquals=" + DEFAULT_MODIFIED_DATE);

        // Get all the productListPriceHistoryList where modifiedDate not equals to UPDATED_MODIFIED_DATE
        defaultProductListPriceHistoryShouldBeFound("modifiedDate.notEquals=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where modifiedDate in DEFAULT_MODIFIED_DATE or UPDATED_MODIFIED_DATE
        defaultProductListPriceHistoryShouldBeFound("modifiedDate.in=" + DEFAULT_MODIFIED_DATE + "," + UPDATED_MODIFIED_DATE);

        // Get all the productListPriceHistoryList where modifiedDate equals to UPDATED_MODIFIED_DATE
        defaultProductListPriceHistoryShouldNotBeFound("modifiedDate.in=" + UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        // Get all the productListPriceHistoryList where modifiedDate is not null
        defaultProductListPriceHistoryShouldBeFound("modifiedDate.specified=true");

        // Get all the productListPriceHistoryList where modifiedDate is null
        defaultProductListPriceHistoryShouldNotBeFound("modifiedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductListPriceHistoriesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);
        Products product = ProductsResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productListPriceHistory.setProduct(product);
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);
        Long productId = product.getId();

        // Get all the productListPriceHistoryList where product equals to productId
        defaultProductListPriceHistoryShouldBeFound("productId.equals=" + productId);

        // Get all the productListPriceHistoryList where product equals to productId + 1
        defaultProductListPriceHistoryShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductListPriceHistoryShouldBeFound(String filter) throws Exception {
        restProductListPriceHistoryMockMvc.perform(get("/api/product-list-price-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productListPriceHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].listPrice").value(hasItem(DEFAULT_LIST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductListPriceHistoryMockMvc.perform(get("/api/product-list-price-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductListPriceHistoryShouldNotBeFound(String filter) throws Exception {
        restProductListPriceHistoryMockMvc.perform(get("/api/product-list-price-histories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductListPriceHistoryMockMvc.perform(get("/api/product-list-price-histories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductListPriceHistory() throws Exception {
        // Get the productListPriceHistory
        restProductListPriceHistoryMockMvc.perform(get("/api/product-list-price-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductListPriceHistory() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        int databaseSizeBeforeUpdate = productListPriceHistoryRepository.findAll().size();

        // Update the productListPriceHistory
        ProductListPriceHistory updatedProductListPriceHistory = productListPriceHistoryRepository.findById(productListPriceHistory.getId()).get();
        // Disconnect from session so that the updates on updatedProductListPriceHistory are not directly saved in db
        em.detach(updatedProductListPriceHistory);
        updatedProductListPriceHistory
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .listPrice(UPDATED_LIST_PRICE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        ProductListPriceHistoryDTO productListPriceHistoryDTO = productListPriceHistoryMapper.toDto(updatedProductListPriceHistory);

        restProductListPriceHistoryMockMvc.perform(put("/api/product-list-price-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productListPriceHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the ProductListPriceHistory in the database
        List<ProductListPriceHistory> productListPriceHistoryList = productListPriceHistoryRepository.findAll();
        assertThat(productListPriceHistoryList).hasSize(databaseSizeBeforeUpdate);
        ProductListPriceHistory testProductListPriceHistory = productListPriceHistoryList.get(productListPriceHistoryList.size() - 1);
        assertThat(testProductListPriceHistory.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testProductListPriceHistory.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testProductListPriceHistory.getListPrice()).isEqualTo(UPDATED_LIST_PRICE);
        assertThat(testProductListPriceHistory.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductListPriceHistory() throws Exception {
        int databaseSizeBeforeUpdate = productListPriceHistoryRepository.findAll().size();

        // Create the ProductListPriceHistory
        ProductListPriceHistoryDTO productListPriceHistoryDTO = productListPriceHistoryMapper.toDto(productListPriceHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductListPriceHistoryMockMvc.perform(put("/api/product-list-price-histories").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productListPriceHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductListPriceHistory in the database
        List<ProductListPriceHistory> productListPriceHistoryList = productListPriceHistoryRepository.findAll();
        assertThat(productListPriceHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductListPriceHistory() throws Exception {
        // Initialize the database
        productListPriceHistoryRepository.saveAndFlush(productListPriceHistory);

        int databaseSizeBeforeDelete = productListPriceHistoryRepository.findAll().size();

        // Delete the productListPriceHistory
        restProductListPriceHistoryMockMvc.perform(delete("/api/product-list-price-histories/{id}", productListPriceHistory.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductListPriceHistory> productListPriceHistoryList = productListPriceHistoryRepository.findAll();
        assertThat(productListPriceHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
