package com.vertical.commerce.web.rest;

import com.vertical.commerce.VscommerceApp;
import com.vertical.commerce.config.TestSecurityConfiguration;
import com.vertical.commerce.domain.Products;
import com.vertical.commerce.domain.ProductDocument;
import com.vertical.commerce.domain.StockItems;
import com.vertical.commerce.domain.Suppliers;
import com.vertical.commerce.domain.ProductCategory;
import com.vertical.commerce.domain.ProductBrand;
import com.vertical.commerce.repository.ProductsRepository;
import com.vertical.commerce.service.ProductsService;
import com.vertical.commerce.service.dto.ProductsDTO;
import com.vertical.commerce.service.mapper.ProductsMapper;
import com.vertical.commerce.service.dto.ProductsCriteria;
import com.vertical.commerce.service.ProductsQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductsResource} REST controller.
 */
@SpringBootTest(classes = { VscommerceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ProductsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_HANDLE = "AAAAAAAAAA";
    private static final String UPDATED_HANDLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SEARCH_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_SEARCH_DETAILS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SELL_COUNT = 1;
    private static final Integer UPDATED_SELL_COUNT = 2;
    private static final Integer SMALLER_SELL_COUNT = 1 - 1;

    private static final String DEFAULT_STOCK_ITEM_STRING = "AAAAAAAAAA";
    private static final String UPDATED_STOCK_ITEM_STRING = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_WISHLIST = 1;
    private static final Integer UPDATED_TOTAL_WISHLIST = 2;
    private static final Integer SMALLER_TOTAL_WISHLIST = 1 - 1;

    private static final Integer DEFAULT_TOTAL_STARS = 1;
    private static final Integer UPDATED_TOTAL_STARS = 2;
    private static final Integer SMALLER_TOTAL_STARS = 1 - 1;

    private static final Integer DEFAULT_DISCOUNTED_PERCENTAGE = 1;
    private static final Integer UPDATED_DISCOUNTED_PERCENTAGE = 2;
    private static final Integer SMALLER_DISCOUNTED_PERCENTAGE = 1 - 1;

    private static final Boolean DEFAULT_PREFERRED_IND = false;
    private static final Boolean UPDATED_PREFERRED_IND = true;

    private static final Boolean DEFAULT_AVAILABLE_DELIVERY_IND = false;
    private static final Boolean UPDATED_AVAILABLE_DELIVERY_IND = true;

    private static final Boolean DEFAULT_ACTIVE_IND = false;
    private static final Boolean UPDATED_ACTIVE_IND = true;

    private static final Boolean DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND = false;
    private static final Boolean UPDATED_QUESTIONS_ABOUT_PRODUCT_IND = true;

    private static final String DEFAULT_LAST_EDITED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_EDITED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_EDITED_WHEN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_EDITED_WHEN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RELEASE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RELEASE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_AVAILABLE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AVAILABLE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductsMapper productsMapper;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private ProductsQueryService productsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductsMockMvc;

    private Products products;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Products createEntity(EntityManager em) {
        Products products = new Products()
            .name(DEFAULT_NAME)
            .handle(DEFAULT_HANDLE)
            .productNumber(DEFAULT_PRODUCT_NUMBER)
            .searchDetails(DEFAULT_SEARCH_DETAILS)
            .sellCount(DEFAULT_SELL_COUNT)
            .stockItemString(DEFAULT_STOCK_ITEM_STRING)
            .totalWishlist(DEFAULT_TOTAL_WISHLIST)
            .totalStars(DEFAULT_TOTAL_STARS)
            .discountedPercentage(DEFAULT_DISCOUNTED_PERCENTAGE)
            .preferredInd(DEFAULT_PREFERRED_IND)
            .availableDeliveryInd(DEFAULT_AVAILABLE_DELIVERY_IND)
            .activeInd(DEFAULT_ACTIVE_IND)
            .questionsAboutProductInd(DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND)
            .lastEditedBy(DEFAULT_LAST_EDITED_BY)
            .lastEditedWhen(DEFAULT_LAST_EDITED_WHEN)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .availableDate(DEFAULT_AVAILABLE_DATE);
        return products;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Products createUpdatedEntity(EntityManager em) {
        Products products = new Products()
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .searchDetails(UPDATED_SEARCH_DETAILS)
            .sellCount(UPDATED_SELL_COUNT)
            .stockItemString(UPDATED_STOCK_ITEM_STRING)
            .totalWishlist(UPDATED_TOTAL_WISHLIST)
            .totalStars(UPDATED_TOTAL_STARS)
            .discountedPercentage(UPDATED_DISCOUNTED_PERCENTAGE)
            .preferredInd(UPDATED_PREFERRED_IND)
            .availableDeliveryInd(UPDATED_AVAILABLE_DELIVERY_IND)
            .activeInd(UPDATED_ACTIVE_IND)
            .questionsAboutProductInd(UPDATED_QUESTIONS_ABOUT_PRODUCT_IND)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN)
            .releaseDate(UPDATED_RELEASE_DATE)
            .availableDate(UPDATED_AVAILABLE_DATE);
        return products;
    }

    @BeforeEach
    public void initTest() {
        products = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducts() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();
        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);
        restProductsMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isCreated());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate + 1);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProducts.getHandle()).isEqualTo(DEFAULT_HANDLE);
        assertThat(testProducts.getProductNumber()).isEqualTo(DEFAULT_PRODUCT_NUMBER);
        assertThat(testProducts.getSearchDetails()).isEqualTo(DEFAULT_SEARCH_DETAILS);
        assertThat(testProducts.getSellCount()).isEqualTo(DEFAULT_SELL_COUNT);
        assertThat(testProducts.getStockItemString()).isEqualTo(DEFAULT_STOCK_ITEM_STRING);
        assertThat(testProducts.getTotalWishlist()).isEqualTo(DEFAULT_TOTAL_WISHLIST);
        assertThat(testProducts.getTotalStars()).isEqualTo(DEFAULT_TOTAL_STARS);
        assertThat(testProducts.getDiscountedPercentage()).isEqualTo(DEFAULT_DISCOUNTED_PERCENTAGE);
        assertThat(testProducts.isPreferredInd()).isEqualTo(DEFAULT_PREFERRED_IND);
        assertThat(testProducts.isAvailableDeliveryInd()).isEqualTo(DEFAULT_AVAILABLE_DELIVERY_IND);
        assertThat(testProducts.isActiveInd()).isEqualTo(DEFAULT_ACTIVE_IND);
        assertThat(testProducts.isQuestionsAboutProductInd()).isEqualTo(DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND);
        assertThat(testProducts.getLastEditedBy()).isEqualTo(DEFAULT_LAST_EDITED_BY);
        assertThat(testProducts.getLastEditedWhen()).isEqualTo(DEFAULT_LAST_EDITED_WHEN);
        assertThat(testProducts.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testProducts.getAvailableDate()).isEqualTo(DEFAULT_AVAILABLE_DATE);
    }

    @Test
    @Transactional
    public void createProductsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productsRepository.findAll().size();

        // Create the Products with an existing ID
        products.setId(1L);
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductsMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setName(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);


        restProductsMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setLastEditedBy(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);


        restProductsMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastEditedWhenIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setLastEditedWhen(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);


        restProductsMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReleaseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setReleaseDate(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);


        restProductsMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAvailableDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = productsRepository.findAll().size();
        // set the field null
        products.setAvailableDate(null);

        // Create the Products, which fails.
        ProductsDTO productsDTO = productsMapper.toDto(products);


        restProductsMockMvc.perform(post("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList
        restProductsMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS)))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].stockItemString").value(hasItem(DEFAULT_STOCK_ITEM_STRING.toString())))
            .andExpect(jsonPath("$.[*].totalWishlist").value(hasItem(DEFAULT_TOTAL_WISHLIST)))
            .andExpect(jsonPath("$.[*].totalStars").value(hasItem(DEFAULT_TOTAL_STARS)))
            .andExpect(jsonPath("$.[*].discountedPercentage").value(hasItem(DEFAULT_DISCOUNTED_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].preferredInd").value(hasItem(DEFAULT_PREFERRED_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].availableDeliveryInd").value(hasItem(DEFAULT_AVAILABLE_DELIVERY_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].questionsAboutProductInd").value(hasItem(DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].availableDate").value(hasItem(DEFAULT_AVAILABLE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get the products
        restProductsMockMvc.perform(get("/api/products/{id}", products.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(products.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.handle").value(DEFAULT_HANDLE))
            .andExpect(jsonPath("$.productNumber").value(DEFAULT_PRODUCT_NUMBER))
            .andExpect(jsonPath("$.searchDetails").value(DEFAULT_SEARCH_DETAILS))
            .andExpect(jsonPath("$.sellCount").value(DEFAULT_SELL_COUNT))
            .andExpect(jsonPath("$.stockItemString").value(DEFAULT_STOCK_ITEM_STRING.toString()))
            .andExpect(jsonPath("$.totalWishlist").value(DEFAULT_TOTAL_WISHLIST))
            .andExpect(jsonPath("$.totalStars").value(DEFAULT_TOTAL_STARS))
            .andExpect(jsonPath("$.discountedPercentage").value(DEFAULT_DISCOUNTED_PERCENTAGE))
            .andExpect(jsonPath("$.preferredInd").value(DEFAULT_PREFERRED_IND.booleanValue()))
            .andExpect(jsonPath("$.availableDeliveryInd").value(DEFAULT_AVAILABLE_DELIVERY_IND.booleanValue()))
            .andExpect(jsonPath("$.activeInd").value(DEFAULT_ACTIVE_IND.booleanValue()))
            .andExpect(jsonPath("$.questionsAboutProductInd").value(DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND.booleanValue()))
            .andExpect(jsonPath("$.lastEditedBy").value(DEFAULT_LAST_EDITED_BY))
            .andExpect(jsonPath("$.lastEditedWhen").value(DEFAULT_LAST_EDITED_WHEN.toString()))
            .andExpect(jsonPath("$.releaseDate").value(DEFAULT_RELEASE_DATE.toString()))
            .andExpect(jsonPath("$.availableDate").value(DEFAULT_AVAILABLE_DATE.toString()));
    }


    @Test
    @Transactional
    public void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        Long id = products.getId();

        defaultProductsShouldBeFound("id.equals=" + id);
        defaultProductsShouldNotBeFound("id.notEquals=" + id);

        defaultProductsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductsShouldNotBeFound("id.greaterThan=" + id);

        defaultProductsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductsShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where name equals to DEFAULT_NAME
        defaultProductsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productsList where name equals to UPDATED_NAME
        defaultProductsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where name not equals to DEFAULT_NAME
        defaultProductsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productsList where name not equals to UPDATED_NAME
        defaultProductsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productsList where name equals to UPDATED_NAME
        defaultProductsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where name is not null
        defaultProductsShouldBeFound("name.specified=true");

        // Get all the productsList where name is null
        defaultProductsShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByNameContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where name contains DEFAULT_NAME
        defaultProductsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productsList where name contains UPDATED_NAME
        defaultProductsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where name does not contain DEFAULT_NAME
        defaultProductsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productsList where name does not contain UPDATED_NAME
        defaultProductsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsByHandleIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle equals to DEFAULT_HANDLE
        defaultProductsShouldBeFound("handle.equals=" + DEFAULT_HANDLE);

        // Get all the productsList where handle equals to UPDATED_HANDLE
        defaultProductsShouldNotBeFound("handle.equals=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductsByHandleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle not equals to DEFAULT_HANDLE
        defaultProductsShouldNotBeFound("handle.notEquals=" + DEFAULT_HANDLE);

        // Get all the productsList where handle not equals to UPDATED_HANDLE
        defaultProductsShouldBeFound("handle.notEquals=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductsByHandleIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle in DEFAULT_HANDLE or UPDATED_HANDLE
        defaultProductsShouldBeFound("handle.in=" + DEFAULT_HANDLE + "," + UPDATED_HANDLE);

        // Get all the productsList where handle equals to UPDATED_HANDLE
        defaultProductsShouldNotBeFound("handle.in=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductsByHandleIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle is not null
        defaultProductsShouldBeFound("handle.specified=true");

        // Get all the productsList where handle is null
        defaultProductsShouldNotBeFound("handle.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByHandleContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle contains DEFAULT_HANDLE
        defaultProductsShouldBeFound("handle.contains=" + DEFAULT_HANDLE);

        // Get all the productsList where handle contains UPDATED_HANDLE
        defaultProductsShouldNotBeFound("handle.contains=" + UPDATED_HANDLE);
    }

    @Test
    @Transactional
    public void getAllProductsByHandleNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where handle does not contain DEFAULT_HANDLE
        defaultProductsShouldNotBeFound("handle.doesNotContain=" + DEFAULT_HANDLE);

        // Get all the productsList where handle does not contain UPDATED_HANDLE
        defaultProductsShouldBeFound("handle.doesNotContain=" + UPDATED_HANDLE);
    }


    @Test
    @Transactional
    public void getAllProductsByProductNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber equals to DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.equals=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber equals to UPDATED_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.equals=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber not equals to DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.notEquals=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber not equals to UPDATED_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.notEquals=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber in DEFAULT_PRODUCT_NUMBER or UPDATED_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.in=" + DEFAULT_PRODUCT_NUMBER + "," + UPDATED_PRODUCT_NUMBER);

        // Get all the productsList where productNumber equals to UPDATED_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.in=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber is not null
        defaultProductsShouldBeFound("productNumber.specified=true");

        // Get all the productsList where productNumber is null
        defaultProductsShouldNotBeFound("productNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByProductNumberContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber contains DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.contains=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber contains UPDATED_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.contains=" + UPDATED_PRODUCT_NUMBER);
    }

    @Test
    @Transactional
    public void getAllProductsByProductNumberNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where productNumber does not contain DEFAULT_PRODUCT_NUMBER
        defaultProductsShouldNotBeFound("productNumber.doesNotContain=" + DEFAULT_PRODUCT_NUMBER);

        // Get all the productsList where productNumber does not contain UPDATED_PRODUCT_NUMBER
        defaultProductsShouldBeFound("productNumber.doesNotContain=" + UPDATED_PRODUCT_NUMBER);
    }


    @Test
    @Transactional
    public void getAllProductsBySearchDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails equals to DEFAULT_SEARCH_DETAILS
        defaultProductsShouldBeFound("searchDetails.equals=" + DEFAULT_SEARCH_DETAILS);

        // Get all the productsList where searchDetails equals to UPDATED_SEARCH_DETAILS
        defaultProductsShouldNotBeFound("searchDetails.equals=" + UPDATED_SEARCH_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductsBySearchDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails not equals to DEFAULT_SEARCH_DETAILS
        defaultProductsShouldNotBeFound("searchDetails.notEquals=" + DEFAULT_SEARCH_DETAILS);

        // Get all the productsList where searchDetails not equals to UPDATED_SEARCH_DETAILS
        defaultProductsShouldBeFound("searchDetails.notEquals=" + UPDATED_SEARCH_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductsBySearchDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails in DEFAULT_SEARCH_DETAILS or UPDATED_SEARCH_DETAILS
        defaultProductsShouldBeFound("searchDetails.in=" + DEFAULT_SEARCH_DETAILS + "," + UPDATED_SEARCH_DETAILS);

        // Get all the productsList where searchDetails equals to UPDATED_SEARCH_DETAILS
        defaultProductsShouldNotBeFound("searchDetails.in=" + UPDATED_SEARCH_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductsBySearchDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails is not null
        defaultProductsShouldBeFound("searchDetails.specified=true");

        // Get all the productsList where searchDetails is null
        defaultProductsShouldNotBeFound("searchDetails.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsBySearchDetailsContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails contains DEFAULT_SEARCH_DETAILS
        defaultProductsShouldBeFound("searchDetails.contains=" + DEFAULT_SEARCH_DETAILS);

        // Get all the productsList where searchDetails contains UPDATED_SEARCH_DETAILS
        defaultProductsShouldNotBeFound("searchDetails.contains=" + UPDATED_SEARCH_DETAILS);
    }

    @Test
    @Transactional
    public void getAllProductsBySearchDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where searchDetails does not contain DEFAULT_SEARCH_DETAILS
        defaultProductsShouldNotBeFound("searchDetails.doesNotContain=" + DEFAULT_SEARCH_DETAILS);

        // Get all the productsList where searchDetails does not contain UPDATED_SEARCH_DETAILS
        defaultProductsShouldBeFound("searchDetails.doesNotContain=" + UPDATED_SEARCH_DETAILS);
    }


    @Test
    @Transactional
    public void getAllProductsBySellCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount equals to DEFAULT_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.equals=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount equals to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.equals=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount not equals to DEFAULT_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.notEquals=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount not equals to UPDATED_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.notEquals=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount in DEFAULT_SELL_COUNT or UPDATED_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.in=" + DEFAULT_SELL_COUNT + "," + UPDATED_SELL_COUNT);

        // Get all the productsList where sellCount equals to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.in=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is not null
        defaultProductsShouldBeFound("sellCount.specified=true");

        // Get all the productsList where sellCount is null
        defaultProductsShouldNotBeFound("sellCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is greater than or equal to DEFAULT_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.greaterThanOrEqual=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount is greater than or equal to UPDATED_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.greaterThanOrEqual=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is less than or equal to DEFAULT_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.lessThanOrEqual=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount is less than or equal to SMALLER_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.lessThanOrEqual=" + SMALLER_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is less than DEFAULT_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.lessThan=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount is less than UPDATED_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.lessThan=" + UPDATED_SELL_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductsBySellCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where sellCount is greater than DEFAULT_SELL_COUNT
        defaultProductsShouldNotBeFound("sellCount.greaterThan=" + DEFAULT_SELL_COUNT);

        // Get all the productsList where sellCount is greater than SMALLER_SELL_COUNT
        defaultProductsShouldBeFound("sellCount.greaterThan=" + SMALLER_SELL_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductsByTotalWishlistIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalWishlist equals to DEFAULT_TOTAL_WISHLIST
        defaultProductsShouldBeFound("totalWishlist.equals=" + DEFAULT_TOTAL_WISHLIST);

        // Get all the productsList where totalWishlist equals to UPDATED_TOTAL_WISHLIST
        defaultProductsShouldNotBeFound("totalWishlist.equals=" + UPDATED_TOTAL_WISHLIST);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalWishlistIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalWishlist not equals to DEFAULT_TOTAL_WISHLIST
        defaultProductsShouldNotBeFound("totalWishlist.notEquals=" + DEFAULT_TOTAL_WISHLIST);

        // Get all the productsList where totalWishlist not equals to UPDATED_TOTAL_WISHLIST
        defaultProductsShouldBeFound("totalWishlist.notEquals=" + UPDATED_TOTAL_WISHLIST);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalWishlistIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalWishlist in DEFAULT_TOTAL_WISHLIST or UPDATED_TOTAL_WISHLIST
        defaultProductsShouldBeFound("totalWishlist.in=" + DEFAULT_TOTAL_WISHLIST + "," + UPDATED_TOTAL_WISHLIST);

        // Get all the productsList where totalWishlist equals to UPDATED_TOTAL_WISHLIST
        defaultProductsShouldNotBeFound("totalWishlist.in=" + UPDATED_TOTAL_WISHLIST);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalWishlistIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalWishlist is not null
        defaultProductsShouldBeFound("totalWishlist.specified=true");

        // Get all the productsList where totalWishlist is null
        defaultProductsShouldNotBeFound("totalWishlist.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByTotalWishlistIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalWishlist is greater than or equal to DEFAULT_TOTAL_WISHLIST
        defaultProductsShouldBeFound("totalWishlist.greaterThanOrEqual=" + DEFAULT_TOTAL_WISHLIST);

        // Get all the productsList where totalWishlist is greater than or equal to UPDATED_TOTAL_WISHLIST
        defaultProductsShouldNotBeFound("totalWishlist.greaterThanOrEqual=" + UPDATED_TOTAL_WISHLIST);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalWishlistIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalWishlist is less than or equal to DEFAULT_TOTAL_WISHLIST
        defaultProductsShouldBeFound("totalWishlist.lessThanOrEqual=" + DEFAULT_TOTAL_WISHLIST);

        // Get all the productsList where totalWishlist is less than or equal to SMALLER_TOTAL_WISHLIST
        defaultProductsShouldNotBeFound("totalWishlist.lessThanOrEqual=" + SMALLER_TOTAL_WISHLIST);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalWishlistIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalWishlist is less than DEFAULT_TOTAL_WISHLIST
        defaultProductsShouldNotBeFound("totalWishlist.lessThan=" + DEFAULT_TOTAL_WISHLIST);

        // Get all the productsList where totalWishlist is less than UPDATED_TOTAL_WISHLIST
        defaultProductsShouldBeFound("totalWishlist.lessThan=" + UPDATED_TOTAL_WISHLIST);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalWishlistIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalWishlist is greater than DEFAULT_TOTAL_WISHLIST
        defaultProductsShouldNotBeFound("totalWishlist.greaterThan=" + DEFAULT_TOTAL_WISHLIST);

        // Get all the productsList where totalWishlist is greater than SMALLER_TOTAL_WISHLIST
        defaultProductsShouldBeFound("totalWishlist.greaterThan=" + SMALLER_TOTAL_WISHLIST);
    }


    @Test
    @Transactional
    public void getAllProductsByTotalStarsIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalStars equals to DEFAULT_TOTAL_STARS
        defaultProductsShouldBeFound("totalStars.equals=" + DEFAULT_TOTAL_STARS);

        // Get all the productsList where totalStars equals to UPDATED_TOTAL_STARS
        defaultProductsShouldNotBeFound("totalStars.equals=" + UPDATED_TOTAL_STARS);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalStarsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalStars not equals to DEFAULT_TOTAL_STARS
        defaultProductsShouldNotBeFound("totalStars.notEquals=" + DEFAULT_TOTAL_STARS);

        // Get all the productsList where totalStars not equals to UPDATED_TOTAL_STARS
        defaultProductsShouldBeFound("totalStars.notEquals=" + UPDATED_TOTAL_STARS);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalStarsIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalStars in DEFAULT_TOTAL_STARS or UPDATED_TOTAL_STARS
        defaultProductsShouldBeFound("totalStars.in=" + DEFAULT_TOTAL_STARS + "," + UPDATED_TOTAL_STARS);

        // Get all the productsList where totalStars equals to UPDATED_TOTAL_STARS
        defaultProductsShouldNotBeFound("totalStars.in=" + UPDATED_TOTAL_STARS);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalStarsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalStars is not null
        defaultProductsShouldBeFound("totalStars.specified=true");

        // Get all the productsList where totalStars is null
        defaultProductsShouldNotBeFound("totalStars.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByTotalStarsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalStars is greater than or equal to DEFAULT_TOTAL_STARS
        defaultProductsShouldBeFound("totalStars.greaterThanOrEqual=" + DEFAULT_TOTAL_STARS);

        // Get all the productsList where totalStars is greater than or equal to UPDATED_TOTAL_STARS
        defaultProductsShouldNotBeFound("totalStars.greaterThanOrEqual=" + UPDATED_TOTAL_STARS);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalStarsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalStars is less than or equal to DEFAULT_TOTAL_STARS
        defaultProductsShouldBeFound("totalStars.lessThanOrEqual=" + DEFAULT_TOTAL_STARS);

        // Get all the productsList where totalStars is less than or equal to SMALLER_TOTAL_STARS
        defaultProductsShouldNotBeFound("totalStars.lessThanOrEqual=" + SMALLER_TOTAL_STARS);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalStarsIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalStars is less than DEFAULT_TOTAL_STARS
        defaultProductsShouldNotBeFound("totalStars.lessThan=" + DEFAULT_TOTAL_STARS);

        // Get all the productsList where totalStars is less than UPDATED_TOTAL_STARS
        defaultProductsShouldBeFound("totalStars.lessThan=" + UPDATED_TOTAL_STARS);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalStarsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where totalStars is greater than DEFAULT_TOTAL_STARS
        defaultProductsShouldNotBeFound("totalStars.greaterThan=" + DEFAULT_TOTAL_STARS);

        // Get all the productsList where totalStars is greater than SMALLER_TOTAL_STARS
        defaultProductsShouldBeFound("totalStars.greaterThan=" + SMALLER_TOTAL_STARS);
    }


    @Test
    @Transactional
    public void getAllProductsByDiscountedPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discountedPercentage equals to DEFAULT_DISCOUNTED_PERCENTAGE
        defaultProductsShouldBeFound("discountedPercentage.equals=" + DEFAULT_DISCOUNTED_PERCENTAGE);

        // Get all the productsList where discountedPercentage equals to UPDATED_DISCOUNTED_PERCENTAGE
        defaultProductsShouldNotBeFound("discountedPercentage.equals=" + UPDATED_DISCOUNTED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscountedPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discountedPercentage not equals to DEFAULT_DISCOUNTED_PERCENTAGE
        defaultProductsShouldNotBeFound("discountedPercentage.notEquals=" + DEFAULT_DISCOUNTED_PERCENTAGE);

        // Get all the productsList where discountedPercentage not equals to UPDATED_DISCOUNTED_PERCENTAGE
        defaultProductsShouldBeFound("discountedPercentage.notEquals=" + UPDATED_DISCOUNTED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscountedPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discountedPercentage in DEFAULT_DISCOUNTED_PERCENTAGE or UPDATED_DISCOUNTED_PERCENTAGE
        defaultProductsShouldBeFound("discountedPercentage.in=" + DEFAULT_DISCOUNTED_PERCENTAGE + "," + UPDATED_DISCOUNTED_PERCENTAGE);

        // Get all the productsList where discountedPercentage equals to UPDATED_DISCOUNTED_PERCENTAGE
        defaultProductsShouldNotBeFound("discountedPercentage.in=" + UPDATED_DISCOUNTED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscountedPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discountedPercentage is not null
        defaultProductsShouldBeFound("discountedPercentage.specified=true");

        // Get all the productsList where discountedPercentage is null
        defaultProductsShouldNotBeFound("discountedPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByDiscountedPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discountedPercentage is greater than or equal to DEFAULT_DISCOUNTED_PERCENTAGE
        defaultProductsShouldBeFound("discountedPercentage.greaterThanOrEqual=" + DEFAULT_DISCOUNTED_PERCENTAGE);

        // Get all the productsList where discountedPercentage is greater than or equal to UPDATED_DISCOUNTED_PERCENTAGE
        defaultProductsShouldNotBeFound("discountedPercentage.greaterThanOrEqual=" + UPDATED_DISCOUNTED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscountedPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discountedPercentage is less than or equal to DEFAULT_DISCOUNTED_PERCENTAGE
        defaultProductsShouldBeFound("discountedPercentage.lessThanOrEqual=" + DEFAULT_DISCOUNTED_PERCENTAGE);

        // Get all the productsList where discountedPercentage is less than or equal to SMALLER_DISCOUNTED_PERCENTAGE
        defaultProductsShouldNotBeFound("discountedPercentage.lessThanOrEqual=" + SMALLER_DISCOUNTED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscountedPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discountedPercentage is less than DEFAULT_DISCOUNTED_PERCENTAGE
        defaultProductsShouldNotBeFound("discountedPercentage.lessThan=" + DEFAULT_DISCOUNTED_PERCENTAGE);

        // Get all the productsList where discountedPercentage is less than UPDATED_DISCOUNTED_PERCENTAGE
        defaultProductsShouldBeFound("discountedPercentage.lessThan=" + UPDATED_DISCOUNTED_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByDiscountedPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where discountedPercentage is greater than DEFAULT_DISCOUNTED_PERCENTAGE
        defaultProductsShouldNotBeFound("discountedPercentage.greaterThan=" + DEFAULT_DISCOUNTED_PERCENTAGE);

        // Get all the productsList where discountedPercentage is greater than SMALLER_DISCOUNTED_PERCENTAGE
        defaultProductsShouldBeFound("discountedPercentage.greaterThan=" + SMALLER_DISCOUNTED_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllProductsByPreferredIndIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where preferredInd equals to DEFAULT_PREFERRED_IND
        defaultProductsShouldBeFound("preferredInd.equals=" + DEFAULT_PREFERRED_IND);

        // Get all the productsList where preferredInd equals to UPDATED_PREFERRED_IND
        defaultProductsShouldNotBeFound("preferredInd.equals=" + UPDATED_PREFERRED_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByPreferredIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where preferredInd not equals to DEFAULT_PREFERRED_IND
        defaultProductsShouldNotBeFound("preferredInd.notEquals=" + DEFAULT_PREFERRED_IND);

        // Get all the productsList where preferredInd not equals to UPDATED_PREFERRED_IND
        defaultProductsShouldBeFound("preferredInd.notEquals=" + UPDATED_PREFERRED_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByPreferredIndIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where preferredInd in DEFAULT_PREFERRED_IND or UPDATED_PREFERRED_IND
        defaultProductsShouldBeFound("preferredInd.in=" + DEFAULT_PREFERRED_IND + "," + UPDATED_PREFERRED_IND);

        // Get all the productsList where preferredInd equals to UPDATED_PREFERRED_IND
        defaultProductsShouldNotBeFound("preferredInd.in=" + UPDATED_PREFERRED_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByPreferredIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where preferredInd is not null
        defaultProductsShouldBeFound("preferredInd.specified=true");

        // Get all the productsList where preferredInd is null
        defaultProductsShouldNotBeFound("preferredInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableDeliveryIndIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where availableDeliveryInd equals to DEFAULT_AVAILABLE_DELIVERY_IND
        defaultProductsShouldBeFound("availableDeliveryInd.equals=" + DEFAULT_AVAILABLE_DELIVERY_IND);

        // Get all the productsList where availableDeliveryInd equals to UPDATED_AVAILABLE_DELIVERY_IND
        defaultProductsShouldNotBeFound("availableDeliveryInd.equals=" + UPDATED_AVAILABLE_DELIVERY_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableDeliveryIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where availableDeliveryInd not equals to DEFAULT_AVAILABLE_DELIVERY_IND
        defaultProductsShouldNotBeFound("availableDeliveryInd.notEquals=" + DEFAULT_AVAILABLE_DELIVERY_IND);

        // Get all the productsList where availableDeliveryInd not equals to UPDATED_AVAILABLE_DELIVERY_IND
        defaultProductsShouldBeFound("availableDeliveryInd.notEquals=" + UPDATED_AVAILABLE_DELIVERY_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableDeliveryIndIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where availableDeliveryInd in DEFAULT_AVAILABLE_DELIVERY_IND or UPDATED_AVAILABLE_DELIVERY_IND
        defaultProductsShouldBeFound("availableDeliveryInd.in=" + DEFAULT_AVAILABLE_DELIVERY_IND + "," + UPDATED_AVAILABLE_DELIVERY_IND);

        // Get all the productsList where availableDeliveryInd equals to UPDATED_AVAILABLE_DELIVERY_IND
        defaultProductsShouldNotBeFound("availableDeliveryInd.in=" + UPDATED_AVAILABLE_DELIVERY_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableDeliveryIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where availableDeliveryInd is not null
        defaultProductsShouldBeFound("availableDeliveryInd.specified=true");

        // Get all the productsList where availableDeliveryInd is null
        defaultProductsShouldNotBeFound("availableDeliveryInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByActiveIndIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where activeInd equals to DEFAULT_ACTIVE_IND
        defaultProductsShouldBeFound("activeInd.equals=" + DEFAULT_ACTIVE_IND);

        // Get all the productsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultProductsShouldNotBeFound("activeInd.equals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByActiveIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where activeInd not equals to DEFAULT_ACTIVE_IND
        defaultProductsShouldNotBeFound("activeInd.notEquals=" + DEFAULT_ACTIVE_IND);

        // Get all the productsList where activeInd not equals to UPDATED_ACTIVE_IND
        defaultProductsShouldBeFound("activeInd.notEquals=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByActiveIndIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where activeInd in DEFAULT_ACTIVE_IND or UPDATED_ACTIVE_IND
        defaultProductsShouldBeFound("activeInd.in=" + DEFAULT_ACTIVE_IND + "," + UPDATED_ACTIVE_IND);

        // Get all the productsList where activeInd equals to UPDATED_ACTIVE_IND
        defaultProductsShouldNotBeFound("activeInd.in=" + UPDATED_ACTIVE_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByActiveIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where activeInd is not null
        defaultProductsShouldBeFound("activeInd.specified=true");

        // Get all the productsList where activeInd is null
        defaultProductsShouldNotBeFound("activeInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByQuestionsAboutProductIndIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where questionsAboutProductInd equals to DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND
        defaultProductsShouldBeFound("questionsAboutProductInd.equals=" + DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND);

        // Get all the productsList where questionsAboutProductInd equals to UPDATED_QUESTIONS_ABOUT_PRODUCT_IND
        defaultProductsShouldNotBeFound("questionsAboutProductInd.equals=" + UPDATED_QUESTIONS_ABOUT_PRODUCT_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByQuestionsAboutProductIndIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where questionsAboutProductInd not equals to DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND
        defaultProductsShouldNotBeFound("questionsAboutProductInd.notEquals=" + DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND);

        // Get all the productsList where questionsAboutProductInd not equals to UPDATED_QUESTIONS_ABOUT_PRODUCT_IND
        defaultProductsShouldBeFound("questionsAboutProductInd.notEquals=" + UPDATED_QUESTIONS_ABOUT_PRODUCT_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByQuestionsAboutProductIndIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where questionsAboutProductInd in DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND or UPDATED_QUESTIONS_ABOUT_PRODUCT_IND
        defaultProductsShouldBeFound("questionsAboutProductInd.in=" + DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND + "," + UPDATED_QUESTIONS_ABOUT_PRODUCT_IND);

        // Get all the productsList where questionsAboutProductInd equals to UPDATED_QUESTIONS_ABOUT_PRODUCT_IND
        defaultProductsShouldNotBeFound("questionsAboutProductInd.in=" + UPDATED_QUESTIONS_ABOUT_PRODUCT_IND);
    }

    @Test
    @Transactional
    public void getAllProductsByQuestionsAboutProductIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where questionsAboutProductInd is not null
        defaultProductsShouldBeFound("questionsAboutProductInd.specified=true");

        // Get all the productsList where questionsAboutProductInd is null
        defaultProductsShouldNotBeFound("questionsAboutProductInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy equals to DEFAULT_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.equals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.equals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy not equals to DEFAULT_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.notEquals=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy not equals to UPDATED_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.notEquals=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy in DEFAULT_LAST_EDITED_BY or UPDATED_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.in=" + DEFAULT_LAST_EDITED_BY + "," + UPDATED_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy equals to UPDATED_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.in=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy is not null
        defaultProductsShouldBeFound("lastEditedBy.specified=true");

        // Get all the productsList where lastEditedBy is null
        defaultProductsShouldNotBeFound("lastEditedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByLastEditedByContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy contains DEFAULT_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.contains=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy contains UPDATED_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.contains=" + UPDATED_LAST_EDITED_BY);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedByNotContainsSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedBy does not contain DEFAULT_LAST_EDITED_BY
        defaultProductsShouldNotBeFound("lastEditedBy.doesNotContain=" + DEFAULT_LAST_EDITED_BY);

        // Get all the productsList where lastEditedBy does not contain UPDATED_LAST_EDITED_BY
        defaultProductsShouldBeFound("lastEditedBy.doesNotContain=" + UPDATED_LAST_EDITED_BY);
    }


    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen equals to DEFAULT_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.equals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.equals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen not equals to DEFAULT_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.notEquals=" + DEFAULT_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen not equals to UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.notEquals=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen in DEFAULT_LAST_EDITED_WHEN or UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldBeFound("lastEditedWhen.in=" + DEFAULT_LAST_EDITED_WHEN + "," + UPDATED_LAST_EDITED_WHEN);

        // Get all the productsList where lastEditedWhen equals to UPDATED_LAST_EDITED_WHEN
        defaultProductsShouldNotBeFound("lastEditedWhen.in=" + UPDATED_LAST_EDITED_WHEN);
    }

    @Test
    @Transactional
    public void getAllProductsByLastEditedWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where lastEditedWhen is not null
        defaultProductsShouldBeFound("lastEditedWhen.specified=true");

        // Get all the productsList where lastEditedWhen is null
        defaultProductsShouldNotBeFound("lastEditedWhen.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where releaseDate equals to DEFAULT_RELEASE_DATE
        defaultProductsShouldBeFound("releaseDate.equals=" + DEFAULT_RELEASE_DATE);

        // Get all the productsList where releaseDate equals to UPDATED_RELEASE_DATE
        defaultProductsShouldNotBeFound("releaseDate.equals=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where releaseDate not equals to DEFAULT_RELEASE_DATE
        defaultProductsShouldNotBeFound("releaseDate.notEquals=" + DEFAULT_RELEASE_DATE);

        // Get all the productsList where releaseDate not equals to UPDATED_RELEASE_DATE
        defaultProductsShouldBeFound("releaseDate.notEquals=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where releaseDate in DEFAULT_RELEASE_DATE or UPDATED_RELEASE_DATE
        defaultProductsShouldBeFound("releaseDate.in=" + DEFAULT_RELEASE_DATE + "," + UPDATED_RELEASE_DATE);

        // Get all the productsList where releaseDate equals to UPDATED_RELEASE_DATE
        defaultProductsShouldNotBeFound("releaseDate.in=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where releaseDate is not null
        defaultProductsShouldBeFound("releaseDate.specified=true");

        // Get all the productsList where releaseDate is null
        defaultProductsShouldNotBeFound("releaseDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where availableDate equals to DEFAULT_AVAILABLE_DATE
        defaultProductsShouldBeFound("availableDate.equals=" + DEFAULT_AVAILABLE_DATE);

        // Get all the productsList where availableDate equals to UPDATED_AVAILABLE_DATE
        defaultProductsShouldNotBeFound("availableDate.equals=" + UPDATED_AVAILABLE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where availableDate not equals to DEFAULT_AVAILABLE_DATE
        defaultProductsShouldNotBeFound("availableDate.notEquals=" + DEFAULT_AVAILABLE_DATE);

        // Get all the productsList where availableDate not equals to UPDATED_AVAILABLE_DATE
        defaultProductsShouldBeFound("availableDate.notEquals=" + UPDATED_AVAILABLE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableDateIsInShouldWork() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where availableDate in DEFAULT_AVAILABLE_DATE or UPDATED_AVAILABLE_DATE
        defaultProductsShouldBeFound("availableDate.in=" + DEFAULT_AVAILABLE_DATE + "," + UPDATED_AVAILABLE_DATE);

        // Get all the productsList where availableDate equals to UPDATED_AVAILABLE_DATE
        defaultProductsShouldNotBeFound("availableDate.in=" + UPDATED_AVAILABLE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByAvailableDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        // Get all the productsList where availableDate is not null
        defaultProductsShouldBeFound("availableDate.specified=true");

        // Get all the productsList where availableDate is null
        defaultProductsShouldNotBeFound("availableDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByProductDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        ProductDocument productDocument = ProductDocumentResourceIT.createEntity(em);
        em.persist(productDocument);
        em.flush();
        products.setProductDocument(productDocument);
        productsRepository.saveAndFlush(products);
        Long productDocumentId = productDocument.getId();

        // Get all the productsList where productDocument equals to productDocumentId
        defaultProductsShouldBeFound("productDocumentId.equals=" + productDocumentId);

        // Get all the productsList where productDocument equals to productDocumentId + 1
        defaultProductsShouldNotBeFound("productDocumentId.equals=" + (productDocumentId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByStockItemListIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        StockItems stockItemList = StockItemsResourceIT.createEntity(em);
        em.persist(stockItemList);
        em.flush();
        products.addStockItemList(stockItemList);
        productsRepository.saveAndFlush(products);
        Long stockItemListId = stockItemList.getId();

        // Get all the productsList where stockItemList equals to stockItemListId
        defaultProductsShouldBeFound("stockItemListId.equals=" + stockItemListId);

        // Get all the productsList where stockItemList equals to stockItemListId + 1
        defaultProductsShouldNotBeFound("stockItemListId.equals=" + (stockItemListId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        Suppliers supplier = SuppliersResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        products.setSupplier(supplier);
        productsRepository.saveAndFlush(products);
        Long supplierId = supplier.getId();

        // Get all the productsList where supplier equals to supplierId
        defaultProductsShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the productsList where supplier equals to supplierId + 1
        defaultProductsShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        products.setProductCategory(productCategory);
        productsRepository.saveAndFlush(products);
        Long productCategoryId = productCategory.getId();

        // Get all the productsList where productCategory equals to productCategoryId
        defaultProductsShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productsList where productCategory equals to productCategoryId + 1
        defaultProductsShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByProductBrandIsEqualToSomething() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);
        ProductBrand productBrand = ProductBrandResourceIT.createEntity(em);
        em.persist(productBrand);
        em.flush();
        products.setProductBrand(productBrand);
        productsRepository.saveAndFlush(products);
        Long productBrandId = productBrand.getId();

        // Get all the productsList where productBrand equals to productBrandId
        defaultProductsShouldBeFound("productBrandId.equals=" + productBrandId);

        // Get all the productsList where productBrand equals to productBrandId + 1
        defaultProductsShouldNotBeFound("productBrandId.equals=" + (productBrandId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductsShouldBeFound(String filter) throws Exception {
        restProductsMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(products.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].handle").value(hasItem(DEFAULT_HANDLE)))
            .andExpect(jsonPath("$.[*].productNumber").value(hasItem(DEFAULT_PRODUCT_NUMBER)))
            .andExpect(jsonPath("$.[*].searchDetails").value(hasItem(DEFAULT_SEARCH_DETAILS)))
            .andExpect(jsonPath("$.[*].sellCount").value(hasItem(DEFAULT_SELL_COUNT)))
            .andExpect(jsonPath("$.[*].stockItemString").value(hasItem(DEFAULT_STOCK_ITEM_STRING.toString())))
            .andExpect(jsonPath("$.[*].totalWishlist").value(hasItem(DEFAULT_TOTAL_WISHLIST)))
            .andExpect(jsonPath("$.[*].totalStars").value(hasItem(DEFAULT_TOTAL_STARS)))
            .andExpect(jsonPath("$.[*].discountedPercentage").value(hasItem(DEFAULT_DISCOUNTED_PERCENTAGE)))
            .andExpect(jsonPath("$.[*].preferredInd").value(hasItem(DEFAULT_PREFERRED_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].availableDeliveryInd").value(hasItem(DEFAULT_AVAILABLE_DELIVERY_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].activeInd").value(hasItem(DEFAULT_ACTIVE_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].questionsAboutProductInd").value(hasItem(DEFAULT_QUESTIONS_ABOUT_PRODUCT_IND.booleanValue())))
            .andExpect(jsonPath("$.[*].lastEditedBy").value(hasItem(DEFAULT_LAST_EDITED_BY)))
            .andExpect(jsonPath("$.[*].lastEditedWhen").value(hasItem(DEFAULT_LAST_EDITED_WHEN.toString())))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(DEFAULT_RELEASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].availableDate").value(hasItem(DEFAULT_AVAILABLE_DATE.toString())));

        // Check, that the count call also returns 1
        restProductsMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductsShouldNotBeFound(String filter) throws Exception {
        restProductsMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductsMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProducts() throws Exception {
        // Get the products
        restProductsMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Update the products
        Products updatedProducts = productsRepository.findById(products.getId()).get();
        // Disconnect from session so that the updates on updatedProducts are not directly saved in db
        em.detach(updatedProducts);
        updatedProducts
            .name(UPDATED_NAME)
            .handle(UPDATED_HANDLE)
            .productNumber(UPDATED_PRODUCT_NUMBER)
            .searchDetails(UPDATED_SEARCH_DETAILS)
            .sellCount(UPDATED_SELL_COUNT)
            .stockItemString(UPDATED_STOCK_ITEM_STRING)
            .totalWishlist(UPDATED_TOTAL_WISHLIST)
            .totalStars(UPDATED_TOTAL_STARS)
            .discountedPercentage(UPDATED_DISCOUNTED_PERCENTAGE)
            .preferredInd(UPDATED_PREFERRED_IND)
            .availableDeliveryInd(UPDATED_AVAILABLE_DELIVERY_IND)
            .activeInd(UPDATED_ACTIVE_IND)
            .questionsAboutProductInd(UPDATED_QUESTIONS_ABOUT_PRODUCT_IND)
            .lastEditedBy(UPDATED_LAST_EDITED_BY)
            .lastEditedWhen(UPDATED_LAST_EDITED_WHEN)
            .releaseDate(UPDATED_RELEASE_DATE)
            .availableDate(UPDATED_AVAILABLE_DATE);
        ProductsDTO productsDTO = productsMapper.toDto(updatedProducts);

        restProductsMockMvc.perform(put("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isOk());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
        Products testProducts = productsList.get(productsList.size() - 1);
        assertThat(testProducts.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProducts.getHandle()).isEqualTo(UPDATED_HANDLE);
        assertThat(testProducts.getProductNumber()).isEqualTo(UPDATED_PRODUCT_NUMBER);
        assertThat(testProducts.getSearchDetails()).isEqualTo(UPDATED_SEARCH_DETAILS);
        assertThat(testProducts.getSellCount()).isEqualTo(UPDATED_SELL_COUNT);
        assertThat(testProducts.getStockItemString()).isEqualTo(UPDATED_STOCK_ITEM_STRING);
        assertThat(testProducts.getTotalWishlist()).isEqualTo(UPDATED_TOTAL_WISHLIST);
        assertThat(testProducts.getTotalStars()).isEqualTo(UPDATED_TOTAL_STARS);
        assertThat(testProducts.getDiscountedPercentage()).isEqualTo(UPDATED_DISCOUNTED_PERCENTAGE);
        assertThat(testProducts.isPreferredInd()).isEqualTo(UPDATED_PREFERRED_IND);
        assertThat(testProducts.isAvailableDeliveryInd()).isEqualTo(UPDATED_AVAILABLE_DELIVERY_IND);
        assertThat(testProducts.isActiveInd()).isEqualTo(UPDATED_ACTIVE_IND);
        assertThat(testProducts.isQuestionsAboutProductInd()).isEqualTo(UPDATED_QUESTIONS_ABOUT_PRODUCT_IND);
        assertThat(testProducts.getLastEditedBy()).isEqualTo(UPDATED_LAST_EDITED_BY);
        assertThat(testProducts.getLastEditedWhen()).isEqualTo(UPDATED_LAST_EDITED_WHEN);
        assertThat(testProducts.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testProducts.getAvailableDate()).isEqualTo(UPDATED_AVAILABLE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingProducts() throws Exception {
        int databaseSizeBeforeUpdate = productsRepository.findAll().size();

        // Create the Products
        ProductsDTO productsDTO = productsMapper.toDto(products);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductsMockMvc.perform(put("/api/products").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Products in the database
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducts() throws Exception {
        // Initialize the database
        productsRepository.saveAndFlush(products);

        int databaseSizeBeforeDelete = productsRepository.findAll().size();

        // Delete the products
        restProductsMockMvc.perform(delete("/api/products/{id}", products.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Products> productsList = productsRepository.findAll();
        assertThat(productsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
